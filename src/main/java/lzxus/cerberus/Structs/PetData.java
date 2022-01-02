package lzxus.cerberus.Structs;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Listeners.DogBehavior;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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

    private static final String [] attackTypeList = {"a","p","m"};

    private void initalizeKeys()
    {
        if (data.get(initalizedKey,PersistentDataType.INTEGER) == null)
        {
            data.set(initalizedKey,PersistentDataType.INTEGER,1);
            PlayerReset.initializeP(p,this);
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

    public PetData() //Should not be used.
    {
        w = null;
        p = null;
        data = null;
        attackQueue = new LinkedList<Entity>();
        updateKeys();
        initalizeKeys();
    }

    public PetData(Player pl) //Called if player does not own a pet.
    {
        w = null;
        p = pl;
        data = p.getPersistentDataContainer();
        updateKeys();
        initalizeKeys();
    }

    public PetData(Wolf newWolf, Player pl) //Called if a player owns a pet.
    {
        w = newWolf;
        p = pl;
        data = p.getPersistentDataContainer();
        attackQueue = new LinkedList<Entity>();
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

    public void enQueue(Entity e)
    {
        if (isAllowedToAttack(e) && w.getTarget()!=e)
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
            DogBehavior.attackChoice(this);
        }
    }

    public void enQueueFirst(Entity e)
    {
        if (isAllowedToAttack(e) && w.getTarget()!=e)
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
            DogBehavior.attackChoice(this);
        }
    }

    public Entity peekQueue()
    {
        if (attackQueue.peek() != null)
        {
            Entity toR = attackQueue.peekFirst();
            while (toR == null || toR.isDead()){
                if (attackQueue.isEmpty())
                    return null;
                toR = attackQueue.pollFirst();
            }
            return toR;
        }
        else
            return null;
    }

    //Static methods
    public static String [] getTypeList(){
        return attackTypeList;
    }

    //Non-Static methods
    public boolean isAllowedToAttack(Entity e)
    {
        if (p != null && !(e.equals(p)))
        {
            Integer attackAllowed = getAttackStatus();
            if (attackAllowed != null && attackAllowed.equals(1))
            {
                String attackMode = getAttackType();
                if (ArrayUtils.contains(attackTypeList,attackMode))
                {
                    if (attackMode.equals("m"))
                    {
                        if (e instanceof Monster)
                        {
                            return true;
                        }
                    }
                    else if (attackMode.equals("p"))
                    {
                        if (e instanceof Animals)
                        {
                            return true;
                        }
                    }
                    else if (attackMode.equals("a"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Player isPlayerPet()
    {
        return p;
    }
    //Getter functions
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
