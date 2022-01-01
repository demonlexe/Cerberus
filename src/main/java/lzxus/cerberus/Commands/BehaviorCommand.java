package lzxus.cerberus.Commands;

import lzxus.cerberus.Structs.ConfigFunctions;
import org.bukkit.entity.Player;

public class BehaviorCommand {
    private static String failColor = null;
    private static String successColor = null;
    private static String dataColor = null;
    private static String systemColor = null;

    public static void updateColors(){
        if (failColor == null){
            failColor = ConfigFunctions.getChatColor("failureChatColor");
        }
        if (successColor == null){
            successColor = ConfigFunctions.getChatColor("successChatColor");
        }
        if (dataColor == null){
            dataColor = ConfigFunctions.getChatColor("dataChatColor");
        }
        if (systemColor == null){
            systemColor = ConfigFunctions.getChatColor("systemChatColor");
        }
    }

    public static boolean commandFailedMessage(Player p)
    {
        if (p!=null)
        {
            updateColors();
            p.sendMessage(failColor+"This is not a valid command!");
            return true;
        }
        return false;
    }
}
