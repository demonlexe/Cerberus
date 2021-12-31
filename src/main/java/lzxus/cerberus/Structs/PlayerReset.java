package lzxus.cerberus.Structs;

import lzxus.cerberus.Cerberus;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class PlayerReset {
    public static void resetP(Player p, Wolf w){

        //Creates Boolean data
        PlayerWolfData.setWolfOwned(p,0); //false
        PlayerWolfData.setWolfStatus(p,0); //false

        //Creates Level data
        PlayerWolfData.setWolfLvl(p,0);

        //Creates XP data
        PlayerWolfData.setWolfXp(p,0.0);

        //Creates Name data
        PlayerWolfData.setWolfName(p,"");

        //Creates Attack data
        PlayerWolfData.setAttackStatus(p,1);
        PlayerWolfData.setAttackType(p,"m");

        //Creates UUID data
        PlayerWolfData.setWolfUUID(p,"");
        if (w!=null && p!= null)
        {
            Cerberus.updateWolfList(Cerberus.obtainPetData(p),p,false);
        }
    }
}
