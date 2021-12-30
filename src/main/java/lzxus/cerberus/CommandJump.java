package lzxus.cerberus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.util.Vector;

public class CommandJump implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Wolf w = Cerberus.obtainFromWolfList(p);
            if (w!=null)
            {
                w.setVelocity(new Vector(0,.5,0));
                /*for(float i = 0; i<=360; i++)
                {
                    w.setRotation(0.0F,i);
                }*/
                return true;
            }
        }
        return false;
    }
}
