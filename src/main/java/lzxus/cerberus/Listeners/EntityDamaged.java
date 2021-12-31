package lzxus.cerberus.Listeners;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.ModifyPetStats;
import lzxus.cerberus.Structs.PetData;
import lzxus.cerberus.Structs.PlayerWolfData;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

// Event listener for adding XP to a wolf.
public class EntityDamaged implements Listener {
    FileConfiguration config = Cerberus.obtainConfig();
    double [] xpList = null;
    Integer maxLevel = null;
    public void updateLevel(Wolf w, Player p)
    {
        if (xpList == null)
        {
            xpList = Cerberus.obtainXPList();
            System.out.println("Cerberus: Obtained XP List . . .");
            maxLevel = xpList.length-1;
        }
        if (w != null && p != null && p.isOnline())
        {
            Double currentXP = PlayerWolfData.getWolfXp(p);
            Integer currentLevel = PlayerWolfData.getWolfLvl(p);
            System.out.println("Cerberus: Obtaining Data: CurrentXP is "+currentXP+" while currentLevel is "+currentLevel);
            if ((currentLevel != null) && (currentXP != null) && (currentLevel < maxLevel) &&(xpList[currentLevel+1] <= currentXP))
            {
                currentLevel++;
                PlayerWolfData.setWolfLvl(p,currentLevel);
                p.sendMessage(ChatColor.GREEN + "Your pet has leveled up! It is now Level "+currentLevel);
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
                Integer allowedToAttack = null;
                if (p != null) {
                    allowedToAttack = PlayerWolfData.getAttackStatus(p);
                    if (allowedToAttack.equals(1) && PetData.isAllowedToAttack(w,e.getEntity()))
                    {
                        double damageDone = e.getFinalDamage();

                        Double prevXp = PlayerWolfData.getWolfXp(p);
                        if (prevXp != null)
                        {
                            PlayerWolfData.setWolfXp(p,prevXp+damageDone);
                            System.out.println("Total XP of this wolf: "+PlayerWolfData.getWolfXp(p));
                            updateLevel(w, p);
                        }
                    }
                    else
                    {
                        e.setCancelled(true);
                    }
                }

            }
        }
    }
}
