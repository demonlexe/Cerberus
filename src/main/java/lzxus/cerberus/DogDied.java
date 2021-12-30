package lzxus.cerberus;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DogDied implements Listener {
    @EventHandler
    public static void onDeath(EntityDeathEvent event)
    {
        if (event.getEntity() instanceof Wolf)
        {
            Wolf w = (Wolf) event.getEntity();
            Player p = Cerberus.obtainFromPlayerList(w.getUniqueId().toString());
            if (p != null)
            {
                PlayerReset.resetP(p,w);
            }
        }
    }
}
