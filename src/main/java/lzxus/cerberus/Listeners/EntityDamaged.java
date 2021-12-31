package lzxus.cerberus.Listeners;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.*;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

// Event listener for adding XP to a wolf.
public class EntityDamaged implements Listener {
    private static String failColor = null;
    private static String successColor = null;
    private static String dataColor = null;
    private static FileConfiguration config = Cerberus.obtainConfig();
    private static double [] xpList = null;
    private static Integer maxLevel = null;

    public void updateLevel(Wolf w, Player p)
    {
        if (xpList == null)
        {
            xpList = Cerberus.obtainXPList();
            //System.out.println("Cerberus: Obtained XP List . . .");
            maxLevel = xpList.length-1;
        }

        if (failColor == null){
            failColor = ConfigFunctions.getChatColor("failureChatColor");
        }
        if (successColor == null){
            successColor = ConfigFunctions.getChatColor("successChatColor");
        }
        if (dataColor == null){
            dataColor = ConfigFunctions.getChatColor("dataChatColor");
        }

        if (w != null && p != null && p.isOnline())
        {
            Double currentXP = PlayerWolfData.getWolfXp(p);
            Integer currentLevel = PlayerWolfData.getWolfLvl(p);
            //System.out.println("Cerberus: Obtaining Data: CurrentXP is "+currentXP+" while currentLevel is "+currentLevel);
            if ((currentLevel != null) && (currentXP != null) && (currentLevel < maxLevel) &&(xpList[currentLevel+1] <= currentXP))
            {
                currentLevel++;
                PlayerWolfData.setWolfLvl(p,currentLevel);
                ParticleBehavior.onLevelUp(w);
                p.sendMessage(successColor + "Your pet has leveled up! It is now " +dataColor+"Level "+currentLevel);
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
                            //System.out.println("Total XP of this wolf: "+PlayerWolfData.getWolfXp(p));
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
