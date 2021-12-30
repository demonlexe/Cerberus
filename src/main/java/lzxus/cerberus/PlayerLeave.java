package lzxus.cerberus;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class PlayerLeave implements Listener {

    public static void funcOnDisconnect(Player p)
    {
        String currentUUID = PlayerWolfData.getWolfUUID(p);
        if (currentUUID.equals("") || currentUUID.equals(null))
        {

        }
        else
        {
            Wolf w = Cerberus.obtainFromWolfList(p);
            PlayerWolfData.setWolfUUID(p,"");
            Cerberus.updateWolfList(w,p,false);
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
