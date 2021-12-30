package lzxus.cerberus;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandClean implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && sender.isOp()) {
            for (World w : Bukkit.getWorlds())
            {
                for (Entity e : w.getEntities())
                {
                    if (e instanceof Wolf)
                        ((Wolf) e).setHealth(0);
                }
            }
            return true;
        }
        return false;
    }
}
