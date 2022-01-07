package lzxus.cerberus.structs;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.petdata.Pet;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class PlayerReset {
    private void createPet(Player p, Pet pet)
    {
        //Creates Boolean data
        pet.setWolfOwned(0); //false
        pet.setWolfStatus(0); //false

        //Creates Level data
        pet.setWolfLvl(0);

        //Creates XP data
        pet.setWolfXp(0.0);

        //Creates Name data
        pet.setWolfName("");

        //Creates allowance data
        pet.setDamageEnabled(0);

        //Creates Attack data
        pet.setAttackStatus(1);
        pet.setAttackType("monster");
        pet.setSpecial1("");
        pet.setSpecial2("");
        pet.setSpecial3("");

        //Creates UUID data
        pet.setWolfUUID("");
    }

    public void initializeP(Player p, Pet pet)
    {
        createPet(p,pet);
    }

    public void newP(Player p, Pet pet, Wolf w) {
        createPet(p, pet);
        pet.setWolfStatus(1);
        pet.setWolfOwned(1);
        pet.setWolfUUID(w.getUniqueId().toString());
    }

    public void resetP(Player p, Pet pet){

        createPet(p,pet);
        if (pet!=null && p!= null)
        {
            Cerberus.updateWolfList(pet,p,"PetRemoved");
        }
    }
}
