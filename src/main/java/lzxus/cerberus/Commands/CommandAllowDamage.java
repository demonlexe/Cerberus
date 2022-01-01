package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.CerberusCommand;
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

public class CommandAllowDamage extends CerberusCommand {

    public String getDescription()
    {
        String formattedString = (successColor + "/ce allow"
                + systemColor + " - " + dataColor + "Toggles if pet is able to be harmed by you.");
        return formattedString;
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player)
        {
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

    public CommandAllowDamage()
    {
        super();
        Description = getDescription();
        CommandName = "allow";
        Aliases.add("allow");
    }
}
