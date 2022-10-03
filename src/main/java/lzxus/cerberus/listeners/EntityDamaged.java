package lzxus.cerberus.listeners;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.configdata.ConfigData;
import lzxus.cerberus.petdata.Pet;
import lzxus.cerberus.structs.LevelChecker;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

// Event listener for adding XP to a wolf.
public class EntityDamaged implements Listener {
    private static String failColor = null;
    private static String successColor = null;
    private static String dataColor = null;
    private static ConfigData cData = new ConfigData();
    private static LevelChecker lvlChk = new LevelChecker();
    private static double [] xpList = null;
    private static Integer maxLevel = null;

    private static Integer checkLevelHelper(Pet p)
    {
        if (p==null) {return null;}

        Double petXP = p.getWolfXp();

        if (petXP == null) {return null;}

        for (int i = 0; i < xpList.length; i++)
        {
            if (petXP < xpList[i])
            {
                return i-1;
            }
        }
        return xpList.length-1;
    }

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
            Integer currentLevel = pet.getWolfLvl();
            Integer updatedLevel = checkLevelHelper(pet);
            Double currentHealth = pet.getCurrentWolfHealth();
            Double maxHealth = pet.getWolfHealth();
            //System.out.println("Cerberus: Obtaining Data: CurrentXP is "+currentXP+" while currentLevel is "+currentLevel);
            if ((currentLevel != null) && (updatedLevel != null) && !(currentLevel.equals(updatedLevel)))
            {
                pet.setWolfLvl(updatedLevel);
                pet.onLevelUp();
                p.sendMessage(successColor + "Your pet has leveled up! It is now " +dataColor+"Level "+updatedLevel);
                System.out.println("newSlotLvl is "+lvlChk.nextSlotLvl(pet)+" updated lvl is "+updatedLevel+" and getslot is "+lvlChk.getOpenSlot(pet));
                if (lvlChk.getOpenSlot(pet) >= 1) {
                    // Will print once per level-up if upgrades are needed
                    p.sendMessage(successColor + "You can unlock another skill! Use "+dataColor+"/ce learn");
                }
                pet.updateStats(updatedLevel);
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

    private static double determineExperienceMultiplier(final double finalDmg, Entity e)
    {
        double dmgToReturn = finalDmg;
        if (e instanceof Monster)
        {
            dmgToReturn = finalDmg*1.5;
        }
        return dmgToReturn;
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
                        double damageDone = determineExperienceMultiplier(e.getFinalDamage(),e.getEntity());

                        Double prevXp = pet.getWolfXp();
                        if (prevXp != null)
                        {
                            pet.setWolfXp(prevXp+damageDone);
                            //System.out.println("Total XP of this wolf: "+PlayerWolfData.getWolfXp(p));
                            damageDone = damageDone*100.0;
                            int convertedDmg = (int)damageDone;
                            damageDone = ((double)convertedDmg)/100.0;
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
