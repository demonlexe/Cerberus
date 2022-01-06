package lzxus.cerberus.Commands;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class MainCommand implements CommandExecutor {
    private static ArrayList<CerberusCommand> commandList = new ArrayList<>();
    private static CerberusCommand helpCommand;

    //to be called by Cerberus.java
    public static void main()
    {
        System.out.println("Adding commands to CommandList.");
        commandList.add(new CommandAllowDamage());
        commandList.add(new CommandAttack());
        commandList.add(new CommandBringPet());
        commandList.add(new CommandJump());
        commandList.add(new CommandNamePet());
        commandList.add(new CommandResetPlayer());
        commandList.add(new CommandViewStats());
        helpCommand = new CommandHelp();
    }

    private static String [] getNewArgs(final String [] oldArgs)
    {
        String [] newArgs = new String[oldArgs.length-1];
        for (int i = 1; i <oldArgs.length; i++)
        {
            newArgs[i-1] = oldArgs[i];
          //  System.out.println("Inserting: "+ oldArgs[i]);
        }
        return newArgs;
    }

    private static boolean checkAliases(final ArrayList<String> list, final String toCompare)
    {
        for (String s : list)
        {
           // System.out.println("Checking for aliases.");
            if (s.equalsIgnoreCase(toCompare))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        boolean toR = false;
        if (sender instanceof Player) {
            if (ArrayUtils.isEmpty(args))
            {
                toR = helpCommand.onCommand(sender,args,commandList);
            }
            else
            {
                String commandName = args[0];
                for (CerberusCommand c : commandList)
                {
                    if (checkAliases(c.getAliases(),commandName))
                    {
                        toR = c.onCommand(sender, getNewArgs(args));
                        return toR;
                    }
                }
                helpCommand.commandFailedMessage((Player) sender);
                toR = helpCommand.onCommand(sender,args,commandList);
            }
        }
        return toR;
    }
}
