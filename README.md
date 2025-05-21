# aneToastAPI | 1.16.5 - 1.20.5+ Component (Custom models)
Simple toast notification system for minecraft bukkit api

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

> 1.20.5+ Component (Custom model data) support
```

toastManager.showToastToAll("NETHERITE_BLOCK", "HEY!", ToastType.CHALLENGE, "my_model_name", false);

toastManager.createToast()
    .withIcon("PAPER")
    .withMessage("First line!|Seconds line")
    .withStyle(ToastType.GOAL)
    .withModelData("my_model_name")
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
