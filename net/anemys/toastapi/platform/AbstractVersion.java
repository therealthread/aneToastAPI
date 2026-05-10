package net.anemys.toastapi.platform;

import net.anemys.toastapi.Toast;
import net.anemys.toastapi.AneToastAPI;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

@SuppressWarnings({"deprecation", "SpellCheckingInspection", "DataFlowIssue"})
public abstract class AbstractVersion
        implements IVersionLogic
{
    protected final Toast toast;

    public AbstractVersion(Toast toast)
    {
        this.toast = toast;
    }

    public abstract String createAdvancementJson();

    public void show(Collection<? extends Player> targets)
    {
        if (targets == null || !targets.iterator().hasNext()) return;

        String json = createAdvancementJson();
        NamespacedKey key = new NamespacedKey(AneToastAPI.getInstance(), "toast_" + UUID.randomUUID());

        Bukkit.getUnsafe().loadAdvancement(key, json);

        for (Player p : targets)
        {
            if (!p.isOnline()) continue;

            p.getAdvancementProgress(Bukkit.getAdvancement(key)).awardCriteria("trigger");

            Bukkit.getScheduler().runTaskLater(AneToastAPI.getInstance(), () ->
            {
                if (p.isOnline())
                {
                    p.getAdvancementProgress(Bukkit.getAdvancement(key)).revokeCriteria("trigger");
                }
            }, 20L);

        }

        Bukkit.getScheduler().runTaskLater(AneToastAPI.getInstance(), () ->
                Bukkit.getUnsafe().removeAdvancement(key), 40L);
    }
}
