package lzxus.cerberus.Structs;

import lzxus.cerberus.Cerberus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CerberusCommand {
    protected static String failColor;
    protected static String successColor;
    protected static String dataColor;
    protected static String systemColor;
    protected static String [] validAttackTypes;
    protected static double [] xpList;
    private static boolean dataInitialized = false;

    public static String getDescription()
    {
        String formattedString = (failColor+"Error in obtained description for this command.");
        return formattedString;
    }

    public static boolean commandFailedMessage(Player p)
    {
        if (p!=null)
        {
            p.sendMessage(failColor+"This is not a valid command!");
            return true;
        }
        return false;
    }

    public static boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player)
            commandFailedMessage((Player) sender);
        return false;
    }

    protected static void initializeData()
    {
        if (!dataInitialized)
        {
            failColor = ConfigFunctions.getChatColor("failureChatColor");
            successColor = ConfigFunctions.getChatColor("successChatColor");
            dataColor = ConfigFunctions.getChatColor("dataChatColor");
            systemColor = ConfigFunctions.getChatColor("systemChatColor");
            validAttackTypes = PetData.getTypeList();
            xpList = Cerberus.obtainXPList();
            dataInitialized = true;
        }
    }
}
