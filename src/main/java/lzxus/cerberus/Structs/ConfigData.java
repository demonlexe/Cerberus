package lzxus.cerberus.Structs;

import lzxus.cerberus.Cerberus;
import org.bukkit.Color;

public class ConfigData {
    public static final String [] attackTypeList = {"all", "passive", "monster"};

    public String failColor;
    public String successColor;
    public String dataColor;
    public String systemColor;
    public Color newColor;

    public double [] xpList;

    public ConfigData()
    {
        failColor = ConfigFunctions.getChatColor("failureChatColor");
        successColor = ConfigFunctions.getChatColor("successChatColor");
        dataColor = ConfigFunctions.getChatColor("dataChatColor");
        systemColor = ConfigFunctions.getChatColor("systemChatColor");
        newColor = ConfigFunctions.getColor("successColor");
        xpList = Cerberus.obtainXPList();
    }

    public String displayTypeList()
    {
        String toR = "";
        for (String s : attackTypeList)
        {
            toR = toR + s +", ";
        }
        toR = toR.substring(0,toR.length()-2);
        return toR;
    }
}
