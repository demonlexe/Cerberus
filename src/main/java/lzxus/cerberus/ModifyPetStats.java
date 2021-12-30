package lzxus.cerberus;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Wolf;

public class ModifyPetStats {
    public static void updateStats(Wolf w, Integer currentLevel){
        if (w != null && currentLevel != null)
        {
            System.out.println("Current Attack Damage: "+ w.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue());
            System.out.println("Current Max Health: "+ w.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());

            w.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(4+(currentLevel*.5));
            w.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20+currentLevel);
        }
    }
}
