package lzxus.cerberus.Commands;

import lzxus.cerberus.Structs.PlayerWolfData;
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
            p.sendMessage(ChatColor.RED + "wolf-xp: "+ PlayerWolfData.getWolfXp(p)+
                    "\n"+"wolf-lvl: "+PlayerWolfData.getWolfLvl(p)+
                    "\n"+"wolf-status: "+PlayerWolfData.getWolfStatus(p)+
                    "\n"+"wolf-uuid: "+PlayerWolfData.getWolfUUID(p)+
                    "\n"+"damageEnabled: "+PlayerWolfData.getDamageEnabled(p)+
                    "\n"+"wolf-name: "+PlayerWolfData.getWolfName(p) +
                    "\n"+"wolf-owned: "+PlayerWolfData.getWolfOwned(p) +
                    "\n"+"wolf-attack-status: "+PlayerWolfData.getAttackStatus(p) +
                    "\n"+"wolf-attack-type: "+PlayerWolfData.getAttackType(p)

            );
            return true;
        }
        return false;
    }
}
