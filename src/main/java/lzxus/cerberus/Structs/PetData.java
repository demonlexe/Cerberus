package lzxus.cerberus.Structs;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Listeners.DogBehavior;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import javax.xml.stream.events.Namespace;
import java.util.Deque;
import java.util.LinkedList;

public class PetData {
    private Wolf w;
    private Player p;
    private PersistentDataContainer data;
    private Deque<Entity> attackQueue;
    private NamespacedKey uniqueIDKey;
    private NamespacedKey xpKey;
    private NamespacedKey levelKey;
    private NamespacedKey aliveKey;
    private NamespacedKey ownedKey;
    private NamespacedKey nameKey;
    private NamespacedKey damageKey;
    private NamespacedKey statusKey;
    private NamespacedKey attackKey;
    private NamespacedKey initalizedKey;
    private ConfigData cData;
    private PlayerReset ResetFunctions;

    private void initalizeKeys()
    {
        if (data.get(initalizedKey,PersistentDataType.INTEGER) == null)
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

    public PetData(Player pl) //Called if player does not own a pet.
    {
        w = null;
        p = pl;
        data = p.getPersistentDataContainer();
        cData = new ConfigData();
        ResetFunctions = new PlayerReset();
        updateKeys();
        initalizeKeys();
    }

    public PetData(Wolf newWolf, Player pl) //Called if a player owns a pet.
    {
        w = newWolf;
        p = pl;
        data = p.getPersistentDataContainer();
        cData = new ConfigData();
        ResetFunctions = new PlayerReset();
        attackQueue = new LinkedList<>();
        updateKeys();
    }

    public Wolf getWolf()
    {
        return w;
    }
    public void setWolf(Wolf newWolf)
    {
        w = newWolf;
    }
    public PlayerReset getResetFunctions() {return ResetFunctions;}

    public Deque<Entity> getQueue(){
        return attackQueue;
    }

    public boolean checkQueueForEntity(Entity e)
    {
        if (attackQueue.contains(e))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean clearQueue()
    {
        attackQueue = new LinkedList<>();
        return true;
    }

    public void enQueue(Entity e)
    {
        Entity peekedEnt = peekQueue();
        if (peekedEnt != null && peekedEnt.equals(e)) {
            return;
        }
        if (isAllowedToAttack(e))
        {
            if (checkQueueForEntity(e))
            {
                attackQueue.remove(e);
            }
            while (attackQueue.size() > 20)
            {
                attackQueue.pollLast();
            }
            attackQueue.addLast(e);
            attackChoice();
        }
    }

    public void enQueueFirst(Entity e)
    {
        Entity peekedEnt = peekQueue();
        if (peekedEnt != null && peekedEnt.equals(e)) {
            return;
        }
        if (isAllowedToAttack(e))
        {
            if (checkQueueForEntity(e))
            {
                attackQueue.remove(e);
            }

            while (attackQueue.size() > 20)
            {
                attackQueue.pollLast();
            }
            attackQueue.addFirst(e);
            attackChoice();
        }
    }

    public boolean withinDistance(Entity e)
    {
        if (e.getWorld().equals(p.getWorld()))
        {
            Location pLoc = p.getLocation();
            Location eLoc = e.getLocation();
            if (pLoc.distance(eLoc) < 15.0)
            {
                return true;
            }
        }
        return false;
    }

    public Entity peekQueue()
    {
        if (attackQueue.peek() != null)
        {
            Entity toR = attackQueue.peekFirst();
            while (toR == null || toR.isDead() || !withinDistance(toR)){
                if (attackQueue.isEmpty())
                    return null;
                toR = attackQueue.pollFirst();
            }
            return toR;
        }
        else
            return null;
    }

    //Non-Static methods
    public boolean isAllowedToAttack(Entity e)
    {
        if (p != null && !e.equals(p) && !(e instanceof Wolf))
        {
            Integer attackAllowed = getAttackStatus();
            if (attackAllowed != null && attackAllowed.equals(1))
            {
                String attackMode = getAttackType();
                if (ArrayUtils.contains(cData.attackTypeList,attackMode))
                {
                    if (attackMode.equals("monster"))
                    {
                        if (e instanceof Monster)
                        {
                            return true;
                        }
                    }
                    else if (attackMode.equals("passive"))
                    {
                        if (e instanceof Animals)
                        {
                            return true;
                        }
                    }
                    else if (attackMode.equals("all"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void attackChoice()
    {
        if (w != null)
        {
            Entity eChosen = peekQueue();
            if (eChosen != null)
            {
                w.setTarget((LivingEntity) eChosen);
            }
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

    public void onLevelUp()
    {
        if (w!=null && p!=null)
        {
            World wWorld = w.getWorld();
            Location loc = w.getLocation();
            wWorld.spawnParticle(Particle.REDSTONE, loc.getX(),loc.getY(),loc.getZ(),45, .5,.7,.5,new Particle.DustOptions(cData.newColor, (float) 1.3));
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
                PetData pet = Cerberus.obtainPetData(p);
                if (pet!= null && pet.getWolf().equals(w))
                    w.setCustomName(pet.getWolfName());
            }
        }
    }

    //Getter functions
    public Player isPlayerPet()
    {
        return p;
    }

    public String getWolfUUID()
    {
        return data.get(uniqueIDKey, PersistentDataType.STRING);
    }

    public Integer getWolfOwned()
    {
        return data.get(ownedKey, PersistentDataType.INTEGER);
    }

    public Integer getWolfLvl()
    {
        return data.get(levelKey, PersistentDataType.INTEGER);
    }

    public Double getWolfXp()
    {
        return data.get(xpKey, PersistentDataType.DOUBLE);
    }

    public Integer getDamageEnabled()
    {
        return data.get(damageKey, PersistentDataType.INTEGER);
    }

    public String getWolfName()
    {
        return data.get(nameKey, PersistentDataType.STRING);
    }

    public Double getWolfHealth()
    {
        if (w!=null)
        {
            AttributeInstance a = w.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (a != null)
            {
                return (a.getBaseValue());
            }
        }
        return null;
    }
    public Double getCurrentWolfHealth()
    {
        if (w!=null)
        {
            return w.getHealth();
        }
        else
        {
            return null;
        }
    }

    public Double getWolfDamage()
    {
        if (w!=null)
        {
            AttributeInstance a = w.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
            if (a != null)
            {
                return (a.getBaseValue());
            }
        }
        return null;
    }

    public Integer getWolfStatus()
    {
        return data.get(aliveKey,PersistentDataType.INTEGER);
    }

    public String getAttackType()
    {
        return data.get(attackKey,PersistentDataType.STRING);
    }

    public Integer getAttackStatus()
    {
        return data.get(statusKey,PersistentDataType.INTEGER);
    }

    //Setter functions
    public void setAttackType(String s)
    {
        data.set(attackKey,PersistentDataType.STRING,s);
    }

    public void setAttackStatus(Integer val)
    {
        data.set(statusKey,PersistentDataType.INTEGER,val);
    }

    public void setDamageEnabled(int val)
    {
        data.set(damageKey,PersistentDataType.INTEGER,val);
    }

    public void setWolfName(String s)
    {
        data.set(nameKey, PersistentDataType.STRING, s);
    }

    public void setWolfOwned(int val)
    {
        data.set(ownedKey, PersistentDataType.INTEGER,val);
    }

    public void setWolfStatus(int val)
    {
        data.set(aliveKey,PersistentDataType.INTEGER, val);
    }

    public void setWolfLvl(int val)
    {
        data.set(levelKey, PersistentDataType.INTEGER, val);
    }

    public void setWolfXp(double val)
    {
        data.set(xpKey, PersistentDataType.DOUBLE, val);
    }

    public void setWolfUUID(String s)
    {
        data.set(uniqueIDKey, PersistentDataType.STRING, s);
    }

}
