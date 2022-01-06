package lzxus.cerberus.structs;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.petdata.Pet;
import org.bukkit.entity.*;

public class WolfObtainer {
    public static Wolf getWolf(Player p){
        Pet pet = Cerberus.obtainPetData(p);
        if (pet == null)
        {
            pet = new Pet(p);
        }
        if ((pet.getWolfStatus().equals(1)) && (pet.getWolfUUID()).equals(""))
        {
            Wolf newWolf = (Wolf) (p).getWorld().spawnEntity((p).getLocation(), EntityType.WOLF);
            newWolf.setTamed(true);
            newWolf.setOwner(p);
            Pet newData = new Pet(newWolf,p);
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
