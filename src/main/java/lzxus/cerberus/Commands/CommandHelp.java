package lzxus.cerberus.Commands;

import lzxus.cerberus.Structs.CerberusCommand;
import lzxus.cerberus.Structs.ConfigFunctions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHelp extends CerberusCommand {
    public static String getDescription()
    {
        initializeData();
        String formattedString = (successColor + "/ce help"
                + systemColor + " - " + dataColor + "View the Cerberus help page.");
        return formattedString;
    }

    public static boolean onCommand(CommandSender sender, String [] args)
    {
        initializeData();
        if (sender instanceof Player)
        {
            sender.sendMessage(systemColor+"------------------"+"\n" + "Current Cerberus Commands:"
                            +"\n" + CommandAllowDamage.getDescription()
                            +"\n" + CommandAttack.getDescription()
                            +"\n" + CommandBringPet.getDescription()
                            +"\n" + getDescription()
                            +"\n" + CommandJump.getDescription()
                            +"\n" + CommandNamePet.getDescription()
                            +"\n" + CommandResetPlayer.getDescription()
                            +"\n" + CommandViewStats.getDescription() + systemColor + "\n" + "------------------"
                    //+"\n" + //FIXME


            );
            return true;
        }
        return false;
    }
}
