package me.thevipershow.bibleplugin.commands;

@FunctionalInterface
public interface PermissionCheck {
    void runWithPermission();
}
