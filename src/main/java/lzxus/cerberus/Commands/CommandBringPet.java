package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.ConfigFunctions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandBringPet {
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
        String formattedString = (successColor + "/ce bring"
                + systemColor + " - " + dataColor + "Teleports your pet to you.");
        return formattedString;
    }

    public static boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {

            updateColors();

            Player p = (Player) sender;
            Wolf obtainedWolf = Cerberus.obtainFromWolfList(p);
            p.sendMessage(successColor + "You have brought your pet!");
            if (obtainedWolf != null)
            {
                obtainedWolf.teleport(p);
            }
        }
        return true;
    }
}
