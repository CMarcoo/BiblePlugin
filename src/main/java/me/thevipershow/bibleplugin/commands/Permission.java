package me.thevipershow.bibleplugin.commands;

public enum Permission {
    AVAILABLE("bible.commands.available"),
    DOWNLOADED("bible.commands.downloaded"),
    DOWNLOAD("bible.commands.download"),
    VERSE("bible.commands.verse"),
    OCCURRENCES("bible.commands.occurrences"),
    FIND("bible.commands.find");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
