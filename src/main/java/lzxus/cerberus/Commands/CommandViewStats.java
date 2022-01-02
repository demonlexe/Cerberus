package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.CerberusCommand;
import lzxus.cerberus.Structs.PetData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandViewStats extends CerberusCommand {
    public String getDescription()
    {
        String formattedString = (successColor + "/ce stats"
                + systemColor + " - " + dataColor + "Displays current pet statistics.");
        return formattedString;
    }

    private String isLowHealth(final double current, final double max)
    {
        if (current/max < 0.5)
        {
            return failColor;
        }
        return dataColor;
    }

    public boolean onCommand(final CommandSender sender, final String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            PetData pet = Cerberus.obtainPetData(p);
            Wolf obtainedWolf = null;

            assert pet != null;

            if (pet.getWolfStatus().equals(1)) {
                String obtainedString = pet.getWolfUUID();
                if (obtainedString != null)
                {
                    obtainedWolf = pet.getWolf();
                }
                else
                {
                    p.sendMessage(failColor + "You do not have a main wolf currently registered!");
                    return false;
                }

                if (obtainedWolf != null) {
                    Double xp = pet.getWolfXp();
                    Integer lvl = pet.getWolfLvl();
                    Double currentHealth = pet.getCurrentWolfHealth();
                    Double maxHealth = pet.getWolfHealth();
                    Double damage = pet.getWolfDamage();

                    if (xp != null && lvl != null && currentHealth != null && maxHealth != null && damage != null) {
                        p.sendMessage(systemColor+"------------------"+ "\n" + successColor + "Your wolf's statistics:" +
                                        systemColor +
                                        "\n" + "Level: " + dataColor +lvl +
                                        systemColor +"\n" + "XP: " + dataColor +Math.ceil(xp) + " / " + xpList[lvl+1] +
                                        systemColor + "\n" + "Health: " + isLowHealth(currentHealth,maxHealth) +currentHealth +" / "+ dataColor + maxHealth+
                                        systemColor + "\n" + "Attack Damage: " + dataColor +damage + systemColor+"\n"+"------------------"
                                //FIXME: Add statistics like personality.
                        );
                    }
                }
                else
                {
                    p.sendMessage(failColor + "You do not have a main wolf currently registered.");
                    return false;
                }
            }
            else {
                commandFailedMessage(p);
            }
            return true;
        }
        return false;
    }

    public CommandViewStats(){
        super();
        Description = getDescription();
        CommandName = "stats";
        Aliases.add("s");
        Aliases.add("stats");
    }
}
