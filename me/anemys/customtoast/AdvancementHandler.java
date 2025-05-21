package me.anemys.anecustomtoast;

import me.anemys.anecustomtoast.utils.ColorParser;
import me.anemys.anecustomtoast.versions.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings({"unused", "SpellCheckingInspection", "deprecation"})

class AdvancementHandler {
    private final JavaPlugin plugin;

    AdvancementHandler(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Shows Toast notification to specified players
     */
    void showToast(Collection<? extends Player> players, String icon, String message, ToastType style, Object modelData, boolean glowing) {
        NamespacedKey advancementKey = createAdvancement(icon, message, style, modelData, glowing);

        /*
         *   Give the same advancement to all players.
         *   If not, create a new advancement for each player.
         *   (We don't want this to happen.)
         * */

        for (Player p : players) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                grantAdvancement(p, advancementKey);
                Bukkit.getScheduler().runTaskLater(plugin, () -> revokeAdvancement(p, advancementKey), 10);
            }, 1);
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getUnsafe().removeAdvancement(advancementKey), 40);
    }

    /**
     * Shows the Toast notification to all players in a more optimal way
     * Creates a single advancement and shows it to all players
     */
    void showToastToAll(String icon, String message, ToastType style, Object modelData, boolean glowing) {
        Collection<? extends Player> allPlayers = Bukkit.getOnlinePlayers();

        if (allPlayers.isEmpty()) {
            return;
        }

        NamespacedKey advancementKey = createAdvancement(icon, message, style, modelData, glowing);

        /*
         *   Give the same advancement to all players.
         *   If not, create a new advancement for each player.
         *   (We don't want this to happen.)
         * */

        for (Player p : allPlayers) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                grantAdvancement(p, advancementKey);
                Bukkit.getScheduler().runTaskLater(plugin, () -> revokeAdvancement(p, advancementKey), 10);
            }, 1);
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getUnsafe().removeAdvancement(advancementKey), 40);
    }

    /**
     * Creates an advancement for displaying a toast notification for Minecraft versions before 1.20.5
     * Uses NBT with integer CustomModelData (1.20.5<legacy)
     *
     * @param icon The Minecraft item ID to use as the toast icon
     * @param message The message to display in the toast
     * @param style The advancement frame style (toast type)
     * @param modelData The custom model data as integer
     * @return The NamespacedKey of the created advancement
     */
    @NotNull
    private NamespacedKey createAdvancementLegacy(String icon, String message, ToastType style, int modelData, boolean glowing, NamespacedKey advancementKey) {


        String json = "{\n" +
                " \"criteria\": {\n" +
                " \"trigger\": {\n" +
                " \"trigger\": \"minecraft:impossible\"\n" +
                " }\n" +
                " },\n" +
                " \"display\": {\n" +
                " \"icon\": {\n" +
                " \"item\": \"minecraft:" + icon + "\",\n" +
                " \"nbt\": \"{CustomModelData:" + modelData +
                (glowing ? ",Enchantments:[{lvl:1,id:\\\"minecraft:protection\\\"}]" : "") + "}\"\n" +
                " },\n" +
                " \"title\": " + message + ",\n" +
                " \"description\": {\n" +
                " \"text\": \"\"\n" +
                " },\n" +
                " \"background\": \"minecraft:textures/gui/advancements/backgrounds/adventure.png\",\n" +
                " \"frame\": \"" + style.toString().toLowerCase() + "\",\n" +
                " \"announce_to_chat\": false,\n" +
                " \"show_toast\": true,\n" +
                " \"hidden\": true\n" +
                " }\n" +
                "}";
        Bukkit.getLogger().info(json);
        Bukkit.getUnsafe().loadAdvancement(advancementKey, json);

        return advancementKey;
    }


    /**
     * Creates an advancement for displaying a toast notification for Minecraft versions 1.20.5+
     * Uses component system with string CustomModelData
     *
     * @param icon The Minecraft item ID to use as the toast icon
     * @param message The message to display in the toast
     * @param style The advancement frame style (toast type)
     * @param modelDataString The custom model data as string
     * @return The NamespacedKey of the created advancement
     */
    @NotNull
    private NamespacedKey createAdvancementModern(String icon, String message, ToastType style, String modelDataString, boolean glowing, NamespacedKey advancementKey) {

        String json = "{\n" +
                " \"criteria\": {\n" +
                " \"trigger\": {\n" +
                " \"trigger\": \"minecraft:impossible\"\n" +
                " }\n" +
                " },\n" +
                " \"display\": {\n" +
                " \"icon\": {\n" +
                " \"id\": \"minecraft:" + icon + "\",\n" +
                " \"components\": {\n" +
                " \"minecraft:custom_model_data\": {\n" +
                " \"strings\": [\n" +
                " \"" + modelDataString + "\"\n" +
                " ]\n" +
                " }" +
                (glowing ? ",\n \"minecraft:enchantments\": {\n" +
                        " \"levels\": {\n" +
                        " \"minecraft:protection\": 1\n" +
                        " }\n" +
                        " }" : "") +
                "\n },\n" +
                " \"count\": 1\n" +
                " },\n" +
                " \"title\": " + message + ",\n" +
                " \"description\": {\n" +
                " \"text\": \"\"\n" +
                " },\n" +
                " \"background\": \"minecraft:textures/gui/advancements/backgrounds/adventure.png\",\n" +
                " \"frame\": \"" + style.toString().toLowerCase() + "\",\n" +
                " \"announce_to_chat\": false,\n" +
                " \"show_toast\": true,\n" +
                " \"hidden\": true\n" +
                " }\n" +
                "}";

        Bukkit.getUnsafe().loadAdvancement(advancementKey, json);

        return advancementKey;
    }

    /**
     * Helper method to determine which advancement creation method to use based on server version
     *
     * @param icon The Minecraft item ID to use as the toast icon
     * @param message The message to display in the toast
     * @param style The advancement frame style (toast type)
     * @param customModelData The custom model data (can be either int or String)
     * @return The NamespacedKey of the created advancement
     */
    @NotNull
    private NamespacedKey createAdvancement(String icon, String message, ToastType style, Object customModelData, boolean glowing) {

        boolean isNewVersion = ServerVersion.isNewVersion(plugin.getServer().getVersion());

        List<Map<String, Object>> msgList = ColorParser.process(message);

        String json = ColorParser.formatToJsonString(msgList);
        json = json.replace("|", "\n");
        icon = icon.toLowerCase().replace("İ", "I").replace("ı", "i");

        UUID randomUUID = UUID.randomUUID();
        NamespacedKey advancementKey = new NamespacedKey(plugin, "anelib_" + randomUUID);

        if (isNewVersion) {
            if (customModelData.toString() == null) { customModelData = "anemys"; }
            return createAdvancementModern(icon, json, style, customModelData.toString(), glowing, advancementKey);

        } else {
            int modelDataInt = 0;
            if (customModelData instanceof Integer) {
                modelDataInt = (Integer) customModelData;
            } else if (customModelData instanceof String) {
                try {
                    modelDataInt = Integer.parseInt(customModelData.toString());
                } catch (NumberFormatException ignored) {
                    //TODO: parsing fails, modelDataInt remains 0
                }
            }
            return createAdvancementLegacy(icon, json, style, modelDataInt, glowing, advancementKey);
        }
    }

    /**
     * Add advancement to the player
     */
    private void grantAdvancement(Player p, NamespacedKey advancementKey) {
        p.getAdvancementProgress(Objects.requireNonNull(Bukkit.getAdvancement(advancementKey)))
                .awardCriteria("trigger");
    }

    /**
     * Revoke advancement from the player
     */
    private void revokeAdvancement(Player p, NamespacedKey advancementKey) {
        p.getAdvancementProgress(Objects.requireNonNull(Bukkit.getAdvancement(advancementKey)))
                .revokeCriteria("trigger");
    }
}
