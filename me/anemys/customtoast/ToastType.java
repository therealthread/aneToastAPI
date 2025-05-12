package me.anemys.anecustomtoast;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"unused"})
@NotNull
public enum ToastType {
    TASK,
    GOAL,
    CHALLENGE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}