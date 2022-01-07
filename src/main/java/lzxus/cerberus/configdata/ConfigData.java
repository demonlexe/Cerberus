package lzxus.cerberus.configdata;

import lzxus.cerberus.Cerberus;
import org.bukkit.Bukkit;
import org.bukkit.Color;

public class ConfigData extends ConfigFunctions{
    //HARD-CODED
    public static final String [] attackTypeList = {"all", "animals", "monster", "raider","creature","watermob"};

    public String failColor;
    public String successColor;
    public String dataColor;
    public String systemColor;
    public Color newColor;
    boolean PvPAllowed;

    public double [] xpList;


    public ConfigData()
    {
        super();
        failColor = getChatColor("failureChatColor");
        successColor = getChatColor("successChatColor");
        dataColor = getChatColor("dataChatColor");
        systemColor = getChatColor("systemChatColor");
        newColor = getColor("levelUpColor");
        xpList = Cerberus.obtainXPList();
        PvPAllowed = getPvPAllowed();
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
