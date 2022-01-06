package lzxus.cerberus.configdata;

import lzxus.cerberus.Cerberus;
import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigFunctions {
    private FileConfiguration config = null;

    //CONFIG
    public String getChatColor(String s){
        String obtainedS = config.getString(s);
        return obtainedS;
    }

    public boolean getPvPAllowed()
    {
        return config.getBoolean("pvpEnabled");
    }

    //HARD-CODED
    public Color getColor(String s)
    {
        switch (s) {
            case "levelUpColor":
                return Color.fromRGB(50,232,5);
            case "warningColor":
                return Color.fromRGB(255,35,23);
        }
        return Color.fromRGB(255,255,255);
    }

    public ConfigFunctions()
    {
        config = Cerberus.obtainConfig();
    }
}
