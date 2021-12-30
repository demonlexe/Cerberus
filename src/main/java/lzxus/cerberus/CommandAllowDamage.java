package lzxus.cerberus;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CommandAllowDamage implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player)
        {
            Player p = (Player) sender;
            Integer dataObtained = PlayerWolfData.getDamageEnabled(p);
            if (dataObtained != null && dataObtained.equals(1))
              {
                  System.out.println("Setting updated; Damage is already enabled.");
                  p.sendMessage(ChatColor.GRAY + "Pet Damaging is now Disabled.");
                  PlayerWolfData.setDamageEnabled(p,0);
              }
            else
              {
                  PlayerWolfData.setDamageEnabled(p,1);
                  System.out.println("Setting updated; Damage is now enabled.");
                  p.sendMessage(ChatColor.GRAY + "Pet Damaging is now Enabled.");
              }
        }
        return true;
    }
}
