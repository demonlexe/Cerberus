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

            //Creates Boolean data
            PlayerWolfData.setWolfOwned(tamer,1);//true
            PlayerWolfData.setWolfStatus(tamer,1); //true

            //Creates Level data
            PlayerWolfData.setWolfLvl(tamer,0);

            //Creates XP data
            PlayerWolfData.setWolfXp(tamer,0.0);

            //Creates Name data
            PlayerWolfData.setWolfName(tamer,"");

            //Creates UUID data
            PlayerWolfData.setWolfUUID(tamer,tamedWolf.getUniqueId().toString());
            Cerberus.updateWolfList(tamedWolf,tamer,true);
        }
    }
}