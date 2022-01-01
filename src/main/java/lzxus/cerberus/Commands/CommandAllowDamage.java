package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.CerberusCommand;
import lzxus.cerberus.Structs.PetData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

    public CommandAllowDamage()
    {
        super();
        Description = getDescription();
        CommandName = "allow";
        Aliases.add("allow");
    }
}
