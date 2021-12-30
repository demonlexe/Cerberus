package lzxus.cerberus;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

//Contains functions related to data
public class PlayerWolfData {

    //Boolean functions
    public static Player isPlayerPet(Wolf w)
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (Cerberus.obtainFromWolfList(p).equals(w))
            {
                return p;
            }
        }
        return null;
    }


    //Getter functions
    public static String getWolfUUID(Player p)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey uniqueIDKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-uuid");
        String toR = data.get(uniqueIDKey, PersistentDataType.STRING);
        return toR;
    }

    public static Integer getWolfOwned(Player p)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey ownedKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-owned");
        Integer toR = data.get(ownedKey, PersistentDataType.INTEGER);
        return toR;
    }

    public static Integer getWolfLvl(Player p)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey levelKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-level");
        Integer toR = data.get(levelKey, PersistentDataType.INTEGER);
        return toR;
    }

    public static Double getWolfXp(Player p)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey xpKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-xp");
        Double toR = data.get(xpKey, PersistentDataType.DOUBLE);
        return toR;
    }

    public static Integer getDamageEnabled(Player p)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey damageKey = new NamespacedKey(Cerberus.getPlugin(), "damageEnabled");
        Integer toR = data.get(damageKey, PersistentDataType.INTEGER);
        return toR;
    }

    public static String getWolfName(Player p)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey nameKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-name");
        String toR = data.get(nameKey, PersistentDataType.STRING);
        return toR;
    }

    public static Integer getWolfStatus(Player p)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey aliveKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-status");
        Integer toR = data.get(aliveKey,PersistentDataType.INTEGER);
        return toR;
    }

    public static Double getWolfHealth(Player p)
    {
        Wolf w = Cerberus.obtainFromWolfList(p);
        if (w!=null)
        {
            return w.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        }
        else
        {
            return null;
        }
    }
    public static Double getCurrentWolfHealth(Player p)
    {
        Wolf w = Cerberus.obtainFromWolfList(p);
        if (w!=null)
        {
            return w.getHealth();
        }
        else
        {
            return null;
        }
    }

    public static Double getWolfDamage(Player p)
    {
        Wolf w = Cerberus.obtainFromWolfList(p);
        if (w!=null)
        {
            return w.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue();
        }
        else
        {
            return null;
        }
    }

    public static String getAttackType(Player p)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey attackKey = new NamespacedKey(Cerberus.getPlugin(), "attackType");
        String toR = data.get(attackKey,PersistentDataType.STRING);
        return toR;
    }

    public static Integer getAttackStatus(Player p)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey statusKey = new NamespacedKey(Cerberus.getPlugin(), "attackStatus");
        Integer toR = data.get(statusKey,PersistentDataType.INTEGER);
        return toR;
    }

    //Setter functions
    public static void setAttackType(Player p, String s)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey attackKey = new NamespacedKey(Cerberus.getPlugin(), "attackType");
        data.set(attackKey,PersistentDataType.STRING,s);
    }

    public static void setAttackStatus(Player p, Integer val)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey statusKey = new NamespacedKey(Cerberus.getPlugin(), "attackStatus");
        data.set(statusKey,PersistentDataType.INTEGER,val);
    }

    public static void setDamageEnabled(Player p, int val)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey damageKey = new NamespacedKey(Cerberus.getPlugin(), "damageEnabled");
        data.set(damageKey,PersistentDataType.INTEGER,val);
    }

    public static void setWolfName(Player p, String s)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey nameKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-name");
        data.set(nameKey, PersistentDataType.STRING, s);
    }

    public static void setWolfOwned(Player p, int val)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey ownedKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-owned");
        data.set(ownedKey, PersistentDataType.INTEGER,val);
    }

    public static void setWolfStatus(Player p, int val)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey aliveKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-status");
        data.set(aliveKey,PersistentDataType.INTEGER, val); //true
    }

    public static void setWolfLvl(Player p, int val)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey levelKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-level");
        data.set(levelKey, PersistentDataType.INTEGER, val);
    }

    public static void setWolfXp(Player p, double val)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey xpKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-xp");
        data.set(xpKey, PersistentDataType.DOUBLE, val);
    }

    public static void setWolfUUID(Player p, String s)
    {
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey uniqueIDKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-uuid");
        data.set(uniqueIDKey, PersistentDataType.STRING, s);
    }
}
