package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.PlayerWolfData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandViewStats implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Wolf obtainedWolf = null;

            String obtainedString = PlayerWolfData.getWolfUUID(p);
            if (obtainedString != null)
            {
                obtainedWolf = Cerberus.obtainFromWolfList(p);
            }
            else
            {
                p.sendMessage(ChatColor.RED + "You do not have a main wolf currently registered!");
                return false;
            }

            if (obtainedWolf != null) {
                Double xp = PlayerWolfData.getWolfXp(p);
                Integer lvl = PlayerWolfData.getWolfLvl(p);
                Double currentHealth = PlayerWolfData.getCurrentWolfHealth(p);
                Double maxHealth = PlayerWolfData.getWolfHealth(p);
                Double damage = PlayerWolfData.getWolfDamage(p);

                if (xp != null && lvl != null) {
                    p.sendMessage(ChatColor.GREEN + "Your wolf's statistics:" +
                            ChatColor.GRAY +
                            "\n" + "Level: " + ChatColor.BLUE +lvl +
                                    ChatColor.GRAY +"\n" + "XP: " + ChatColor.BLUE +Math.ceil(xp) +
                                    ChatColor.GRAY + "\n" + "Health: " + ChatColor.BLUE +currentHealth +" / "+ maxHealth+
                                    ChatColor.GRAY + "\n" + "Attack Damage: " + ChatColor.BLUE +damage + "\n"
                            //FIXME: Add statistics like personality.
                    );
                }
            }
            else
            {
                p.sendMessage(ChatColor.GREEN + "You do not have a main wolf currently registered!");
                return false;
            }
        }
        return true;
    }
}
