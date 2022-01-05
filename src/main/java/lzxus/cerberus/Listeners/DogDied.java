package lzxus.cerberus.Listeners;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.PetData;
import lzxus.cerberus.Structs.PlayerReset;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class DogDied implements Listener {
    @EventHandler
    public static void onDeath(EntityDeathEvent event)
    {
        if (event.getEntity() instanceof Wolf)
        {
            Wolf w = (Wolf) event.getEntity();
            Player p = (Player) w.getOwner();
            if (p != null)
            {
                PetData pet = Cerberus.obtainPetData(p);
                if (pet!= null && pet.getWolf().equals(w))
                    pet.getResetFunctions().resetP(p,pet);
            }
        }
    }
}
