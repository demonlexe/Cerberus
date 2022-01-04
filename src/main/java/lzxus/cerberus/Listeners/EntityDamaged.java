package lzxus.cerberus.Listeners;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.*;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
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
            PetData pet = Cerberus.obtainPetData(p);
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
        if (!isDeadByPet(damagedEntity,attacker)) {
            if (damagedEntity instanceof Player)
            {
                Player p = (Player) damagedEntity;
                PetData pet = Cerberus.obtainPetData(p);
                if (pet!= null)
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
                    PetData pet = Cerberus.obtainPetData(p);
                    if (pet!= null)
                    {
                        pet.enQueueFirst(attacker);
                    }
                }
            }
        }
    }

    private static boolean isDeadByPet(Entity damagedEntity, Entity attacker)
    {
        if (attacker instanceof Wolf && damagedEntity instanceof LivingEntity)
        {
            Wolf w = (Wolf) attacker;
            Player owner = (Player) (w).getOwner();
            if (owner != null)
            {
                PetData pet = Cerberus.obtainPetData(owner);
                if (pet!=null && (((LivingEntity) damagedEntity).getHealth() <= 0))
                {
                    DogBehavior.attackChoice(pet);
                    return true;
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onDamaged(EntityDamageByEntityEvent e)
    {
        isThreat(e.getEntity(),e.getDamager());
        if (e.getDamager() instanceof Wolf)
        {
            Wolf w = (Wolf) e.getDamager();
            if (w.getOwner() != null)
            {
                Player p = (Player) w.getOwner();
                if (p != null) {
                    PetData pet = Cerberus.obtainPetData(p);
                    Integer allowedToAttack = pet.getAttackStatus();
                    if (allowedToAttack.equals(1) && pet.isAllowedToAttack(e.getEntity()))
                    {
                        double damageDone = e.getFinalDamage();

                        Double prevXp = pet.getWolfXp();
                        if (prevXp != null)
                        {
                            pet.setWolfXp(prevXp+damageDone);
                            //System.out.println("Total XP of this wolf: "+PlayerWolfData.getWolfXp(p));
                            pet.onXPGained(Double.toString(damageDone));
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
