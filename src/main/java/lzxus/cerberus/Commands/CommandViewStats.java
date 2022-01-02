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
        String formattedString = (cData.successColor + "/ce stats"
                + cData.systemColor + " - " + cData.dataColor + "Displays current pet statistics.");
        return formattedString;
    }

    private String isLowHealth(final double current, final double max)
    {
        if (current/max < 0.5)
        {
            return cData.failColor;
        }
        return cData.dataColor;
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
                    p.sendMessage(cData.failColor + "You do not have a main wolf currently registered!");
                    return false;
                }

                if (obtainedWolf != null) {
                    Double xp = pet.getWolfXp();
                    Integer lvl = pet.getWolfLvl();
                    Double currentHealth = pet.getCurrentWolfHealth();
                    Double maxHealth = pet.getWolfHealth();
                    Double damage = pet.getWolfDamage();

                    if (xp != null && lvl != null && currentHealth != null && maxHealth != null && damage != null) {
                        p.sendMessage(cData.systemColor+"------------------"+ "\n" + cData.successColor + "Your wolf's statistics:" +
                                        cData.systemColor +
                                        "\n" + "Level: " + cData.dataColor +lvl +
                                        cData.systemColor +"\n" + "XP: " + cData.dataColor +Math.ceil(xp) + " / " + cData.xpList[lvl+1] +
                                        cData.systemColor + "\n" + "Health: " + isLowHealth(currentHealth,maxHealth) +currentHealth +" / "+ cData.dataColor + maxHealth+
                                        cData.systemColor + "\n" + "Attack Damage: " + cData.dataColor +damage + cData.systemColor+"\n"+"------------------"
                                //FIXME: Add statistics like personality.
                        );
                    }
                }
                else
                {
                    p.sendMessage(cData.failColor + "You do not have a main wolf currently registered.");
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
