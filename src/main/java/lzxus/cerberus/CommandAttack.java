package lzxus.cerberus;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.Locale;

public class CommandAttack implements CommandExecutor {
    private static final String [] validAttackTypes = {"all","a","passive","p","monsters","m"};

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (!ArrayUtils.isEmpty(args)){
                String attackType = args[0];
                attackType = attackType.toLowerCase();
                if (ArrayUtils.contains(validAttackTypes,attackType))
                {
                    p.sendMessage(ChatColor.GREEN+"Your pet will now attack: "+ ChatColor.BLUE +attackType);
                }
                else
                {
                    //FIXME
                    p.sendMessage(ChatColor.RED+"That is not a valid attack type! Valid types are "+"all,passive,monsters");
                }
            }
            else
            {
                if (PlayerWolfData.getAttackStatus(p).equals(0))
                {
                    PlayerWolfData.setAttackStatus(p,1);
                    p.sendMessage(ChatColor.GREEN+ "Your pet will now attack mobs!");
                }
                else
                {
                    PlayerWolfData.setAttackStatus(p,0);
                    p.sendMessage(ChatColor.RED+"Your pet will no longer attack any mobs.");
                }
            }

            return true;
        }
        return false;
    }
}
