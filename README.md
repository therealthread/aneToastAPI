# aneToastAPI
Simple toast notification system for minecraft bukkit api

```java
// new obj ToastManager
ToastManager toastManager = new ToastManager(yourPlugin);

// to any player
toastManager.showToast(player, "diamond", "Elmas Buldun!", ToastType.GOAL, 0);

// to multiple players
toastManager.showToast(Arrays.asList(player1, player2), "gold_ingot", "Altın Topladınız!", ToastType.TASK, 0);

// to all players
toastManager.showToastToAll("netherite_ingot", "Sunucu Ödülü!", ToastType.CHALLENGE, 0);

// with builder
toastManager.createToast()
    .withIcon("emerald")
    .withMessage("Harika İş!|İkinci Satır")
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
