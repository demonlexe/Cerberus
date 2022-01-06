package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.petdata.Pet;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            Pet pet = Cerberus.obtainPetData(p);
            assert pet!=null;
            if (pet.getWolfOwned().equals(0))
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
                    Pet pet = Cerberus.obtainPetData(p);
                    if (pet!=null){
                        pet.getResetFunctions().resetP(p,pet);
                        return true;
                    }
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
