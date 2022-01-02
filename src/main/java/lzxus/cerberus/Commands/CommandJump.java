package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.CerberusCommand;
import lzxus.cerberus.Structs.ConfigFunctions;
import lzxus.cerberus.Structs.PetData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.util.Vector;

public class CommandJump extends CerberusCommand {
    /**
     * CAN be overridden by subclass.
     * Returns a formatted string describing command function.
     * @return
     */
    public String getDescription()
    {
        String formattedString = (cData.successColor + "/ce jump"
                + cData.systemColor + " - " + cData.dataColor + "Your pet does a lil jump.");
        return formattedString;
    }

    public boolean onCommand(final CommandSender sender, final String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            PetData pet = Cerberus.obtainPetData(p);
            if (pet!=null && pet.getWolfStatus().equals(1))
            {
                Wolf w = pet.getWolf();
                if (w!=null)
                {
                    w.setVelocity(new Vector(0,.5,0));
                /*for(float i = 0; i<=360; i++)
                {
                    w.setRotation(0.0F,i);
                }*/
                    return true;
                }
            }
            else
            {
                commandFailedMessage(p);
                return true;
            }
        }
        return false;
    }

    public CommandJump()
    {
        super();
        Description = getDescription();
        CommandName = "jump";
        Aliases.add("jump");
        Aliases.add("j");
    }

}
