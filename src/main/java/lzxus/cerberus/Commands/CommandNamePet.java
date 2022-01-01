package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.CerberusCommand;
import lzxus.cerberus.Structs.ConfigFunctions;
import lzxus.cerberus.Structs.PlayerWolfData;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandNamePet extends CerberusCommand {
    public String getDescription()
    {
        String formattedString = (successColor + "/ce name <Name>"
                + systemColor + " - " + dataColor + "Names your pet.");
        return formattedString;
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player && !ArrayUtils.isEmpty(args)) {
            Player p = (Player) sender;
            Wolf obtainedWolf = Cerberus.obtainFromWolfList(p);
            String newName = (args[0]).replaceAll("\\s+","");
            p.sendMessage(successColor + "Your pet is now named " +dataColor+newName+ successColor+"!");
            if (obtainedWolf != null)
            {
                obtainedWolf.setCustomName(newName);
                PlayerWolfData.setWolfName(p,newName);
            }
        }
        return true;
    }

    public CommandNamePet(){
        super();
        Description = getDescription();
        CommandName = "name";
        Aliases.add("name");
        Aliases.add("n");
    }
}
