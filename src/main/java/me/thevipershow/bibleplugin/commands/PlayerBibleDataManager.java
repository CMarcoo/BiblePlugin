package me.thevipershow.bibleplugin.commands;

import java.util.HashMap;
import java.util.UUID;
import me.thevipershow.bibleplugin.exceptions.BibleException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class PlayerBibleDataManager {

    private final Plugin plugin;
    private static PlayerBibleDataManager instance = null;

    private PlayerBibleDataManager(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Get the instance of this manager.
     * This uses Lazy singleton instantiation pattern, so nothing will be
     * loaded until any object calls this method.
     * @param plugin This plugin's instance.
     * @return The manager instance (always the same).
     */
    public static PlayerBibleDataManager getInstance(Plugin plugin) {
        if (instance == null) {
            instance = new PlayerBibleDataManager(plugin);
        }
        return instance;
    }

    private final HashMap<UUID, PlayerBibleData> playerBibleDataMap = new HashMap<>(); // HashMap

    /**
     * Get the data mapped to each player's UUID.
     * @return The map containing all the data.
     */
    public final HashMap<UUID, PlayerBibleData> getPlayerBibleDataMap() {
        return playerBibleDataMap;
    }

    /**
     * Add a new player into the saved data.
     * @param player An online and non null player.
     */
    public final void addNew(Player player) {
        this.playerBibleDataMap.put(player.getUniqueId(), new PlayerBibleData());
    }

    /**
     * Updates someone's PlayerBibleData through a BibleSection enum.
     * If the Object passed doesn't match the BibleSection's appropriate type
     * a {@link me.thevipershow.bibleplugin.exceptions.BibleException} will be thrown.
     * @param player The player whose data will be updated.
     * @param bibleSection The BibleSection to update.
     * @param newData The new data Object.
     */
    public final void update(Player player, BibleSection bibleSection, Object newData) throws BibleException {
        if (bibleSection.isValidObject(newData)) {
            if (this.playerBibleDataMap.containsKey(player.getUniqueId())) {
                PlayerBibleData playerBibleData = this.playerBibleDataMap.get(player.getUniqueId());
                playerBibleData.setSection(bibleSection, newData);
            } else {
                PlayerBibleData playerBibleData = new PlayerBibleData();
                playerBibleData.setSection(bibleSection, newData);
                this.playerBibleDataMap.put(player.getUniqueId(), playerBibleData);
            }
        } else {
            throw new BibleException("An illegal object has been tried to be inserted into " + player.getName() + "'s PlayerBibleData." +
                    "\nThe Object passed was of type " + newData.getClass().getSimpleName() + " , but the required was " + bibleSection.getRequiredType().getName());
        }
    }
}
