package lzxus.cerberus.attacks;

import lzxus.cerberus.petdata.Pet;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GroupFlameAttack extends SpecialAttack{
    @Override
    public boolean attack(LivingEntity target) {
        Wolf w = assignedPet.getWolf();
        if (w == null || target == null || target.isDead()) {return false;} else {
            target.getWorld();
        }

        World tWorld = target.getWorld();
        if (!applyPotionEffects(w,target)) {return false;}

        for (Particle p : targetParticles)
        {
            for (Entity e : target.getNearbyEntities(2,2,2))
            {
                if (e instanceof LivingEntity && !e.isDead() && !(e instanceof Player) && !(e instanceof Wolf))
                {
                    e.setFireTicks(effectLengthTicks);
                    tWorld.spawnParticle(p,e.getLocation(),6,.8,.8,.8);
                }
            }
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
    public GroupFlameAttack(Pet petToUse){
        super(petToUse);

        attackName = "Group Flame Attack";
        attackChatColor = ChatColor.YELLOW;

        PotionEffect newPotionEffect = new PotionEffect(PotionEffectType.FIRE_RESISTANCE,effectLengthTicks, (int) damageMultiplier,false,false);
        if (!addPetEffect(newPotionEffect)) {return;}
        Particle newParticle = Particle.LAVA;
        if (!addTargetParticle(newParticle)) {return;}
    }
}