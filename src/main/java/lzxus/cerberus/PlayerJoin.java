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
        if (PlayerWolfData.getWolfStatus(p).equals(1))
        {
            Wolf obtainedWolf = WolfObtainer.getWolf(p);
            System.out.println("Running PlayerJoin wolf obtainer: "+obtainedWolf);
            if (obtainedWolf != null)
            {
                Integer lvl = PlayerWolfData.getWolfLvl(p);
                if (lvl != null)
                    ModifyPetStats.updateStats(obtainedWolf,lvl);
            }

        }
    }
}
