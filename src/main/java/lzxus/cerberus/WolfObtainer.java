package lzxus.cerberus;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class WolfObtainer {
    public static Wolf getWolf(String idAsString){
        for (World w : Bukkit.getWorlds())
        {
            for (Entity e : w.getEntities())
            {
                System.out.println(e.getUniqueId());
                if (e.getUniqueId().toString().equals(idAsString))
                {
                    return (Wolf) e;
                }
            }
        }
        return null;
    }

    public static String getWolfIDFromPlayer(Player p)
    {
        System.out.println("Getting WolfID from Player "+p.getName());
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey uniqueIDKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-uuid");
        String toReturn = data.get(uniqueIDKey, PersistentDataType.STRING);
        return toReturn;
    }
}
