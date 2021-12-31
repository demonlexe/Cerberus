package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.ConfigFunctions;
import lzxus.cerberus.Structs.PlayerWolfData;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CommandAllowDamage implements CommandExecutor {
    private static String systemColor = null;
    private static String successColor = null;
    private static String failColor = null;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player)
        {
            if (systemColor == null){
                systemColor = ConfigFunctions.getChatColor("systemChatColor");
            }
            if (failColor == null){
                failColor = ConfigFunctions.getChatColor("failureChatColor");
            }
            if (successColor == null){
                successColor = ConfigFunctions.getChatColor("successChatColor");
            }

            Player p = (Player) sender;
            Integer dataObtained  = PlayerWolfData.getDamageEnabled(p);
            if (dataObtained != null && dataObtained.equals(1))
              {
                  //System.out.println("Setting updated; Damage is already enabled.");
                  p.sendMessage(systemColor + "Pet Damaging is now " +failColor+ "Disabled.");
                  PlayerWolfData.setDamageEnabled(p,0);
              }
            else
              {
                  PlayerWolfData.setDamageEnabled(p,1);
                  //System.out.println("Setting updated; Damage is now enabled.");
                  p.sendMessage(systemColor + "Pet Damaging is now "+ successColor+ "Enabled.");
              }
        }
        return true;
    }
}
