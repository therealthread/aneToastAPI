package me.anemys.anecustomtoast;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collection;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class ToastBuilder {
    private String icon = "paper";
    private String message = "Toast Message";
    private ToastType style = ToastType.TASK;
    private int modelData = 0;
    private Collection<? extends Player> players;
    private boolean isToAll = false;
    private final AdvancementHandler advancementHandler;

    ToastBuilder(JavaPlugin plugin, AdvancementHandler advancementHandler) {
        this.advancementHandler = advancementHandler;
    }

    /**
     * Determines the players to display the notification
     * @param players Players to display the notification
     * @return This builder
     */
    public ToastBuilder to(Collection<? extends Player> players) {
        this.players = players;
        this.isToAll = false;
        return this;
    }

    /**
     * Determines the players to display the notification
     * @param players Players to display the notification
     * @return This builder
     */
    public ToastBuilder to(Player... players) {
        this.players = Arrays.asList(players);
        this.isToAll = false;
        return this;
    }

    /**
     * Determines whether the notification will be displayed to all players on the server
     * @return This builder
     **/
    public ToastBuilder toAll() {
        this.isToAll = true;
        return this;
    }

    /**
     * Determines the icon to be displayed in the notification
     * @param icon Minecraft item ID ("diamond", "paper" etc.)
     * @return This builder
     */
    public ToastBuilder withIcon(String icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Determines the message to be displayed in the notification
     * | character can be used for line break
     * @param message Message to be displayed
     * @return This builder
     */
    public ToastBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Determines the style of the notification
     * @param style Toast style
     * @return This builder
     */
    public ToastBuilder withStyle(ToastType style) {
        this.style = style;
        return this;
    }

    /**
     * Determines the CustomModelData value to be used in the notification icon
     * @param modelData CustomModelData value
     * @return This builder
     */
    public ToastBuilder withModelData(int modelData) {
        this.modelData = modelData;
        return this;
    }

    /**
     * Notifications are generated and displayed to specified players
     */
    public void show() {
        if (isToAll) {
            advancementHandler.showToastToAll(icon, message, style, modelData);
        } else {
            if (players == null || players.isEmpty()) {
                throw new IllegalStateException("Bildirim gösterilecek oyuncu belirlenmedi!");
            }
            advancementHandler.showToast(players, icon, message, style, modelData);
        }
    }
}