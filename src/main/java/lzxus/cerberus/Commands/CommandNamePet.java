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

public class CommandNamePet implements CommandExecutor {
    private static String failColor = null;
    private static String successColor = null;
    private static String dataColor = null;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && !ArrayUtils.isEmpty(args)) {

            if (failColor == null){
                failColor = ConfigFunctions.getChatColor("failureChatColor");
            }
            if (successColor == null){
                successColor = ConfigFunctions.getChatColor("successChatColor");
            }
            if (dataColor == null){
                dataColor = ConfigFunctions.getChatColor("dataChatColor");
            }

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
