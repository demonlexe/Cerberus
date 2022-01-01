package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.ConfigFunctions;
import lzxus.cerberus.Structs.PetData;
import lzxus.cerberus.Structs.PlayerReset;
import net.md_5.bungee.chat.SelectorComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandClean implements CommandExecutor {
    private static String warnColor = null;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && sender.isOp()) {
            if (warnColor == null){
                warnColor = ConfigFunctions.getChatColor("warningChatColor");
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
                            PlayerReset.resetP(wolfOwner,pet);
                        }
                        ((Wolf) e).setHealth(0);
                    }
                }
            }
            ((Player) sender).sendMessage(warnColor+"All wolves have been removed from the world.");
            return true;
        }
        return false;
    }
}
