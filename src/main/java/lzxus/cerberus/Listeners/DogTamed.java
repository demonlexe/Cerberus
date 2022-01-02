package lzxus.cerberus.Listeners;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.PetData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;

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
            PetData pet = Cerberus.obtainPetData(tamer);
            if (pet!=null)
            {
                if (!(tamedWolf).isAdult() || pet.getWolfOwned().equals(1))
                {
                    event.setCancelled(true);
                    tamer.sendMessage(ChatColor.BLUE+"You already have a pet!");
                    return;
                }
                //Creates Boolean data
                pet.setWolfOwned(1);//true
                pet.setWolfStatus(1); //true

                //Creates Level data
                pet.setWolfLvl(0);

                //Creates XP data
                pet.setWolfXp(0.0);

                //Creates Attack data
                pet.setAttackStatus(1);
                pet.setAttackType("m");

                //Creates Name data
                pet.setWolfName("");

                //Creates UUID data
                pet.setWolfUUID(tamedWolf.getUniqueId().toString());
                PetData newData = new PetData(tamedWolf,tamer);
                Cerberus.updateWolfList(newData,tamer,"PetAdded");
            }
            //System.out.println("A wolf has been tamed by "+tamer.getName());
        }
    }
}
