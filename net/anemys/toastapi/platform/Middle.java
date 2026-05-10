package net.anemys.toastapi.platform;

import net.anemys.toastapi.Toast;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Middle extends AbstractVersion
{
    public Middle(Toast toast)
    {
        super(toast);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String createAdvancementJson()
    {
        return "soon";
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public String @NotNull [] versions()
    {
        return new String[] {
                "1.20.5", "1.20.6", "1.21", "1.21.1", "1.21.2", "1.21.3"
        };
    }
}