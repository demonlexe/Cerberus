package lzxus.cerberus;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/*
-------------------------------------------------------
Plugin written by Alexis Kaufman, a.k.a lzxus.
First version published 12/29/2021
-------------------------------------------------------
 */

public final class Cerberus extends JavaPlugin {

    private static FileConfiguration config;
    private static Cerberus plugin;
    private static double [] xpRequirementList; //Holds the full list of XP requirements
    private static Hashtable<Player, Wolf> wList = new Hashtable<Player, Wolf>(); //Key is Player, Value is live Wolf entity
    private static Hashtable<String, Player> pList = new Hashtable<String, Player>(); //Key is UUID, Value is Player

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new DogBehavior(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
        getServer().getPluginManager().registerEvents(new DogTamed(), this);
        getServer().getPluginManager().registerEvents(new EntityDamaged(), this);
        getServer().getPluginManager().registerEvents(new DogDied(), this);
        this.getCommand("callow").setExecutor(new CommandAllowDamage());
        this.getCommand("cstats").setExecutor(new CommandViewStats());
        this.getCommand("creset").setExecutor(new CommandResetPlayer());
        this.getCommand("cbring").setExecutor(new CommandBringPet());
        this.getCommand("cname").setExecutor(new CommandNamePet());
        this.getCommand("cdebug").setExecutor(new CommandDebugData());
        this.getCommand("cclean").setExecutor(new CommandClean());
        plugin = this;
        config = getConfig();

        //Main Config Set-up
        config.addDefault("levelMultiplier", 50);
        config.addDefault("xpBaseLevel",30.0);
        config.addDefault("maxLevel",100);

        //Updates Main Config
        config.options().copyDefaults(true);
        saveConfig();


        //Level-array Set-up
        int maxLevel = config.getInt("maxLevel");
        double xpBaseLevel = config.getDouble("xpBaseLevel");
        double levelMultiplier = config.getDouble("levelMultiplier");

        double [] xpRequirements = new double[maxLevel+1];

        xpRequirements[0] = 0.0;
        xpRequirements[1] = xpBaseLevel;
        for (int j = 2; j <= maxLevel; j++)
        {
            xpRequirements[j] = xpRequirements[j-1] + (levelMultiplier*1.25);
        }
        xpRequirementList = xpRequirements;

        /* Outputs the XP list
        for (int i = 0; i <= maxLevel; i++)
        {
            System.out.println("At position: "+i+" Value: "+xpRequirementList[i]);
        }
        */

        System.out.println("Cerberus has completed start-up behaviors and is successfully launched.");
    }

    @Override
    public void onDisable() {
        System.out.println("Cerberus has closed down.");
        // Plugin shutdown logic
    }

    //Public Functions, non-startup and non-shutdown

    //Obtains plugin, as Cerberus
    public static Cerberus getPlugin() {
        return plugin;
    }

    //Obtains config, as FileConfiguration
    public static FileConfiguration obtainConfig() {return config; }

    //Obtains XP List, as double array
    public static double [] obtainXPList() {return xpRequirementList; }

    /* Public mutator function, updates wList and pList:
    - boolean active serves to determine if the intended call is to add values (true) or destroy values (false)
    */
    public static void updateWolfList(Wolf w, Player p, boolean active)
    {
        String obtainedID = PlayerWolfData.getWolfUUID(p);
        if (active){
            System.out.println("Calling for update with "+ obtainedID);
            if (obtainedID!=null && w!=null && (w.getUniqueId().toString()).equals(obtainedID))
            {
                wList.put(p,w);
                pList.put(obtainedID,p);
            }
            else if (PlayerWolfData.getWolfStatus(p).equals(1) && w!=null)
            {
                obtainedID = w.getUniqueId().toString();
                PlayerWolfData.setWolfUUID(p,obtainedID);
                wList.put(p,w);
                pList.put(obtainedID,p);
            }
        }
        else
        {
            Wolf wObtained = wList.get(p);
            if (wObtained != null) {
                wList.remove(p);
                wObtained.remove();
                wObtained.setHealth(0);
            }
            if (pList.get(obtainedID)!=null)
                pList.remove(obtainedID);
        }

    }

    // Public getter function, returns a Wolf from wList using Player as the key.
    public static Wolf obtainFromWolfList(Player p)
    {
        System.out.println("Calling for Wolfobtainer with "+p);
        Wolf w = wList.get(p);
        if (w!=null)
        {
            return w;
        }
        return null;
    }
    public static Player obtainFromPlayerList(String id)
    {
        System.out.println("Calling for Playerobtainer with "+id);
        Player p = pList.get(id);
        if (p!=null)
        {
            return p;
        }
        return null;
    }
}
