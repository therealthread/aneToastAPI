# aneToastAPI | 1.16.5 - 1.20.4+ Component (Custom models) - 1.21**
Simple toast notification system for minecraft bukkit api

![](https://github.com/therealthread/aneToastAPI/blob/main/image.png?raw=true)
![](https://github.com/therealthread/aneToastAPI/blob/main/rgb.png?raw=true)

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
    .withModelData(1000, "floats") //("string", "float", or ("integer [<1.20.4]"))
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
