package lzxus.cerberus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandResetPlayer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Wolf w = Cerberus.obtainFromWolfList(p);
            if (w != null)
            {
                w.setHealth(0);
                w = null;
                Cerberus.updateWolfList(w,p,false);
            }
            PlayerReset.resetP(p,w);
            return true;
        }
        return false;
    }
}
