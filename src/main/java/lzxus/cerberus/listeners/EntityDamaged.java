package lzxus.cerberus.listeners;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.configdata.ConfigData;
import lzxus.cerberus.petdata.Pet;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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
    private static ConfigData cData = new ConfigData();
    private static double [] xpList = null;
    private static Integer maxLevel = null;



    public static void updateLevel(Wolf w, Player p)
    {
        if (xpList == null)
        {
            xpList = Cerberus.obtainXPList();
            //System.out.println("Cerberus: Obtained XP List . . .");
            maxLevel = xpList.length-1;
        }

        if (failColor == null){
            failColor = cData.getChatColor("failureChatColor");
        }
        if (successColor == null){
            successColor = cData.getChatColor("successChatColor");
        }
        if (dataColor == null){
            dataColor = cData.getChatColor("dataChatColor");
        }

        if (w != null && p != null && p.isOnline())
        {
            Pet pet = Cerberus.obtainPetData(p);
            if (pet==null) {return;}
            Double currentXP = pet.getWolfXp();
            Integer currentLevel = pet.getWolfLvl();
            Double currentHealth = pet.getCurrentWolfHealth();
            Double maxHealth = pet.getWolfHealth();
            //System.out.println("Cerberus: Obtaining Data: CurrentXP is "+currentXP+" while currentLevel is "+currentLevel);
            if ((currentLevel != null) && (currentXP != null) && (currentLevel < maxLevel) &&(xpList[currentLevel+1] <= currentXP))
            {
                currentLevel++;
                pet.setWolfLvl(currentLevel);
                pet.onLevelUp();
                p.sendMessage(successColor + "Your pet has leveled up! It is now " +dataColor+"Level "+currentLevel);
                pet.updateStats(currentLevel);
            }

            if (currentHealth / maxHealth < .5)
            {
                p.sendMessage(failColor+"Warning! Your pet is at low health!");
            }

        }

    }

    private static void isThreat(Entity damagedEntity, Entity attacker)
    {
            if (damagedEntity instanceof Player)
            {
                Player p = (Player) damagedEntity;
                Pet pet = Cerberus.obtainPetData(p);
                if (pet!= null && pet.getWolf() != null)
                {
                    pet.enQueueFirst(attacker);
                }
            }
            else if (damagedEntity instanceof Wolf)
            {
                Wolf w = (Wolf) damagedEntity;
                Player p = (Player) (w).getOwner();
                if (p!= null)
                {
                    Pet pet = Cerberus.obtainPetData(p);
                    if (pet!= null && pet.getWolf() != null)
                    {
                        pet.enQueueFirst(attacker);
                    }
                }
            }
    }

    @EventHandler
    public void onDamaged(EntityDamageByEntityEvent e)
    {
        isThreat(e.getEntity(),e.getDamager());
        if (e.getDamager() instanceof Wolf && e.getEntity() instanceof LivingEntity)
        {
            Wolf w = (Wolf) e.getDamager();
            if (w.getOwner() != null)
            {
                Player p = (Player) w.getOwner();
                if (p != null) {
                    Pet pet = Cerberus.obtainPetData(p);
                    if (pet.getWolf() != w) {return;}
                    Integer allowedToAttack = pet.getAttackStatus();
                    if (allowedToAttack.equals(1) && pet.isAllowedToAttack(e.getEntity()))
                    {
                        double damageDone = e.getFinalDamage();

                        Double prevXp = pet.getWolfXp();
                        if (prevXp != null)
                        {
                            pet.setWolfXp(prevXp+damageDone);
                            //System.out.println("Total XP of this wolf: "+PlayerWolfData.getWolfXp(p));
                            damageDone = damageDone*100;
                            int convertedDmg = (int)damageDone;
                            damageDone = (double)(convertedDmg/100);
                            pet.onXPGained(Double.toString(damageDone));
                            updateLevel(w, p);
                            pet.determineSpecialAttack((LivingEntity) e.getEntity());
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
