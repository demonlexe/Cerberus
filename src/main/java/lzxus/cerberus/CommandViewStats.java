package lzxus.cerberus;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;

public class CommandViewStats implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Wolf obtainedWolf = null;
            PersistentDataContainer data = p.getPersistentDataContainer();

            NamespacedKey uniqueIDKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-uuid");
            String obtainedString = data.get(uniqueIDKey, PersistentDataType.STRING);
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
                NamespacedKey xpKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-xp");
                NamespacedKey lvlKey = new NamespacedKey(Cerberus.getPlugin(), "wolf-level");
                Double xp = data.get(xpKey,PersistentDataType.DOUBLE);
                Integer lvl = data.get(lvlKey, PersistentDataType.INTEGER);

                if (xp != null && lvl != null) {
                    p.sendMessage(ChatColor.GREEN + "Your wolf's statistics:" +
                            ChatColor.GRAY +
                            "\r\n" + "Level: " + lvl +
                            "\r\n" + "XP: " + Math.ceil(xp)
                            //FIXME: Add statistics like health, personality, damage, name.
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
