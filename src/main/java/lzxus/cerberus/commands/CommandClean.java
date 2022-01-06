package lzxus.cerberus.commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.configdata.ConfigData;
import lzxus.cerberus.petdata.Pet;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandClean implements CommandExecutor {
    private static ConfigData cData = new ConfigData();
    private static String warnColor;
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player && sender.isOp()) {
            if (warnColor == null){
                warnColor = cData.getChatColor("warningChatColor");
            }
            for (World w : Bukkit.getWorlds())
            {
                for (Entity e : w.getEntities())
                {
                    if (e instanceof Wolf)
                    {
                        Player wolfOwner = (Player) ((Wolf) e).getOwner();
                        if (wolfOwner != null)
                        {
                            Pet pet = Cerberus.obtainPetData(wolfOwner);
                            if (pet!=null)
                            {
                                pet.getResetFunctions().resetP(wolfOwner,pet);
                            }
                        }
                        e.remove();
                    }
                }
            }
            ((Player) sender).sendMessage(warnColor+"All wolves have been removed from the world.");
            return true;
        }
        return false;
    }
}
