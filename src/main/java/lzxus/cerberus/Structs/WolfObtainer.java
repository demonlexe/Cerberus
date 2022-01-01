package lzxus.cerberus.Structs;

import lzxus.cerberus.Cerberus;
import org.bukkit.entity.*;

public class WolfObtainer {
    public static Wolf getWolf(Player p){
        PetData pet = Cerberus.obtainPetData(p);
        if (pet == null)
        {
            pet = new PetData(p);
        }
        if ((pet.getWolfStatus().equals(1)) && (pet.getWolfUUID()).equals(""))
        {
            Wolf newWolf = (Wolf) (p).getWorld().spawnEntity((p).getLocation(), EntityType.WOLF);
            newWolf.setTamed(true);
            newWolf.setOwner(p);
            PetData newData = new PetData(newWolf,p);
            Cerberus.updateWolfList(newData,p,"PetAdded");
            return newWolf;
        }
        else
        {
            Cerberus.updateWolfList(pet,p,"NoPetOwned");
        }
        return null;
    }
}
