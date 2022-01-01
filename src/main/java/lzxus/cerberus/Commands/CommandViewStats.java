package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.CerberusCommand;
import lzxus.cerberus.Structs.ConfigFunctions;
import lzxus.cerberus.Structs.PlayerWolfData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandViewStats extends CerberusCommand {
    public static String getDescription()
    {
        initializeData();
        String formattedString = (successColor + "/ce stats"
                + systemColor + " - " + dataColor + "Displays current pet statistics.");
        return formattedString;
    }

    private static String isLowHealth(double current, double max)
    {
        if (current/max < 0.5)
        {
            return failColor;
        }
        return dataColor;
    }

    public static boolean onCommand(CommandSender sender, String[] args) {
        initializeData();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Wolf obtainedWolf = null;

            String obtainedString = PlayerWolfData.getWolfUUID(p);
            if (obtainedString != null)
            {
                obtainedWolf = Cerberus.obtainFromWolfList(p);
            }
            else
            {
                p.sendMessage(failColor + "You do not have a main wolf currently registered!");
                return false;
            }

            if (obtainedWolf != null) {
                Double xp = PlayerWolfData.getWolfXp(p);
                Integer lvl = PlayerWolfData.getWolfLvl(p);
                Double currentHealth = PlayerWolfData.getCurrentWolfHealth(p);
                Double maxHealth = PlayerWolfData.getWolfHealth(p);
                Double damage = PlayerWolfData.getWolfDamage(p);

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
        return true;
    }
}
