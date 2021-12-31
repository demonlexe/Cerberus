package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.ConfigFunctions;
import lzxus.cerberus.Structs.PlayerWolfData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandViewStats {
    private static double [] xpList = null;
    private static String systemColor = null;
    private static String successColor = null;
    private static String failColor = null;
    private static String dataColor = null;

    public static void updateColors(){
        if (failColor == null){
            failColor = ConfigFunctions.getChatColor("failureChatColor");
        }
        if (successColor == null){
            successColor = ConfigFunctions.getChatColor("successChatColor");
        }
        if (dataColor == null){
            dataColor = ConfigFunctions.getChatColor("dataChatColor");
        }
        if (systemColor == null){
            systemColor = ConfigFunctions.getChatColor("systemChatColor");
        }
    }

    public static String getDescription()
    {
        updateColors();
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
        if (sender instanceof Player) {

            updateColors();

            Player p = (Player) sender;
            Wolf obtainedWolf = null;

            if (xpList == null)
            {
                xpList = Cerberus.obtainXPList();
                //System.out.println("Cerberus: Obtained XP List . . .");
            }

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
                    p.sendMessage("\n"+successColor + "Your wolf's statistics:" +
                            systemColor +
                            "\n" + "Level: " + dataColor +lvl +
                                    systemColor +"\n" + "XP: " + dataColor +Math.ceil(xp) + " / " + xpList[lvl+1] +
                                    systemColor + "\n" + "Health: " + isLowHealth(currentHealth,maxHealth) +currentHealth +" / "+ dataColor + maxHealth+
                                    systemColor + "\n" + "Attack Damage: " + dataColor +damage + "\n"+""
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
