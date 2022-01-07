package lzxus.cerberus.attacks;

import lzxus.cerberus.petdata.Pet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class IndividualFlameAttack extends SpecialAttack{

    @Override
    public boolean attack(LivingEntity target) {
        Wolf w = assignedPet.getWolf();
        if (w == null || target == null || target.isDead()) {return false;} else {
            target.getWorld();
        }

        World tWorld = target.getWorld();
        if (!applyPotionEffects(w,target)) {return false;}
        target.setFireTicks(effectLengthTicks);
        for (Particle p : targetParticles)
        {
            tWorld.spawnParticle(p,target.getLocation(),10,1.5,1,1.5);
        }

        return true;
    }

    @Override
    public String getAttackMessage() {
        if (attackName!=null && attackChatColor!=null)
        {
            return (attackChatColor+attackName);
        }
        return null;
    }

    @Override
    public String getAttackInfo() {
        String toReturn = cData.systemColor+attackName+" ("
                +attackChatColor+nameInData+cData.systemColor+") - "
                +ChatColor.WHITE+ChatColor.ITALIC+"Provides "+effectLengthTicks/20+" seconds of "
                +attackChatColor+ChatColor.ITALIC+"Flame Effect "+ChatColor.WHITE+ChatColor.ITALIC+"to an enemy.";
        return toReturn;
    }

    public IndividualFlameAttack(Pet petToUse){
        super(petToUse);

        attackName = "Flame Attack";
        nameInData = "flame";
        attackChatColor = ChatColor.GOLD;
        effectLengthTicks = 100;

        PotionEffect newPotionEffect = new PotionEffect(PotionEffectType.FIRE_RESISTANCE,effectLengthTicks, (int) damageMultiplier,false,false);
        if (!addPetEffect(newPotionEffect)) {return;}
        Particle newParticle = Particle.LAVA;
        if (!addTargetParticle(newParticle)) {return;}
    }
}
