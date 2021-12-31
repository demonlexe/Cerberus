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

public class CommandAllowDamage {
    private static String systemColor = null;
    private static String successColor = null;
    private static String failColor = null;
    private static String dataColor = null;

    public static void updateColors(){
        if (failColor == null){
            failColor = ConfigFunctions.getChatColor("failureChatColor");
        }
        if (successColor == null){
            successColor = ConfigFunctions.getChatColor("successChatColor");
        }
        if (dataColor == null){
            dataColor = ConfigFunctions.getChatColor("dataChatColor");
        }
        if (systemColor == null){
            systemColor = ConfigFunctions.getChatColor("systemChatColor");
        }
    }

    public static String getDescription()
    {
        updateColors();
        String formattedString = (successColor + "/ce allow"
                + systemColor + " - " + dataColor + "Toggles if pet is able to be harmed by you.");
        return formattedString;
    }

    public static boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player)
        {
            updateColors();

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
