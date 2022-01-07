package lzxus.cerberus.hologram;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.petdata.Pet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

public class Hologram {
    private ArmorStand standEntity;
    private Pet data;

    public Hologram(Pet pet, String displayName, String displayColor, Entity ToTeleport){
        data = pet;
        Player p = pet.isPlayerPet();
        World w = p.getWorld();
        Wolf pWolf = pet.getWolf();
        if (w.equals(pWolf.getWorld()))
        {
            Location awayLoc = new Location(w, 0,-200,0);
            standEntity = (ArmorStand) w.spawnEntity(awayLoc, EntityType.ARMOR_STAND);
            standEntity.setVisible(false);
            standEntity.setCustomNameVisible(true);
            standEntity.setCustomName(displayColor + displayName);
            standEntity.setGravity(true);
            standEntity.setRemoveWhenFarAway(true);
            Bukkit.getScheduler().runTaskLater(Cerberus.getPlugin(), () -> {
               // System.out.println("Hologram: Teleporting hologram.");
                Location newLoc = ToTeleport.getLocation();
                newLoc.setY(newLoc.getY()-1.2);
                standEntity.setVelocity(new Vector(0,.4,0));
                standEntity.teleport(newLoc);
            }, 3L);
        }
    }

    public void removeHologram(){
        //System.out.println("Hologram: Removing hologram.");
        standEntity.remove();
    }
}
