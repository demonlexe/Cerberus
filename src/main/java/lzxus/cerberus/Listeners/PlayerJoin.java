package lzxus.cerberus.Listeners;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.PetData;
import lzxus.cerberus.Structs.WolfObtainer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public void playerJoined (PlayerJoinEvent event)
    {
        //Code to teleport main wolf to player, if applicable. Also loads wolf into Cerberus' hashtable.
        Player p = event.getPlayer();
        Wolf obtainedWolf = WolfObtainer.getWolf(p);
        //System.out.println("Running PlayerJoin wolf obtainer: "+obtainedWolf);
        if (obtainedWolf != null)
        {
            PetData pet = Cerberus.obtainPetData(p);
            assert pet != null;
            Integer lvl = pet.getWolfLvl();
            if (lvl != null)
            {
                pet.updateStats(lvl);
            }

        }
    }
}
