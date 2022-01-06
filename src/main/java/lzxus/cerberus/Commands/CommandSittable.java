package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Commands.CerberusCommand;
import lzxus.cerberus.configdata.ConfigFunctions;
import lzxus.cerberus.petdata.Pet;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.util.Vector;

/**
 * Should be constructed as an object by CommandMain. //FIXME : Description
 */
public class CommandSittable extends CerberusCommand {
/**
 * CAN be overridden by subclass.
 * Returns a formatted string describing command function.
 * @return
 */
public String getDescription()
        {
        String formattedString = (cData.successColor + "/ce " + CommandName
        + cData.systemColor + " - " + cData.dataColor + "Toggles if your pet is able to be sat.");
        return formattedString;
        }
/**
 * Code to be run when a Player executes this command.
 * Toggles if your pet is able to be sat.
 * CAN be overridden by subclass.
 */

public boolean onCommand(final CommandSender sender, final String[] args) {
        if (sender instanceof Player) {
                Player p = (Player) sender;
                Pet pet = Cerberus.obtainPetData(p);

                if (pet!=null)
                {
                        if (pet.getWolfStatus().equals(1))
                        {
                        //FIXME
                        }
                        else
                        {
                                commandFailedMessage(p);
                        }
                        return true;
                }
        }
        return false;
}

/**
 * Constructor to be called by CommandMain. MUST call super();
 * Overwrites command info, such as Description, CommandName, Aliases.
 */
public CommandSittable ()
    {
            super();
            Description = getDescription();
            CommandName = "sittable";
            Aliases.add("sittable");
            Aliases.add("sit");
    }

}