package net.anemys.toastapi.platform;

import net.anemys.toastapi.Toast;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
        String descriptionJson = """
            {"text":""}
            """;

        StringBuilder components = new StringBuilder();

        if (meta != null)
        {

            if (meta.hasCustomModelData())
            {
                CustomModelDataComponent cmd = meta.getCustomModelDataComponent();

                components.append("\"minecraft:custom_model_data\": {");

                boolean first = true;

                if (!cmd.getStrings().isEmpty())
                {
                    components.append("\"strings\": [")
                            .append(
                                    cmd.getStrings()
                                            .stream()
                                            .map(s -> "\"" + s + "\"")
                                            .collect(Collectors.joining(","))
                            )
                            .append("]");

                    first = false;
                }

                if (!cmd.getFloats().isEmpty())
                {
                    if (!first) components.append(",");

                    components.append("\"floats\": ")
                            .append(cmd.getFloats());

                    first = false;
                }

                if (!cmd.getFlags().isEmpty())
                {
                    if (!first) components.append(",");

                    components.append("\"flags\": ")
                            .append(cmd.getFlags());

                    first = false;
                }

                if (!cmd.getColors().isEmpty())
                {
                    if (!first) components.append(",");

                    components.append("\"colors\": ")
                            .append(
                                    cmd.getColors()
                                            .stream()
                                            .map(c -> String.valueOf(c.asRGB()))
                                            .collect(Collectors.joining(","))
                            );
                }

                components.append("}");
            }

            if (meta.hasEnchants())
            {

                if (!components.isEmpty())
                {
                    components.append(",");
                }

                String enchantments = meta.getEnchants()
                        .entrySet()
                        .stream()
                        .map(entry ->
                                "\"minecraft:" +
                                        entry.getKey().getKey().getKey() +
                                        "\":" +
                                        entry.getValue()
                        )
                        .collect(Collectors.joining(","));

                components.append("""
                    "minecraft:enchantments": {
                """);

                components.append(enchantments);
                components.append("""
        }
        """);
            }
        }

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
                components,
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