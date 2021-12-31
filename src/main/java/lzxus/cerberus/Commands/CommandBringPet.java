package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.ConfigFunctions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandBringPet implements CommandExecutor {
    private static String successColor = null;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            if (successColor == null){
                successColor = ConfigFunctions.getChatColor("successChatColor");
            }

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
