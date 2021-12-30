package lzxus.cerberus;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

// Event listener for adding XP to a wolf.
public class EntityDamaged implements Listener {
    FileConfiguration config = Cerberus.obtainConfig();
    double [] xpList = null;

    public void updateLevel(Wolf w, Player p, PersistentDataContainer data, NamespacedKey xpKey, NamespacedKey lvlKey)
    {
        if (xpList == null)
        {
            xpList = Cerberus.obtainXPList();
            System.out.println("Obtained XP List . . .");
        }
        if (w != null && p != null && p.isOnline())
        {
            Double currentXP = data.get(xpKey,PersistentDataType.DOUBLE);
            Integer currentLevel = data.get(lvlKey,PersistentDataType.INTEGER);
            System.out.println("Obtaining Data: CurrentXP is "+currentXP+" while currentLevel is "+currentLevel);
            if ((currentLevel != null) && (currentXP != null) && (xpList[currentLevel+1] <= currentXP))
            {
                currentLevel++;
                data.set(lvlKey,PersistentDataType.INTEGER, currentLevel);
                p.sendMessage(ChatColor.GREEN + "Your pet has levelled up! It is now Level "+currentLevel);
                ModifyPetStats.updateStats(w,currentLevel);
            }
        }

    }

    @EventHandler
    public void onDamaged(EntityDamageByEntityEvent e)
    {
        if (e.getDamager() instanceof Wolf)
        {
            Wolf w = (Wolf) e.getDamager();
            if (w.getOwner() != null)
            {
                Player p = (Player) w.getOwner();
                double damageDone = e.getFinalDamage();

                PersistentDataContainer data = p.getPersistentDataContainer();
                NamespacedKey xpKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-xp");
                NamespacedKey lvlKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-level");

                Double prevXp = data.get(xpKey, PersistentDataType.DOUBLE);
                if (prevXp != null)
                {
                    data.set(xpKey, PersistentDataType.DOUBLE, prevXp+damageDone);
                    System.out.println("Total XP of this wolf: "+data.get(xpKey, PersistentDataType.DOUBLE));
                    updateLevel(w, p, data, xpKey, lvlKey);
                }
                }
        }
    }
}
