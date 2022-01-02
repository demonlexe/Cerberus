package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.CerberusCommand;
import lzxus.cerberus.Structs.ConfigFunctions;
import lzxus.cerberus.Structs.PetData;
import lzxus.cerberus.Structs.PlayerReset;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandResetPlayer extends CerberusCommand {

//FIXME : CHANGE TO /CE RESET CONFIRM. OVERWRITE ERROR MESSAGE TO SAY "PLEASE CONFIRM WITH /CE RESET CONFIRM"
    public String getDescription()
    {
        String formattedString = (cData.successColor + "/ce reset"
                + cData.systemColor + " - " + cData.dataColor + "Kills your current pet.");
        return formattedString;
    }

    public boolean commandFailedMessage(Player p)
    {
        if (p!=null)
        {
            PetData pet = Cerberus.obtainPetData(p);
            assert pet!=null;
            if (pet.getWolfStatus().equals(0))
            {
                p.sendMessage(cData.failColor+"You do not currently have a pet!");
            }
            else
            {
                p.sendMessage(cData.failColor+"To confirm this action, please type "+cData.dataColor+"/ce reset confirm");
            }
            return true;
        }
        return false;
    }

    public boolean onCommand(final CommandSender sender, final String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!ArrayUtils.isEmpty(args)) {
                String confirmation = args[0];
                if (confirmation.equalsIgnoreCase("confirm"))
                {
                    PetData pet = Cerberus.obtainPetData(p);
                    pet.resetP(p,pet);
                    p.sendMessage(cData.successColor+"Your pet data has been reset!");
                    return true;
                }
            }
            commandFailedMessage(p);
            return true;
        }
        return false;
    }

    public CommandResetPlayer()
    {
        super();
        Description = getDescription();
        CommandName = "reset";
        Aliases.add("reset");
    }
}
