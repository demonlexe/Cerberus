package lzxus.cerberus.Structs;

import lzxus.cerberus.Cerberus;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ModifyPetStats {
    public static void updateStats(Wolf w, Integer currentLevel){
        if (w != null && currentLevel != null)
        {
            //System.out.println("Current Attack Damage: "+ w.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue());
            //System.out.println("Current Max Health: "+ w.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());

            w.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(4+(((double)currentLevel)*.5));
            w.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20+currentLevel);

            if (currentLevel >= 5 && !w.hasPotionEffect(PotionEffectType.REGENERATION))
            {
                PotionEffect newEffect = new PotionEffect(PotionEffectType.REGENERATION,Integer.MAX_VALUE,1,true,false);
                w.addPotionEffect(newEffect);
            }

            Player p = (Player) w.getOwner();
            if (p != null)
            {
                PetData pet = Cerberus.obtainPetData(p);
                if (pet!= null && pet.getWolf().equals(w))
                    w.setCustomName(pet.getWolfName());
            }
        }
    }

}
