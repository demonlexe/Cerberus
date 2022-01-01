package lzxus.cerberus.Structs;

import lzxus.cerberus.Cerberus;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class PlayerReset {
    public static void resetP(Player p, PetData pet){

        //Creates Boolean data
        pet.setWolfOwned(0); //false
        pet.setWolfStatus(0); //false

        //Creates Level data
        pet.setWolfLvl(0);

        //Creates XP data
        pet.setWolfXp(0.0);

        //Creates Name data
        pet.setWolfName("");

        //Creates Attack data
        pet.setAttackStatus(1);
        pet.setAttackType("m");

        //Creates UUID data
        pet.setWolfUUID("");
        if (pet!=null && p!= null)
        {
            Cerberus.updateWolfList(pet,p,"PetRemoved");
        }
    }
}
