package lzxus.cerberus.attacks;

import lzxus.cerberus.petdata.Pet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;

public abstract class SpecialAttack {
    protected String attackName;
    protected ChatColor attackChatColor;

    protected int effectLengthTicks;
    protected double attackKnockbackStrength;
    protected double damageMultiplier;
    protected double chance;
    protected double critChance;
    protected ArrayList<PotionEffect> petEffects;
    protected ArrayList<PotionEffect> targetEffects;
    protected ArrayList<Particle> petParticles;
    protected ArrayList<Particle> targetParticles;
    protected Pet assignedPet;

    private void updateChance()
    {
        chance = ((double)assignedPet.getWolfLvl())/200;
        critChance = ((double)assignedPet.getWolfLvl())/400;
    }

    public abstract boolean attack(LivingEntity target);
    public abstract String getAttackMessage();

    public boolean applyChance(double rand, int numAttacks)
    {
        updateChance();
        if (rand <= chance/numAttacks)
        {
            return true;
        }
        return false;
    }

    public boolean applyPotionEffects(Wolf w, LivingEntity target)
    {
        for (PotionEffect e : petEffects)
        {
            w.addPotionEffect(e);
        }
        for (PotionEffect e : targetEffects)
        {
            target.addPotionEffect(e);
        }
        return true;
    }

    public boolean assignChance(double c, double crit)
    {
        chance = c;
        critChance = crit;
        return true;
    }

    public boolean addPetEffect(PotionEffect newFX)
    {
        if (petEffects == null) {return false;}

        petEffects.add(newFX);
        return true;
    }

    public boolean addTargetEffect(PotionEffect newFX)
    {
        if (targetEffects == null) {return false;}

        targetEffects.add(newFX);
        return true;
    }

    public boolean addPetParticle(Particle newParticle)
    {
        if (petParticles == null) {return false;}

        petParticles.add(newParticle);
        return true;
    }

    public boolean addTargetParticle(Particle newParticle)
    {
        if (targetParticles == null) {return false;}

        targetParticles.add(newParticle);
        return true;
    }


   /* public SpecialAttack(){
        effectLengthTicks = 0;
        attackKnockbackStrength = 0;
        damageMultiplier = 0;
        chance = 0;
        critChance = 0;
        attackEffect = new ArrayList<>();
        assignedPet = null;
        target = null;
    }*/

    public SpecialAttack(Pet petToUse){
        if (petToUse==null) {return;}
        effectLengthTicks = 40;
        attackKnockbackStrength = 0;
        damageMultiplier = 1;
        petEffects = new ArrayList<>();
        targetEffects = new ArrayList<>();
        petParticles = new ArrayList<>();
        targetParticles = new ArrayList<>();
        assignedPet = petToUse;
        updateChance();
    }
}
