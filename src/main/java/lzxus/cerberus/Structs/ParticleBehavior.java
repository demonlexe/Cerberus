package lzxus.cerberus.Structs;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.PetData;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import java.util.ArrayList;
import java.util.List;

public class ParticleBehavior {
    private static Color newColor = null;

    public static void onLevelUp(Wolf w)
    {
        if (newColor == null){
            newColor = ConfigFunctions.getColor("successColor");
        }

        if (w!=null)
        {
            Player p = (Player) (w).getOwner();
            PetData pet = Cerberus.obtainPetData(p);
            if (p!=null && pet != null && pet.getWolf().equals(w))
            {
                World wWorld = w.getWorld();
                Location loc = w.getLocation();
                wWorld.spawnParticle(Particle.REDSTONE, loc.getX(),loc.getY(),loc.getZ(),45, .5,.7,.5,new Particle.DustOptions(newColor, (float) 1.3));
            }
        }
    }
}
