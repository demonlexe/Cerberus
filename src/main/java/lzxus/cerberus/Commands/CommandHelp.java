package lzxus.cerberus.Commands;

import lzxus.cerberus.Structs.CerberusCommand;
import lzxus.cerberus.Structs.ConfigFunctions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandHelp extends CerberusCommand {
    public String getDescription()
    {
        String formattedString = (successColor + "/ce help"
                + systemColor + " - " + dataColor + "View the Cerberus help page.");
        return formattedString;
    }

    private String obtainDesc(final ArrayList<CerberusCommand> list)
    {
        String newString = "";
        for (CerberusCommand c : list)
        {
            newString += ("\n" + c.getDescription());
        }
        return newString;
    }

    public boolean onCommand(final CommandSender sender, final String [] args, final ArrayList<CerberusCommand> list)
    {
        if (sender instanceof Player)
        {
            sender.sendMessage(systemColor+"------------------"+"\n" + "Current Cerberus Commands:"
                            + obtainDesc(list)
                            + systemColor + "\n" + "------------------"
//FIXME add pages?

            );
            return true;
        }
        return false;
    }

    public CommandHelp(){
        super();
        Description = getDescription();
        CommandName = "help";
        Aliases.add("help");
        Aliases.add("h");
    }

}
