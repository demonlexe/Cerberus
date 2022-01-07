package lzxus.cerberus.attacks;

import lzxus.cerberus.petdata.Pet;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RegenerateAttack extends SpecialAttack{
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
            tWorld.spawnParticle(p,target.getLocation(),15,1,2,1);
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
    String toReturn = attackChatColor+attackName+cData.systemColor+" ("
            +attackChatColor+nameInData+cData.systemColor+") - "
            +ChatColor.ITALIC+ChatColor.WHITE+"Provides "+effectLengthTicks/20+" seconds of "
            +attackChatColor+ChatColor.ITALIC+"Regeneration "+ChatColor.ITALIC+ChatColor.WHITE+"for your pet.";
        return toReturn;
    }

    public RegenerateAttack(Pet petToUse){
        super(petToUse);

        attackName = "Regeneration Spell";
        nameInData = "regen";
        attackChatColor = ChatColor.LIGHT_PURPLE;
        effectLengthTicks = 80;

        PotionEffect newPotionEffect = new PotionEffect(PotionEffectType.REGENERATION,effectLengthTicks, (int) damageMultiplier,false,true);
        if (!addPetEffect(newPotionEffect)) {return;}
        Particle newParticle = Particle.SPELL_WITCH;
        if (!addTargetParticle(newParticle)) {return;}
    }
}
