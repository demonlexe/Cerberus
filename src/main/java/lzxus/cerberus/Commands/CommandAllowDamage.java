package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.CerberusCommand;
import lzxus.cerberus.Structs.PetData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Should be constructed as an object by CommandMain. Toggles if the pet is able to be harmed by its owner.
 */
public class CommandAllowDamage extends CerberusCommand {
    /**
     * CAN be overridden by subclass.
     * Returns a formatted string describing command function.
     * @return
     */
    public String getDescription()
    {
        String formattedString = (successColor + "/ce allow"
                + systemColor + " - " + dataColor + "Toggles if pet is able to be harmed by you.");
        return formattedString;
    }

    /**
     * Code to be run when a Player executes this command.
     * Toggles if a pet is able to be harmed by the owner.
     * CAN be overridden by subclass.
     */
    public boolean onCommand(final CommandSender sender, final String[] args) {
        if (sender instanceof Player)
        {
            Player p = (Player) sender;
            PetData pet = Cerberus.obtainPetData(p);
            assert pet!=null;
            if (pet.getWolfStatus().equals(1))
            {
                Integer dataObtained  = pet.getDamageEnabled();
                if (dataObtained != null && dataObtained.equals(1))
                {
                    //System.out.println("Setting updated; Damage is already enabled.");
                    p.sendMessage(systemColor + "Pet Damaging is now " +failColor+ "Disabled.");
                    pet.setDamageEnabled(0);
                }
                else
                {
                    pet.setDamageEnabled(1);
                    //System.out.println("Setting updated; Damage is now enabled.");
                    p.sendMessage(systemColor + "Pet Damaging is now "+ successColor+ "Enabled.");
                }
            }
            else
            {
                commandFailedMessage(p);
            }
            return true;
        }
        return false;
    }

    /**
     * Constructor to be called by CommandMain. MUST call super();
     * Overwrites command info, such as Description, CommandName, Aliases.
     */
    public CommandAllowDamage()
    {
        super();
        Description = getDescription();
        CommandName = "allow";
        Aliases.add("allow");
    }
}
