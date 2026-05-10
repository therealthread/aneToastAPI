package net.anemys.toastapi;

import net.anemys.toastapi.platform.*;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

public final class AneToastAPI
{

    private static JavaPlugin INSTANCE;

    public static void setInstance(JavaPlugin plugin)
    {
        INSTANCE = plugin;
    }

    public static void showToast(@NotNull Toast toast)
    {
        if (toast.players() == null) throw new IllegalStateException("Toast players cannot be null for this method!");
        showToast(toast, toast.players());
    }

    public static void showToast(Toast toast, Player... players)
    {
        showToast(toast, Arrays.asList(players));
    }

    public static void showToast(Toast toast, Collection<? extends Player> players)
    {
        AbstractVersion implementation = getImplementation(toast);
        implementation.show(players);
    }

    @Contract("_ -> new")
    private static @NotNull AbstractVersion getImplementation(Toast toast)
    {
        return new Modern(toast);
    }

    public static JavaPlugin getInstance()
    {
        return INSTANCE;
    }
}