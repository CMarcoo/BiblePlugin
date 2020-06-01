package me.thevipershow.bibleplugin.commands;

import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface PermissionCheck {
    void runWithPermission(CommandSender sender);
}
