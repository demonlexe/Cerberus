package lzxus.cerberus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDebugData implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            p.sendMessage(ChatColor.RED + "wolf-xp: "+PlayerWolfData.getWolfXp(p)+
                    "\r\n"+"wolf-lvl: "+PlayerWolfData.getWolfLvl(p)+
                    "\r\n"+"wolf-status: "+PlayerWolfData.getWolfStatus(p)+
                    "\r\n"+"wolf-uuid: "+PlayerWolfData.getWolfUUID(p)+
                    "\r\n"+"damageEnabled: "+PlayerWolfData.getDamageEnabled(p)+
                    "\r\n"+"wolf-name: "+PlayerWolfData.getWolfName(p) +
                    "\r\n"+"wolf-owned: "+PlayerWolfData.getWolfOwned(p)
            );
            return true;
        }
        return false;
    }
}
