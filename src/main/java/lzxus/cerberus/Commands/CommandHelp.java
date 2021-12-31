package lzxus.cerberus.Commands;

import lzxus.cerberus.Structs.ConfigFunctions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHelp {
    private static String successColor = null;
    private static String dataColor = null;
    private static String systemColor = null;

    public static boolean onCommand(CommandSender sender, String [] args)
    {
        if (sender instanceof Player)
        {
            if (successColor == null){
                successColor = ConfigFunctions.getChatColor("successChatColor");
            }
            if (dataColor == null){
                dataColor = ConfigFunctions.getChatColor("dataChatColor");
            }
            if (systemColor == null){
                systemColor = ConfigFunctions.getChatColor("systemChatColor");
            }

            sender.sendMessage(systemColor+"------------------"+"\n" + "Current Cerberus Commands:"
                            +"\n" + CommandAllowDamage.getDescription()
                            +"\n" + CommandAttack.getDescription()
                            +"\n" + CommandBringPet.getDescription()
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
