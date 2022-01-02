package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.ConfigData;
import lzxus.cerberus.Structs.ConfigFunctions;
import lzxus.cerberus.Structs.PetData;
import lzxus.cerberus.Structs.PlayerReset;
import net.md_5.bungee.chat.SelectorComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
                            PetData pet = Cerberus.obtainPetData(wolfOwner);
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
