package lzxus.cerberus.attacks;

import lzxus.cerberus.petdata.Pet;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class IndividualFlameAttack extends SpecialAttack{

    @Override
    public boolean attack(LivingEntity target) {
        Bukkit.getLogger().info("Attacking with IndividualFlameAttack.");
        Wolf w = assignedPet.getWolf();
        if (w == null || target == null || target.isDead()) {return false;} else {
            target.getWorld();
        }

        World tWorld = target.getWorld();
        if (!applyPotionEffects(w,target)) {return false;}
        target.setFireTicks(effectLengthTicks);
        for (Particle p : targetParticles)
        {
            tWorld.spawnParticle(p,target.getLocation(),20,2,2,2);
        }

        return true;
    }

    public IndividualFlameAttack(Pet petToUse){
        super(petToUse);
        PotionEffect newPotionEffect = new PotionEffect(PotionEffectType.FIRE_RESISTANCE,effectLengthTicks, (int) damageMultiplier,false,true);
        if (!addPetEffect(newPotionEffect)) {return;}
        Particle newParticle = Particle.FLAME;
        if (!addTargetParticle(newParticle)) {return;}
    }
}
