package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.PlayerWolfData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandNamePet implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Wolf obtainedWolf = Cerberus.obtainFromWolfList(p);
            p.sendMessage(ChatColor.BLUE + "Your pet is now named " +ChatColor.GREEN+args[0]+ ChatColor.BLUE+"!");
            if (obtainedWolf != null)
            {
                obtainedWolf.setCustomName(args[0]);
                PlayerWolfData.setWolfName(p,args[0]);
            }
        }
        return true;
    }
}
