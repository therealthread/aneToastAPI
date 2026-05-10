# 1.16 - 1.21.5 Component (Custom models) - [aneCT]
Simple toast notification system for minecraft bukkit

![](https://github.com/therealthread/aneToastAPI/blob/main/image.png?raw=true)
![](https://github.com/therealthread/aneToastAPI/blob/main/rgb.png?raw=true)

### Between 1.21.2 - 1.21.11 | 26.1x *(FLOATS & STRINGS - Components Format)*
```json
{
  "id": "minecraft:stone",
  "components": { "minecraft:custom_model_data": { "floats":[1] }
}
```
```json
{
  "id": "minecraft:stone",
  "components": { "minecraft:custom_model_data": { "strings":["anemys"] }
}
```

## **SENDING**
```java
// set instance in main class
AneToastAPI.setInstance(this);

// create toast object
Toast toast = new Toast(ItemStack.of(Material.STONE), ToastTypes.GOAL, Component.text("selam", NamedTextColor.YELLOW), players); // -> Collection<Player>

AneToastAPI.show(toast);
AneToastAPI.show(cachedToast, newTargets);
```
