package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.CerberusCommand;
import lzxus.cerberus.Structs.ConfigFunctions;
import lzxus.cerberus.Structs.PetData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandBringPet extends CerberusCommand {
    public String getDescription()
    {
        String formattedString = (successColor + "/ce bring"
                + systemColor + " - " + dataColor + "Teleports your pet to you.");
        return formattedString;
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            PetData pet = Cerberus.obtainPetData(p);
            assert pet!=null;
            if (pet.getWolfStatus().equals(1))
            {
                Wolf obtainedWolf = pet.getWolf();
                p.sendMessage(successColor + "You have brought your pet!");
                if (obtainedWolf != null)
                {
                    obtainedWolf.teleport(p);
                }
                return true;
            }
            else
            {
                commandFailedMessage(p);
                return true;
            }
        }
        return false;
    }

    public CommandBringPet(){
        super();
        Description = getDescription();
        CommandName = "bring";
        Aliases.add("bring");
        Aliases.add("b");
    }
}
