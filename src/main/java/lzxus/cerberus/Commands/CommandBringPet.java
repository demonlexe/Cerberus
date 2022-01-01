package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.CerberusCommand;
import lzxus.cerberus.Structs.ConfigFunctions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandBringPet extends CerberusCommand {
    public static String getDescription()
    {
        initializeData();
        String formattedString = (successColor + "/ce bring"
                + systemColor + " - " + dataColor + "Teleports your pet to you.");
        return formattedString;
    }

    public static boolean onCommand(CommandSender sender, String[] args) {
        initializeData();
        if (sender instanceof Player) {
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
