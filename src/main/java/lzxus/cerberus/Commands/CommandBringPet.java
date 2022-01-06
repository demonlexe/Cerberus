package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.petdata.Pet;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandBringPet extends CerberusCommand {
    public String getDescription()
    {
        String formattedString = (cData.successColor + "/ce " + CommandName
                + cData.systemColor + " - " + cData.dataColor + "Teleports your pet to you.");
        return formattedString;
    }

    public boolean onCommand(final CommandSender sender, final String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Pet pet = Cerberus.obtainPetData(p);

            if (pet!=null && pet.getWolfStatus().equals(1))
            {
                Wolf obtainedWolf = pet.getWolf();
                p.sendMessage(cData.successColor + "You have brought your pet!");
                if (obtainedWolf != null)
                {
                    obtainedWolf.teleport(p);
                    pet.clearQueue();
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
