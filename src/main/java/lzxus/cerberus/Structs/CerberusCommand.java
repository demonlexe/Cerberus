package lzxus.cerberus.Structs;

import lzxus.cerberus.Cerberus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CerberusCommand {
    protected String failColor;
    protected String successColor;
    protected String dataColor;
    protected String systemColor;
    protected String [] validAttackTypes;
    protected double [] xpList;

    protected String CommandName;
    protected ArrayList<String> Aliases;
    protected String Description;
    //FIXME Add Permission Field

    public String getDescription()
    {
        String formattedString = (failColor+"Error in obtained description for this command.");
        return formattedString;
    }

    public ArrayList<String> getAliases()
    {
        return Aliases;
    }

    public boolean commandFailedMessage(Player p)
    {
        if (p!=null)
        {
            PetData pet = Cerberus.obtainPetData(p);
            assert pet!=null;
            if (pet.getWolfStatus().equals(0))
            {
                p.sendMessage(failColor+"You do not currently have a pet!");
            }
            else
            {
                p.sendMessage(failColor+"This is not a valid command!");
            }
            return true;
        }
        return false;
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player)
            commandFailedMessage((Player) sender);
        return false;
    }
    public boolean onCommand(CommandSender sender, String[] args, ArrayList<CerberusCommand> list) {
        if (sender instanceof Player)
            commandFailedMessage((Player) sender);
        return false;
    }

    protected void initializeConfigData()
    {
            failColor = ConfigFunctions.getChatColor("failureChatColor");
            successColor = ConfigFunctions.getChatColor("successChatColor");
            dataColor = ConfigFunctions.getChatColor("dataChatColor");
            systemColor = ConfigFunctions.getChatColor("systemChatColor");
            validAttackTypes = PetData.getTypeList();
            xpList = Cerberus.obtainXPList();
    }

    public CerberusCommand()
    {
        initializeConfigData();
        Aliases = new ArrayList<>();
        Description = getDescription();
    }
}
