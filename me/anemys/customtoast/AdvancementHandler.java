package me.anemys.anecustomtoast;

import me.anemys.aneiridiumpre.IridiumColorAPI;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings({"unused", "SpellCheckingInspection", "deprecation"})

class AdvancementHandler {
    private final JavaPlugin plugin;

    AdvancementHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Shows Toast notification to specified players
     */
    void showToast(Collection<? extends Player> players, String icon, String message, ToastType style, int modelData) {
        NamespacedKey advancementKey = createAdvancement(icon, message, style, modelData);

        /*
         *   Give the same advancement to all players.
         *   If not, create a new advancement for each player.
         *   (We don't want this to happen.)
         * */

        for (Player player : players) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                grantAdvancement(player, advancementKey);
                Bukkit.getScheduler().runTaskLater(plugin, () -> revokeAdvancement(player, advancementKey), 10);
            }, 1);
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getUnsafe().removeAdvancement(advancementKey), 40);
    }

    /**
     * Shows the Toast notification to all players in a more optimal way
     * Creates a single advancement and shows it to all players
     */
    void showToastToAll(String icon, String message, ToastType style, int modelData) {
        Collection<? extends Player> allPlayers = Bukkit.getOnlinePlayers();

        if (allPlayers.isEmpty()) {
            return;
        }

        NamespacedKey advancementKey = createAdvancement(icon, message, style, modelData);

        /*
        *   Give the same advancement to all players.
        *   If not, create a new advancement for each player.
        *   (We don't want this to happen.)
        * */

        for (Player player : allPlayers) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                grantAdvancement(player, advancementKey);
                Bukkit.getScheduler().runTaskLater(plugin, () -> revokeAdvancement(player, advancementKey), 10);
            }, 1);
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getUnsafe().removeAdvancement(advancementKey), 40);
    }

    /**
     * Creates Advancement
     */
    private NamespacedKey createAdvancement(String icon, String message, ToastType style, int modelData) {
        UUID randomUUID = UUID.randomUUID();
        message = IridiumColorAPI.process(message);

        NamespacedKey advancementKey = new NamespacedKey(plugin, "anelib_" + randomUUID);
        String serverVersion = Bukkit.getServer().getVersion();
        String iconDefinition;

        icon = icon.toLowerCase()
                .replace("İ", "I")
                .replace("ı", "i");

        // For versions 1.20.5+ "id" is used, for previous versions "item"
        if (serverVersion.contains("1.20.5") || serverVersion.contains("1.20.6") || serverVersion.contains("1.21")) {
            iconDefinition = "\"id\": \"minecraft:" + icon + "\"";
        } else {
            iconDefinition = "\"item\": \"minecraft:" + icon + "\"";
        }

        if (modelData > 0) {
            iconDefinition += ",\n \"nbt\": \"{CustomModelData:" + modelData + "}\"";
        }

        Bukkit.getUnsafe().loadAdvancement(advancementKey, "{\n" +
                " \"criteria\": {\n" +
                " \"trigger\": {\n" +
                " \"trigger\": \"minecraft:impossible\"\n" +
                " }\n" +
                " },\n" +
                " \"display\": {\n" +
                " \"icon\": {\n" +
                " " + iconDefinition + "\n" +
                " },\n" +
                " \"title\": {\n" +
                " \"text\": \"" + message.replace("|", "\n") + "\"\n" +
                " },\n" +
                " \"description\": {\n" +
                " \"text\": \"\"\n" +
                " },\n" +
                " \"background\": \"minecraft:textures/gui/advancements/backgrounds/adventure.png\",\n" +
                " \"frame\": \"" + style.toString().toLowerCase() + "\",\n" +
                " \"announce_to_chat\": false,\n" +
                " \"show_toast\": true,\n" +
                " \"hidden\": true\n" +
                " }\n" +
                "}");
        return advancementKey;
    }

    /**
     * Add advancement to the player
     */
    private void grantAdvancement(Player player, NamespacedKey advancementKey) {
        player.getAdvancementProgress(Objects.requireNonNull(Bukkit.getAdvancement(advancementKey)))
                .awardCriteria("trigger");
    }

    /**
     * Revoke advancement from the player
     */
    private void revokeAdvancement(Player player, NamespacedKey advancementKey) {
        player.getAdvancementProgress(Objects.requireNonNull(Bukkit.getAdvancement(advancementKey)))
                .revokeCriteria("trigger");
    }
}
