package lzxus.cerberus.Structs;

import lzxus.cerberus.Cerberus;
import org.bukkit.Color;

public class ConfigData {
    public String failColor;
    public String successColor;
    public String dataColor;
    public String systemColor;
    public Color newColor;

    public String [] validAttackTypes;
    public double [] xpList;

    public ConfigData()
    {
        failColor = ConfigFunctions.getChatColor("failureChatColor");
        successColor = ConfigFunctions.getChatColor("successChatColor");
        dataColor = ConfigFunctions.getChatColor("dataChatColor");
        systemColor = ConfigFunctions.getChatColor("systemChatColor");
        newColor = ConfigFunctions.getColor("successColor");
        validAttackTypes = PetData.getTypeList();
        xpList = Cerberus.obtainXPList();
    }
}
