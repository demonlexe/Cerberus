package lzxus.cerberus.petdata;

import lzxus.cerberus.attacks.SpecialAttack;
import lzxus.cerberus.structs.PlayerReset;
import lzxus.cerberus.configdata.ConfigData;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public abstract class PetData {
    protected Wolf w;
    protected Player p;
    protected PersistentDataContainer data;
    protected Deque<Entity> attackQueue;
    protected NamespacedKey uniqueIDKey;
    protected NamespacedKey xpKey;
    protected NamespacedKey levelKey;
    protected NamespacedKey aliveKey;
    protected NamespacedKey ownedKey;
    protected NamespacedKey nameKey;
    protected NamespacedKey damageKey;
    protected NamespacedKey statusKey;
    protected NamespacedKey attackKey;
    protected NamespacedKey initalizedKey;
    protected ConfigData cData;
    protected PlayerReset ResetFunctions;
    protected ArrayList<SpecialAttack> specialAttacks;

    public PetData(Player pl) //Called if player does not own a pet.
    {
        w = null;
        p = pl;
        data = p.getPersistentDataContainer();
        cData = new ConfigData();
        ResetFunctions = new PlayerReset();
    }

    public PetData(Wolf newWolf, Player pl) //Called if a player owns a pet.
    {
        w = newWolf;
        p = pl;
        data = p.getPersistentDataContainer();
        cData = new ConfigData();
        ResetFunctions = new PlayerReset();
        attackQueue = new LinkedList<>();
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
        if ((e instanceof Player) && !(cData.getPvPAllowed())) {return false;}

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
                    else if (attackMode.equals("animals"))
                    {
                        if (e instanceof Animals)
                        {
                            return true;
                        }
                    }
                    else if (attackMode.equals("creature"))
                    {
                        if (e instanceof Creature)
                        {
                            return true;
                        }
                    }
                    else if (attackMode.equals("raider"))
                    {
                        if (e instanceof Raider)
                        {
                            return true;
                        }
                    }
                    else if (attackMode.equals("watermob"))
                    {
                        if (e instanceof WaterMob)
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
