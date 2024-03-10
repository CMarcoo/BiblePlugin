package top.cmarco.bibleplugin.commands;

public enum Permission {
    AVAILABLE("bible.commands.available"),
    DOWNLOADED("bible.commands.downloaded"),
    DOWNLOAD("bible.commands.download"),
    LOAD("bible.commands.load"),
    VERSE("bible.commands.verse"),
    OCCURRENCES("bible.commands.occurrences"),
    BOOKS("bible.commands.books"),
    VERSES("bible.commands.verses"),
    CHAPTERS("bible.commands.chapters"),

    LOGIN_VERSE("bible.commands.loginverse"),
    FIND("bible.commands.find");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
