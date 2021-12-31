package lzxus.cerberus.Structs;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.PlayerWolfData;
import org.bukkit.entity.*;

public class WolfObtainer {
    public static Wolf getWolf(Player p){
        if ((PlayerWolfData.getWolfStatus(p).equals(1)) && (PlayerWolfData.getWolfUUID(p)).equals(""))
        {
            Wolf newWolf = (Wolf) (p).getWorld().spawnEntity((p).getLocation(), EntityType.WOLF);
            newWolf.setTamed(true);
            newWolf.setOwner(p);
            PetData newData = new PetData(newWolf);
            Cerberus.updateWolfList(newData,p,true);
            return newWolf;
        }
        return null;
    }
}
