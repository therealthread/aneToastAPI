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
     */
    public void showToast(Collection<? extends Player> players, String icon, String message, ToastType style, int modelData) {
        createToast()
                .withIcon(icon)
                .withMessage(message)
                .withStyle(style)
                .withModelData(modelData)
                .to(players)
                .show();
    }

    /**
     * To All
     * @param icon Minecraft item
     * @param message Show message
     * @param style Toast Type
     * @param modelData CustomModelData value
     */
    public void showToastToAll(String icon, String message, ToastType style, int modelData) {
        createToast()
                .withIcon(icon)
                .withMessage(message)
                .withStyle(style)
                .withModelData(modelData)
                .toAll()
                .show();
    }

    /**
     * @param players Send player(s)
     * @param icon Minecraft item
     * @param message Show message
     * @param style Toast Type
     * @param modelData CustomModelData value
     */
    public void showToast(Player[] players, String icon, String message, ToastType style, int modelData) {
        showToast(Arrays.asList(players), icon, message, style, modelData);
    }
}