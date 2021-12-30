package lzxus.cerberus;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerJoin implements Listener {
    @EventHandler
    public void playerJoined (PlayerJoinEvent event)
    {
        //Code to teleport main wolf to player, if applicable. Also loads wolf into Cerberus' hashtable.
        Player p = event.getPlayer();
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey uniqueIDKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-uuid");
        String obtainedString = data.get(uniqueIDKey, PersistentDataType.STRING);
        System.out.println("Player has joined, the string obtained is "+obtainedString);
        if (obtainedString != null)
        {
            Wolf obtainedWolf = WolfObtainer.getWolf(obtainedString);
            System.out.println("Running PlayerJoin wolf obtainer: "+obtainedWolf);
            if (obtainedWolf != null)
            {
                Cerberus.updateWolfList(obtainedWolf,p,true);
                NamespacedKey levelKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-level");
                Integer lvl = data.get(levelKey, PersistentDataType.INTEGER);
                if (lvl != null)
                    ModifyPetStats.updateStats(obtainedWolf,lvl);
            }

        }
    }
}
