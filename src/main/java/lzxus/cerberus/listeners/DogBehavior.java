package lzxus.cerberus.listeners;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.petdata.Pet;
import lzxus.cerberus.structs.PlayerReset;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.PlayerInventory;

public class DogBehavior implements Listener {
    private static final PlayerReset pReset = new PlayerReset();

    // A static member function that resets anger and sit values
    private static void resetWolf(Wolf playerWolf)
    {
        playerWolf.setAngry(false);
        playerWolf.setSitting(false);
    }
    // A static member function that makes a wolf angry
    private static void angerWolf(Wolf playerWolf)
    {
        playerWolf.setAngry(true);
        playerWolf.setSitting(false);
    }

    // An event function that operates upon an animal being interacted.
    // It then checks if it is of EntityType.WOLF and
    // the item interacted with is a bone.
    @EventHandler
    public void onAnimalClicked(PlayerInteractEntityEvent event)
    {
        Player p = event.getPlayer();
        PlayerInventory pInv = p.getInventory();
        Material itemInHand = pInv.getItemInMainHand().getType();
        Entity entityClicked = event.getRightClicked();

        //If a wolf is clicked by the player with a bone
        if (entityClicked.getType() == EntityType.WOLF && itemInHand == Material.BONE)
        {
            Wolf playerDog = (Wolf) entityClicked;
            Pet pet = Cerberus.obtainPetData(p);
            if (pet==null) { return;}
            //If player is the owner, changes this dog to be main pet;
            if (playerDog.isTamed() && playerDog.getOwner().getName() == p.getName())
            {
                if (pet.getWolfOwned().equals(1))
                {
                    if (pet.getWolf().equals(playerDog))
                    {
                        p.sendMessage(ChatColor.GREEN+"This is your main pet! To view statistics, type"+ChatColor.BLUE+" /ce stats");
                    }
                    else
                    {
                        p.sendMessage(ChatColor.GREEN+"You already own a pet! To reset and claim a new pet,"
                                +"\n"+"type"+ChatColor.BLUE+" /ce reset");
                    }

                }
                else
                {
                    Pet newData = new Pet(playerDog,p);
                    pReset.newP(p,newData,playerDog);
                    Cerberus.updateWolfList(newData,p,"PetAdded");
                }
            }
            else if (playerDog.isTamed())
            {
                Integer ownedValue = pet.getWolfOwned();
                if (ownedValue != null && ownedValue.equals(1))
                {
                    event.setCancelled(true);
                    p.sendMessage(ChatColor.RED+"This is not your pet!");
                }
            }
        }
    }

    // An event function that operates upon an animal being attacked.
    // It then checks if it is of EntityType.WOLF and the attacker is not its owner.
    @EventHandler
    public void onAnimalAttacked(EntityDamageByEntityEvent event)
    {
        Entity damager = event.getDamager();
        Entity damagee = event.getEntity();
        Player p;
        Wolf damagedDog;
        if (damager instanceof Player && damagee.getType()==EntityType.WOLF)
        {
            p = (Player) damager;
            damagedDog = (Wolf) damagee;
        }
        else
        {
            return;
        }

        Pet pet = Cerberus.obtainPetData(p);
        if (pet==null) {return;}
        Integer dataObtained = pet.getDamageEnabled();
        //If the attacker is not its owner, the damage does not occur and the wolf is angered.
        //System.out.println("Owner: " + damagedDog.getOwner().getName() + ", wolf is tamed: " + damagedDog.isTamed() + ", damager: " + damager.getName());
            if (damagedDog.isTamed() && damagedDog.getOwner().getName() != p.getName()) {
                event.setCancelled(true);
            }
            //If attacker is the owner, and has pet attacking disabled, cancel
            else if (damagedDog.isTamed() && damagedDog.getOwner().getName() == damager.getName()) {
                if (!dataObtained.equals(1)) {
                    event.setCancelled(true);
                }
            }

        }
}
