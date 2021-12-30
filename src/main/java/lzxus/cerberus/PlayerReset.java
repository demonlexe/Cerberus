package lzxus.cerberus;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerReset {
    public static void resetP(Player p, Wolf w){
        PersistentDataContainer data = p.getPersistentDataContainer();

        //Creates Boolean data
        NamespacedKey ownedKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-owned");
        data.set(ownedKey, PersistentDataType.INTEGER, 0); //false
        NamespacedKey aliveKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-status");
        data.set(aliveKey,PersistentDataType.INTEGER, 0); //false

        //Creates Level data
        NamespacedKey levelKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-level");
        data.set(levelKey, PersistentDataType.INTEGER, 0);

        //Creates XP data
        NamespacedKey xpKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-xp");
        data.set(xpKey, PersistentDataType.DOUBLE, 0.0);

        //Creates Name data
        NamespacedKey nameKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-name");
        data.set(nameKey, PersistentDataType.STRING, "");

        //Creates UUID data
        NamespacedKey uniqueIDKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-uuid");
        data.set(uniqueIDKey, PersistentDataType.STRING, "");
        if (w!=null && p!= null)
        {
            Cerberus.updateWolfList(w,p,false);
        }
    }
}
