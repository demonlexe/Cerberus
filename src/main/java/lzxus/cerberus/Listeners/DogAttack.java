package lzxus.cerberus.Listeners;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.petdata.Pet;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;


public class DogAttack implements Listener {
    @EventHandler
    public static void onPlayerMoved(PlayerMoveEvent event)
    {
        //System.out.println("Cerberus: Testing position 4");
        Player p = event.getPlayer();
        Pet pet = Cerberus.obtainPetData(p);
        Wolf w;
        if (pet != null && pet.getWolf() != null)
        {
            w = pet.getWolf();
            //System.out.println("Cerberus: Testing position 3"+p);
            if (w!= null)
            {
                for (Entity entityFound : p.getNearbyEntities(5.0,5.0,5.0))
                {
                    //System.out.println("Cerberus: Testing position 2");
                    if (entityFound instanceof LivingEntity)
                    {
                        pet.enQueue(entityFound);
                    }
                }
            }
        }
    }
}
