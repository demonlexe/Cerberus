package lzxus.cerberus.petdata;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.hologram.Hologram;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Pet extends PetData{

    private void initalizeKeys()
    {
        if (data.get(initalizedKey, PersistentDataType.INTEGER) == null)
        {
            data.set(initalizedKey,PersistentDataType.INTEGER,1);
            ResetFunctions.initializeP(p,this); //extended from PlayerReset
        }
    }

    private void updateKeys()
    {
        uniqueIDKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-uuid");
        xpKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-xp");
        levelKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-level");
        aliveKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-status");
        ownedKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-owned");
        nameKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-name");
        damageKey = new NamespacedKey(Cerberus.getPlugin(), "damageEnabled");
        statusKey = new NamespacedKey(Cerberus.getPlugin(), "attackStatus");
        attackKey = new NamespacedKey(Cerberus.getPlugin(), "attackType");
        initalizedKey = new NamespacedKey(Cerberus.getPlugin(),"initalizedStatus");
    }

    public Pet (Player pl)
    {
        super(pl);
        updateKeys();
        initalizeKeys();
    }

    public Pet (Wolf w,Player pl)
    {
        super(w,pl);
        updateKeys();
    }

    public void onLevelUp()
    {
        if (w!=null && p!=null)
        {
            World wWorld = w.getWorld();
            Location loc = w.getLocation();
            wWorld.spawnParticle(Particle.REDSTONE, loc.getX(),loc.getY(),loc.getZ(),45, .5,.7,.5,new Particle.DustOptions(cData.newColor, (float) 1.3));
        }
    }

    public void onXPGained(String gainedXP)
    {
        if (w!=null) {
            if (p != null) {
                Hologram h = new Hologram(this, "+ "+gainedXP+" XP", cData.successColor,w);

                Bukkit.getScheduler().runTaskLater(Cerberus.getPlugin(), () -> {
                    h.removeHologram();
                    attackChoice();
                }, 15L);
                //p.sendMessage(cData.successColor+"Your pet has gained " + gainedXP + " XP!");
            }
        }
    }

    public void updateStats(Integer currentLevel){
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
                Pet pet = Cerberus.obtainPetData(p);
                if (pet!= null && pet.getWolf().equals(w))
                    w.setCustomName(pet.getWolfName());
            }
        }
    }
}
