package net.anemys.toastapi.platform;

import net.anemys.toastapi.Toast;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("UnstableApiUsage")
public final class Modern extends AbstractVersion
{
    public Modern(Toast toast)
    {
        super(toast);
    }

    @Override
    public @NotNull String createAdvancementJson()
    {
        ItemMeta meta = toast.icon().getItemMeta();
        String titleJson = GsonComponentSerializer.gson().serialize(toast.text());
        String descriptionJson = "{\"text\":\"\"}";

        StringBuilder components = new StringBuilder();

        if (meta != null)
        {

            CustomModelDataComponent cmd = meta.getCustomModelDataComponent();

            if (!cmd.getStrings().isEmpty() || !cmd.getFloats().isEmpty() || !cmd.getFlags().isEmpty() || !cmd.getColors().isEmpty()) {

                if (!components.isEmpty()) components.append(",");

                components.append("\"minecraft:custom_model_data\": {");

                List<String> parts = new ArrayList<>();

                if (!cmd.getStrings().isEmpty())
                {
                    String stringsJoined = cmd.getStrings().stream()
                            .map(s -> "\"" + s + "\"")
                            .collect(Collectors.joining(","));

                    parts.add("\"strings\": [" + stringsJoined + "]");
                }

                if (!cmd.getFloats().isEmpty()) parts.add("\"floats\": " + cmd.getFloats());
                if (!cmd.getFlags().isEmpty()) parts.add("\"flags\": " + cmd.getFlags());

                if (!cmd.getColors().isEmpty())
                {
                    String colorsJoined = cmd.getColors().stream()
                            .map(c -> String.valueOf(c.asRGB()))
                            .collect(Collectors.joining(","));

                    parts.add("\"colors\": [" + colorsJoined + "]");
                }

                components.append(String.join(",", parts));
                components.append("}");
            }

            if (meta.hasEnchants())
            {
                if (!components.isEmpty()) components.append(",");

                String enchantments = meta.getEnchants().entrySet().stream()
                        .map(entry -> "\"minecraft:" + entry.getKey().getKey().getKey() + "\":" + entry.getValue())
                        .collect(Collectors.joining(","));

                components.append("\"minecraft:enchantments\": {")
                        .append(enchantments)
                        .append("}");
            }

        }

        String componentsPart = components.isEmpty() ? "" : components.toString();

        return """
        {
          "display": {
            "icon": {
              "id": "minecraft:%s",
              "components": { %s }
            },
            "title": %s,
            "description": %s,
            "frame": "%s",
            "show_toast": true,
            "announce_to_chat": false,
            "hidden": true
          },
          "criteria": {
            "trigger": {
              "trigger": "minecraft:impossible"
            }
          }
        }
        """.formatted(
                    toast.icon().getType().getKey().getKey(),
                    componentsPart,
                    titleJson,
                    descriptionJson,
                    toast.type().value()
            );
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public String @NotNull [] versions()
    {
        return new String[]{"1.21.4", "1.21.5", "1.21.6", "1.21.7", "1.21.8", "1.21.9", "1.21.10", "1.21.11", "26.1", "26.1.2"};
    }
}
