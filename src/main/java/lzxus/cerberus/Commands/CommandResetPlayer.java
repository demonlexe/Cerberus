package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.CerberusCommand;
import lzxus.cerberus.Structs.ConfigFunctions;
import lzxus.cerberus.Structs.PetData;
import lzxus.cerberus.Structs.PlayerReset;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandResetPlayer extends CerberusCommand {

//FIXME : CHANGE TO /CE RESET CONFIRM. OVERWRITE ERROR MESSAGE TO SAY "PLEASE CONFIRM WITH /CE RESET CONFIRM"
    public String getDescription()
    {
        String formattedString = (successColor + "/ce reset"
                + systemColor + " - " + dataColor + "Kills your current pet.");
        return formattedString;
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            PetData pet = Cerberus.obtainPetData(p);
            PlayerReset.resetP(p,pet);
            p.sendMessage(successColor+"Your pet data has been reset!");
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
