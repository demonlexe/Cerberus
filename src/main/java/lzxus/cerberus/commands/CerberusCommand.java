package lzxus.cerberus.commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.petdata.Pet;
import lzxus.cerberus.configdata.ConfigData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CerberusCommand {
    protected ConfigData cData = new ConfigData();;

    protected String CommandName;
    protected ArrayList<String> Aliases;
    protected String Description;
    //FIXME Add Permission Field

    /**
     * General getter function for a command description.
     * SHOULD be overridden by subclass.
     * Returns an error message to be sent to player.
     * @return
     */
    public String getDescription()
    {
        String formattedString = (cData.failColor+"Error in obtained description for this command.");
        return formattedString;
    }

    /**
     * Returns an ArrayList containing all aliases for a command.
     * Should NOT be overridden.
     * @return
     */
    public final ArrayList<String> getAliases()
    {
        return Aliases;
    }

    /**
     * Method to be called by MainCommand.
     * CAN be overridden.
     * @param p
     * @return
     */
    public boolean commandFailedMessage(Player p)
    {
        if (p!=null)
        {
            Pet pet = Cerberus.obtainPetData(p);
            if (pet==null) {return false;}
            if (pet.getWolfStatus().equals(0))
            {
                p.sendMessage(cData.failColor+"You do not currently have a pet!");
            }
            else
            {
                p.sendMessage(cData.failColor+"This is not a valid command!");
            }
            return true;
        }
        return false;
    }

    /**
     * SHOULD be overridden. Default main function for subclass. Returns error message.
     * @param sender
     * @param args
     * @return
     */
    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player)
            commandFailedMessage((Player) sender);
        return false;
    }
    /**
     * SHOULD be overridden. Default main function, intended for CommandHelp or any command needing access
     * to the command list. Returns error message.
     * @param sender
     * @param args
     * @return
     */
    public boolean onCommand(CommandSender sender, String[] args, ArrayList<CerberusCommand> list) {
        if (sender instanceof Player)
            commandFailedMessage((Player) sender);
        return false;
    }

    /**
     * Should NOT be overridden by subclass.
     * Initializes chat color values, from the config.
     */

    /**
     * Default constructor. Should NOT be overridden, SHOULD be called by subclass.
     */
    public CerberusCommand()
    {
        Aliases = new ArrayList<>();
        Description = getDescription();
    }
}
