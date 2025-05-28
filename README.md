# 1.16 - 1.21.5 Component (Custom models) - [aneCT]
Simple toast notification system for minecraft bukkit api

![](https://github.com/therealthread/aneToastAPI/blob/main/image.png?raw=true)
![](https://github.com/therealthread/aneToastAPI/blob/main/rgb.png?raw=true)

## 📌 Data Formats by Version

### **Between 1.16 - 1.20.4 (INTEGER - Tag Format)**
```json
{
  "id": "minecraft:stone",
  "tag": { "CustomModelData": 1 }
}
```

### **Between 1.20.5 - 1.21.1 (INTEGER - Components Format)**
```json
{
  "id": "minecraft:stone",
  "components": { "minecraft:custom_model_data": 1 }
}
```

### **Between 1.21.2 - 1.21.5 (FLOATS & STRINGS - Components Format)**
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

##BUILDING
```java
// new obj ToastManager
ToastManager toastManager = new ToastManager(yourPlugin);

// to any player
toastManager.showToast(player, "DIAMOND", "FIND THE DIAMONDS!", ToastType.GOAL, 0, true);

// to multiple players
toastManager.showToast(Arrays.asList(player1, player2), "GOLD_INGOT", "HE,! ANY MESSAGE!", ToastType.TASK, 0, false);

// to all players
toastManager.showToastToAll("NETHERITE_BLOCK", "HEY!", ToastType.CHALLENGE, 0, false);

// with builder
toastManager.createToast()
    .withIcon("EMERALD")
    .withMessage("First line!|Seconds line")
    .withStyle(ToastType.GOAL)
    .withModelData(1)
    .setGlowing(true)
    .to(player)
    .show();


```

> 1.20.4+ Component (Custom model data) support
```java

toastManager.showToastToAll("NETHERITE_BLOCK", "HEY!", ToastType.CHALLENGE, "my_model_name", false);

toastManager.createToast()
    .withIcon("PAPER")
    .withMessage("&#FF0000t&#E72000e&#D04000x&#B86000t &#889F00w&#71BF00i&#59DF00t&#41FF00h &#2ECD49r&#25B46Dg&#1C9A92b &#0968DB:&#004FFFO")
    .withStyle(ToastType.GOAL)
    .withModelData("my_model_name")
    .setGlowing(false)
    .to(player)
    .show();

//valueOf types

toastManager.createToast()
    .withIcon("PAPER")
    .withMessage("&#FF0000t&#E72000e&#D04000x&#B86000t &#889F00w&#71BF00i&#59DF00t&#41FF00h &#2ECD49r&#25B46Dg&#1C9A92b &#0968DB:&#004FFFO")
    .withStyle(ToastType.GOAL)
    .withModelData(1000, "float") //("string", "float", or ("integer [<1.20.4]"))
    .withModelData("my_model_name") //Auto-detect model data type
    .setGlowing(false)
    .to(player)
    .show();


```

> **Annotations** (Maven)
```
        <dependency>
            <groupId>org.jetbrains</groupId>
            <artifactId>annotations</artifactId>
            <version>24.1.0</version>
        </dependency>
```
##
