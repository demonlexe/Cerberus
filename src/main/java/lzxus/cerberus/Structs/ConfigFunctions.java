package lzxus.cerberus.Structs;

import lzxus.cerberus.Cerberus;
import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigFunctions {
    private static FileConfiguration config = null;

    private static void configObtained()
    {
        if (config == null)
        {
            config = Cerberus.obtainConfig();
        }
    }

    public static String getChatColor(String s){
        configObtained();
        String obtainedS = config.getString(s);
        return obtainedS;
    }
    public static Color getColor(String s)
    {
        configObtained();
        List<Integer> rgbArray = config.getIntegerList(s);
        if (rgbArray != null && rgbArray.size() == 3)
        {
            Color newColor = Color.fromRGB(rgbArray.get(0),rgbArray.get(1),rgbArray.get(2));
            return newColor;
        }
        return null;
    }
}
