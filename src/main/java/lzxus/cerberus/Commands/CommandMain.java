package lzxus.cerberus.Commands;

import lzxus.cerberus.Structs.CerberusCommand;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.HelpCommand;
import org.bukkit.entity.Player;

public class CommandMain implements CommandExecutor {

    private static String [] getNewArgs(String [] oldArgs)
    {
        String [] newArgs = new String[oldArgs.length-1];
        for (int i = 1; i <oldArgs.length; i++)
        {
            newArgs[i-1] = oldArgs[i];
            System.out.println("Inserting: "+ oldArgs[i]);
        }
        return newArgs;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean toR = false;
        if (sender instanceof Player) {
            if (ArrayUtils.isEmpty(args))
            {
                toR = CommandHelp.onCommand(sender,args);
            }
            else
            {
                String commandName = args[0];
                if (commandName.equalsIgnoreCase("allow"))
                {
                    toR = CommandAllowDamage.onCommand(sender, getNewArgs(args));
                }
                else if (commandName.equalsIgnoreCase("attack"))
                {
                    toR = CommandAttack.onCommand(sender, getNewArgs(args));
                }
                else if (commandName.equalsIgnoreCase("bring"))
                {
                    toR = CommandBringPet.onCommand(sender, getNewArgs(args));
                }
                else if (commandName.equalsIgnoreCase("help"))
                {
                    toR = CommandHelp.onCommand(sender, getNewArgs(args));
                }
                else if (commandName.equalsIgnoreCase("jump"))
                {
                    toR = CommandJump.onCommand(sender, getNewArgs(args));
                }
                else if (commandName.equalsIgnoreCase("name"))
                {
                    toR = CommandNamePet.onCommand(sender, getNewArgs(args));
                }
                else if (commandName.equalsIgnoreCase("reset"))
                {
                    toR = CommandResetPlayer.onCommand(sender, getNewArgs(args));
                }
                else if (commandName.equalsIgnoreCase("stats") || commandName.equalsIgnoreCase("s"))
                {
                    toR = CommandViewStats.onCommand(sender, getNewArgs(args));
                }
                else
                {
                    toR = CerberusCommand.commandFailedMessage((Player) sender);
                }
            }
        }
        return toR;
    }
}
