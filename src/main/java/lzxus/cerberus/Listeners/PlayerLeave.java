package lzxus.cerberus.Listeners;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.PlayerWolfData;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    public static void funcOnDisconnect(Player p)
    {
        String currentUUID = PlayerWolfData.getWolfUUID(p);
        if (currentUUID.equals("") || currentUUID.equals(null))
        {

        }
        else
        {
            PlayerWolfData.setWolfUUID(p,"");
            Cerberus.updateWolfList(Cerberus.obtainPetData(p),p,false);
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
