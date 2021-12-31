package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.ConfigFunctions;
import lzxus.cerberus.Structs.PlayerWolfData;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandNamePet {
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
        String formattedString = (successColor + "/ce name <Name>"
                + systemColor + " - " + dataColor + "Names your pet.");
        return formattedString;
    }

    public static boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player && !ArrayUtils.isEmpty(args)) {

            updateColors();

            Player p = (Player) sender;
            Wolf obtainedWolf = Cerberus.obtainFromWolfList(p);
            String newName = (args[0]).replaceAll("\\s+","");
            p.sendMessage(successColor + "Your pet is now named " +dataColor+newName+ successColor+"!");
            if (obtainedWolf != null)
            {
                obtainedWolf.setCustomName(newName);
                PlayerWolfData.setWolfName(p,newName);
            }
        }
        return true;
    }
}
