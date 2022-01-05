package lzxus.cerberus.Structs;

import lzxus.cerberus.Cerberus;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class PlayerReset {
    private void createPetData(Player p, PetData pet)
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

        //Creates UUID data
        pet.setWolfUUID("");
    }

    public void initializeP(Player p, PetData pet)
    {
        createPetData(p,pet);
    }

    public void newP(Player p, PetData pet, Wolf w) {
        createPetData(p, pet);
        pet.setWolfStatus(1);
        pet.setWolfOwned(1);
        pet.setWolfUUID(w.getUniqueId().toString());
    }

    public void resetP(Player p, PetData pet){

        createPetData(p,pet);
        if (pet!=null && p!= null)
        {
            Cerberus.updateWolfList(pet,p,"PetRemoved");
        }
    }
}
