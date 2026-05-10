package net.anemys.toastapi.platform;

import net.anemys.toastapi.Toast;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Legacy extends AbstractVersion
{

    public Legacy(Toast toast)
    {
        super(toast);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String createAdvancementJson()
    {
        return "soon";
    }


    @Override
    public String[] versions()
    {
        return new String[] {
                "1.16", "1.17", "1.18", "1.19", "1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4"
        };
    }
}