package me.anemys.anecustomtoast;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collection;

@SuppressWarnings({"unused"})
public class ToastManager {
    private final JavaPlugin plugin;
    private final AdvancementHandler advancementHandler;

    public ToastManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.advancementHandler = new AdvancementHandler(plugin);
    }

    /**
     * @return New ToastBuilder
     */
    public ToastBuilder createToast() {
        return new ToastBuilder(plugin, advancementHandler);
    }

    /**
     * @param players Send player(s)
     * @param icon Minecraft item
     * @param message Show message
     * @param style Toast Type
     * @param modelData CustomModelData value
     * @param glowing Glowing icon
     */
    public void showToast(Collection<? extends Player> players, String icon, String message, ToastType style, Object modelData, boolean glowing) {
        createToast()
                .withIcon(icon)
                .withMessage(message)
                .withStyle(style)
                .withModelData(modelData)
                .setGlowing(glowing)
                .to(players)
                .show();
    }

    /**
     * To All
     * @param icon Minecraft item
     * @param message Show message
     * @param style Toast Type
     * @param modelData CustomModelData value
     * @param glowing Glowing icon
     */
    public void showToastToAll(String icon, String message, ToastType style, Object modelData, boolean glowing) {
        createToast()
                .withIcon(icon)
                .withMessage(message)
                .withStyle(style)
                .withModelData(modelData)
                .setGlowing(glowing)
                .toAll()
                .show();
    }

    /**
     * @param players Send player(s)
     * @param icon Minecraft item
     * @param message Show message
     * @param style Toast Type
     * @param modelData CustomModelData value
     * @param glowing Glowing icon
     */
    public void showToast(Player[] players, String icon, String message, ToastType style, Object modelData, boolean glowing) {
        showToast(Arrays.asList(players), icon, message, style, modelData, glowing);
    }
}
