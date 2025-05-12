# aneToastAPI
Simple toast notification system for minecraft bukkit api

```java
// new obj ToastManager
ToastManager toastManager = new ToastManager(yourPlugin);

// to any player
toastManager.showToast(player, "DIAMOND", "FIND THE DIAMONDS!", ToastType.GOAL, 0);

// to multiple players
toastManager.showToast(Arrays.asList(player1, player2), "GOLD_INGOT", "HE,! ANY MESSAGE!", ToastType.TASK, 0);

// to all players
toastManager.showToastToAll("NETHERITE_BLOCK", "HEY!", ToastType.CHALLENGE, 0);

// with builder
toastManager.createToast()
    .withIcon("EMERALD")
    .withMessage("First line!|Seconds line")
    .withStyle(ToastType.GOAL)
    .withModelData(1)
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
