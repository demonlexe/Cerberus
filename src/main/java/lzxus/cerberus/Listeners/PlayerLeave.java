package lzxus.cerberus.Listeners;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.PetData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    /**
     * Used by Cerberus.java, PlayerLeave.java
     */
    public static void funcOnDisconnect(Player p)
    {
        PetData pet = Cerberus.obtainPetData(p);
        if (pet==null) {return;}
        String currentUUID = pet.getWolfUUID();
        if (currentUUID == null || currentUUID.equals(""))
        {

        }
        else
        {
            pet.setWolfUUID("");
            Cerberus.updateWolfList(pet,p,"PetRemoved");
        }
    }

    @EventHandler
    public void playerLeft (PlayerQuitEvent event)
    {
        if (event.getPlayer() instanceof Player)
        {
            funcOnDisconnect(event.getPlayer());
        }
    }
}
