package lzxus.cerberus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DogBehavior implements Listener {

    // A static member function that resets anger and sit values
    public static void resetWolf(Wolf playerWolf)
    {
        playerWolf.setAngry(false);
        playerWolf.setSitting(false);
    }
    // A static member function that makes a wolf angry
    public static void angerWolf(Wolf playerWolf)
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

        PersistentDataContainer data = p.getPersistentDataContainer();

        //If a wolf is clicked by the player with a bone
        if (entityClicked.getType() == EntityType.WOLF && itemInHand == Material.BONE)
        {
            Wolf playerDog = (Wolf) entityClicked;
            //If player is the owner, changes this dog to be main pet;
            if (playerDog.isTamed() && playerDog.getOwner().getName() == p.getName())
            {
                p.sendMessage(ChatColor.BLUE+"This dog is your main pet!");
            }
            else
            {
                NamespacedKey ownedKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-owned");
                Integer ownedValue = data.get(ownedKey,PersistentDataType.INTEGER);
                if (ownedValue != null && ownedValue.equals(1))
                {
                    event.setCancelled(true);
                    p.sendMessage(ChatColor.BLUE+"You already have a pet!");
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
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey damageKey = new NamespacedKey(Cerberus.getPlugin(), "damageEnabled");
        Integer dataObtained = data.get(damageKey, PersistentDataType.INTEGER);
        //If the attacker is not its owner, the damage does not occur and the wolf is angered.
        //System.out.println("Owner: " + damagedDog.getOwner().getName() + ", wolf is tamed: " + damagedDog.isTamed() + ", damager: " + damager.getName());
            if (damagedDog.isTamed() && damagedDog.getOwner().getName() != p.getName()) {
                event.setCancelled(true);
                angerWolf(damagedDog);
            }
            //If attacker is the owner, and has pet attacking disabled, cancel
            else if (damagedDog.isTamed() && damagedDog.getOwner().getName() == damager.getName()) {
                if (!dataObtained.equals(1)) {
                    event.setCancelled(true);
                }
            }

        }
}
