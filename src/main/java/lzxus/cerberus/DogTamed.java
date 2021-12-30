package lzxus.cerberus;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.naming.Name;
import java.io.File;
import java.io.IOException;

public class DogTamed implements Listener {
    // An event function that operates upon an animal being tamed.
    // It then checks if it is of EntityType.WOLF

    @EventHandler
    public void onAnimalTamed(EntityTameEvent event) throws IOException {
        if (event.getEntity() instanceof Wolf && event.getOwner() instanceof Player)
        {
            //FIXME: Add check to make sure player has a value of 0 for wolf owned
            Wolf tamedWolf = (Wolf) event.getEntity();
            Player tamer = (Player) event.getOwner();
            //System.out.println("A wolf has been tamed by "+tamer.getName());
            PersistentDataContainer data = tamer.getPersistentDataContainer();

            //Creates Boolean data
            NamespacedKey ownedKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-owned");
            data.set(ownedKey,PersistentDataType.INTEGER, 1); //true
            NamespacedKey aliveKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-status");
            data.set(aliveKey,PersistentDataType.INTEGER, 1); //true

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
            data.set(uniqueIDKey, PersistentDataType.STRING, tamedWolf.getUniqueId().toString());
            Cerberus.updateWolfList(tamedWolf,tamer,true);
        }
    }
}
