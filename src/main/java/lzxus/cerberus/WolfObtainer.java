package lzxus.cerberus;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class WolfObtainer {
    public static Wolf getWolf(Player p){
        if ((PlayerWolfData.getWolfStatus(p).equals(1)) && (PlayerWolfData.getWolfUUID(p)).equals(""))
        {
            Wolf newWolf = (Wolf) (p).getWorld().spawnEntity((p).getLocation(), EntityType.WOLF);
            newWolf.setTamed(true);
            newWolf.setOwner(p);
            Cerberus.updateWolfList(newWolf,p,true);
            return newWolf;
        }
        return null;
    }
}
