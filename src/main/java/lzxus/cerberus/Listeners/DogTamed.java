package lzxus.cerberus.Listeners;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.PetData;
import lzxus.cerberus.Structs.PlayerReset;
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
    private static final PlayerReset pReset = new PlayerReset();

    @EventHandler
    public void onAnimalTamed(EntityTameEvent event) throws IOException {
        if (event.getEntity() instanceof Wolf && event.getOwner() instanceof Player)
        {
            Wolf tamedWolf = (Wolf) event.getEntity();
            Player tamer = (Player) event.getOwner();
            PetData pet = Cerberus.obtainPetData(tamer);
            if (pet!=null)
            {
                if (pet.getWolfOwned().equals(1))
                {
                    tamer.sendMessage(ChatColor.BLUE+"You already have a pet!");
                    return;
                }
                else
                {
                    PetData newData = new PetData(tamedWolf,tamer);
                    pReset.newP(tamer,newData,tamedWolf);
                    Cerberus.updateWolfList(newData,tamer,"PetAdded");
                }
            }
            //System.out.println("A wolf has been tamed by "+tamer.getName());
        }
    }
}
