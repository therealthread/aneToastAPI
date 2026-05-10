package net.anemys.toastapi;

import net.kyori.adventure.text.Component;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public record Toast(
        ItemStack icon,
        ToastTypes.ToastLogic type,
        Component text,
        @Nullable Collection<? extends Player> players
) {

    /*
    * preservable object
    * */
    @Contract("_, _, _ -> new")
    public static @NotNull Toast withoutPlayers(ItemStack icon, ToastTypes.ToastLogic type, Component text) {
        return new Toast(icon, type, text, null);
    }
}