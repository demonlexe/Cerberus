package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.CerberusCommand;
import lzxus.cerberus.Structs.PetData;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandNamePet extends CerberusCommand {
    public String getDescription()
    {
        String formattedString = (cData.successColor + "/ce name <Name>"
                + cData.systemColor + " - " + cData.dataColor + "Names your pet.");
        return formattedString;
    }

    public boolean onCommand(final CommandSender sender, final String[] args) {
        if (sender instanceof Player && !ArrayUtils.isEmpty(args)) {
            Player p = (Player) sender;
            PetData pet = Cerberus.obtainPetData(p);
            assert pet!=null;

            if (pet.getWolfStatus().equals(1))
            {
                Wolf obtainedWolf = pet.getWolf();
                String newName = (args[0]).replaceAll("\\s+","");
                p.sendMessage(cData.successColor + "Your pet is now named " +cData.dataColor+newName+ cData.successColor+"!");
                if (obtainedWolf != null)
                {
                    obtainedWolf.setCustomName(newName);
                    pet.setWolfName(newName);
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

    public CommandNamePet(){
        super();
        Description = getDescription();
        CommandName = "name";
        Aliases.add("name");
        Aliases.add("n");
    }
}
