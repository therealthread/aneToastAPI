package me.anemys.anecustomtoast;

import me.anemys.toastapi.utils.ColorParser;
import me.anemys.toastapi.versions.VersionType;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static me.anemys.toastapi.versions.ServerVersion.getVersionType;

@SuppressWarnings({"unused", "SpellCheckingInspection", "deprecation"})
class AdvancementHandler {
    private final JavaPlugin plugin;

    AdvancementHandler(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Shows Toast notification to specified players
     */
    void showToast(Collection<? extends Player> players, String icon, String message, ToastType style, Object modelData, String modelDataType, boolean glowing) {
        NamespacedKey advancementKey = createAdvancement(icon, message, style, modelData, modelDataType, glowing);

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
    void showToastToAll(String icon, String message, ToastType style, Object modelData, String modelDataType, boolean glowing) {
        Collection<? extends Player> allPlayers = Bukkit.getOnlinePlayers();

        if (allPlayers.isEmpty()) {
            return;
        }

        NamespacedKey advancementKey = createAdvancement(icon, message, style, modelData, modelDataType, glowing);

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
     * Uses NBT with integer CustomModelData (1.16-1.20.4)
     *
     * @param icon The Minecraft item ID to use as the toast icon
     * @param message The message to display in the toast
     * @param style The advancement frame style (toast type)
     * @param modelData The custom model data as integer
     * @return The NamespacedKey of the created advancement
     */
    @NotNull
    private NamespacedKey legacyType(String icon, String message, ToastType style, Object modelData, boolean glowing, NamespacedKey advancementKey) {

        int modelDataInt = 0;
        if (modelData instanceof Integer) {
            modelDataInt = (Integer) modelData;
        } else if (modelData instanceof Float) {
            modelDataInt = ((Float) modelData).intValue();
        } else if (modelData instanceof String) {
            try {
                modelDataInt = Integer.parseInt(modelData.toString());
            } catch (NumberFormatException ignored) {
                //TODO: If parsing fails, modelDataInt remains 0
            }
        }

        String json = "{\n" +
                " \"criteria\": {\n" +
                " \"trigger\": {\n" +
                " \"trigger\": \"minecraft:impossible\"\n" +
                " }\n" +
                " },\n" +
                " \"display\": {\n" +
                " \"icon\": {\n" +
                " \"item\": \"minecraft:" + icon + "\",\n" +
                " \"nbt\": \"{CustomModelData:" + modelDataInt +
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

        Bukkit.getUnsafe().loadAdvancement(advancementKey, json);
        return advancementKey;
    }

    /**
     * Creates an advancement for displaying a toast notification for Minecraft versions 1.20.5-1.21.1
     * Uses components system with integer CustomModelData
     *
     * @param icon The Minecraft item ID to use as the toast icon
     * @param message The message to display in the toast
     * @param style The advancement frame style (toast type)
     * @param modelData The custom model data as integer
     * @return The NamespacedKey of the created advancement
     */
    @NotNull
    private NamespacedKey middleType(String icon, String message, ToastType style, Object modelData, boolean glowing, NamespacedKey advancementKey) {
        int modelDataInt = 0;
        if (modelData instanceof Integer) {
            modelDataInt = (Integer) modelData;
        } else if (modelData instanceof Float) {
            modelDataInt = ((Float) modelData).intValue();
        } else if (modelData instanceof String) {
            try {
                modelDataInt = Integer.parseInt(modelData.toString());
            } catch (NumberFormatException ignored) {
                //TODO: If parsing fails, modelDataInt remains 0
            }
        }

        String json = "{\n" +
                " \"criteria\": {\n" +
                "   \"trigger\": {\n" +
                "     \"trigger\": \"minecraft:impossible\"\n" +
                "   }\n" +
                " },\n" +
                " \"display\": {\n" +
                "   \"icon\": {\n" +
                "     \"id\": \"minecraft:" + icon + "\",\n" +
                "     \"components\": {\n" +
                "       \"minecraft:custom_model_data\": " + modelDataInt +
                (glowing ? ",\n       \"minecraft:enchantments\": {\n" +
                        "         \"levels\": {\n" +
                        "           \"minecraft:protection\": 1\n" +
                        "         }\n" +
                        "       }" : "") +
                "\n     },\n" +
                "     \"count\": 1\n" +
                "   },\n" +
                "   \"title\": " + message + ",\n" +
                "   \"description\": {\n" +
                "     \"text\": \"\"\n" +
                "   },\n" +
                "   \"background\": \"minecraft:textures/gui/advancements/backgrounds/adventure.png\",\n" +
                "   \"frame\": \"" + style.toString().toLowerCase() + "\",\n" +
                "   \"announce_to_chat\": false,\n" +
                "   \"show_toast\": true,\n" +
                "   \"hidden\": true\n" +
                " }\n" +
                "}";

        Bukkit.getUnsafe().loadAdvancement(advancementKey, json);
        return advancementKey;
    }

    /**
     * Creates an advancement for displaying a toast notification for Minecraft versions 1.21.2+
     * Uses component system with string or float CustomModelData arrays
     *
     * @param icon The Minecraft item ID to use as the toast icon
     * @param message The message to display in the toast
     * @param style The advancement frame style (toast type)
     * @param modelData The custom model data (string or float)
     * @param modelDataType The type of model data ("string", "float", or "integer")
     * @return The NamespacedKey of the created advancement
     */
    @NotNull
    private NamespacedKey modernType(String icon, String message, ToastType style, Object modelData, String modelDataType, boolean glowing, NamespacedKey advancementKey) {
        String customModelData;

        if (modelDataType == null) {
            modelDataType = modelData instanceof String ? "string" :
                    (modelData instanceof Float || modelData instanceof Double) ? "float" : "integer";
        }

        if ("float".equals(modelDataType) || "integer".equals(modelDataType)) {
            customModelData = "\"minecraft:custom_model_data\": {\n" +
                    "        \"floats\": [" + modelData + "]\n" +
                    "      }";
        } else {
            customModelData = "\"minecraft:custom_model_data\": {\n" +
                    "        \"strings\": [\n" +
                    "          \"" + modelData + "\"\n" +
                    "        ]\n" +
                    "      }";
        }

        String json = "{\n" +
                " \"criteria\": {\n" +
                "   \"trigger\": {\n" +
                "     \"trigger\": \"minecraft:impossible\"\n" +
                "   }\n" +
                " },\n" +
                " \"display\": {\n" +
                "   \"icon\": {\n" +
                "     \"id\": \"minecraft:" + icon + "\",\n" +
                "     \"components\": {\n" +
                "       " + customModelData +
                (glowing ? ",\n       \"minecraft:enchantments\": {\n" +
                        "         \"levels\": {\n" +
                        "           \"minecraft:protection\": 1\n" +
                        "         }\n" +
                        "       }" : "") +
                "\n     },\n" +
                "     \"count\": 1\n" +
                "   },\n" +
                "   \"title\": " + message + ",\n" +
                "   \"description\": {\n" +
                "     \"text\": \"\"\n" +
                "   },\n" +
                "   \"background\": \"minecraft:textures/gui/advancements/backgrounds/adventure.png\",\n" +
                "   \"frame\": \"" + style.toString().toLowerCase() + "\",\n" +
                "   \"announce_to_chat\": false,\n" +
                "   \"show_toast\": true,\n" +
                "   \"hidden\": true\n" +
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
     * @param modelData The custom model data (can be int, float, or String)
     * @param modelDataType The type of model data ("string", "float", or "integer")
     * @return The NamespacedKey of the created advancement
     */
    @NotNull
    private NamespacedKey createAdvancement(String icon, String message, ToastType style, Object modelData, String modelDataType, boolean glowing) {
        String serverVersion = plugin.getServer().getVersion();

        List<Map<String, Object>> msgList = ColorParser.process(message);
        String json = ColorParser.formatToJsonString(msgList);
        json = json.replace("|", "\n");
        icon = icon.toLowerCase().replace("İ", "I").replace("ı", "i");

        UUID randomUUID = UUID.randomUUID();
        NamespacedKey advancementKey = new NamespacedKey(plugin, "anelib_" + randomUUID);
        
        VersionType versionType = getVersionType(serverVersion);

        switch (versionType) {
            case LEGACY:
                // 1.16 - 1.20.4: NBT format with integer CustomModelData
                if (modelData == null) {
                    modelData = 0;
                }
                return legacyType(icon, json, style, modelData, glowing, advancementKey);

            case MIDDLE:
                // 1.20.5 - 1.21.1: Components format with integer CustomModelData
                if (modelData == null) {
                    modelData = 0;
                }
                return middleType(icon, json, style, modelData, glowing, advancementKey);

            case MODERN:
            default:
                // 1.21.2+: Components format with floats/strings arrays
                if (modelData == null) {
                    modelData = "anemys";
                    modelDataType = "string";
                }
                return modernType(icon, json, style, modelData, modelDataType, glowing, advancementKey);
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
