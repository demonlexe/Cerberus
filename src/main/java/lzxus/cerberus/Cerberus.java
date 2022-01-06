package lzxus.cerberus;

import lzxus.cerberus.commands.*;
import lzxus.cerberus.listeners.*;
import lzxus.cerberus.structs.UpdateChecker;
import lzxus.cerberus.configdata.ConfigData;
import lzxus.cerberus.petdata.Pet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Hashtable;

/*
-------------------------------------------------------
Plugin written by Alexis Kaufman, a.k.a lzxus.
First version published 12/29/2021
-------------------------------------------------------
 */

/**
 * This is the main Cerberus class, extending JavaPlugin.
 */

public final class Cerberus extends JavaPlugin {

    private static FileConfiguration config;
    private static Cerberus plugin;
    private static double [] xpRequirementList; //Holds the full list of XP requirements
    private static Hashtable<Player, Pet> wList = new Hashtable<>(); //Key is Player, Value is live Wolf entity
    private static ConfigData cData;
    private static UpdateChecker uCheck;

    /**
     * Plugin start-up logic.
     * Registers all events/listeners.
     * Registers single-word utility commands ce, cclean, and cdebug
     * Sets up plugin config with default values, which is a FileConfiguration
     * Initializes the array of levels using data from config
     * Calls MainCommand.main() to set up CerberusCommand
     *
     */
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        uCheck = new UpdateChecker(this, 98849);
        config = getConfig();

        //Main Config Set-up
        config.addDefault("levelMultiplier", 50);
        config.addDefault("xpBaseLevel",30.0);
        config.addDefault("maxLevel",100);
        config.addDefault("systemChatColor", (ChatColor.GRAY).toString());
        config.addDefault("successChatColor", (ChatColor.GREEN).toString());
        config.addDefault("warningChatColor", (ChatColor.DARK_RED).toString());
        config.addDefault("failureChatColor", (ChatColor.RED).toString());
        config.addDefault("dataChatColor", (ChatColor.BLUE).toString());
        config.addDefault("pvpEnabled",false);

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

        //Initalize main methods:
        MainCommand.main();

        getServer().getPluginManager().registerEvents(new DogBehavior(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
        getServer().getPluginManager().registerEvents(new DogTamed(), this);
        getServer().getPluginManager().registerEvents(new EntityDamaged(), this);
        getServer().getPluginManager().registerEvents(new DogDied(), this);
        getServer().getPluginManager().registerEvents(new DogAttack(), this);
        getServer().getPluginManager().registerEvents(new ArmorStandBehavior(), this);
        this.getCommand("cdebug").setExecutor(new CommandDebugData());
        this.getCommand("cclean").setExecutor(new CommandClean());
        this.getCommand("ce").setExecutor(new MainCommand());

        cData = new ConfigData();

        Bukkit.getLogger().info("Cerberus has completed start-up behaviors and is successfully launched.");
    }

    /**
     * Plugin shutdown logic. For all online players, calls disconnection function.
     * @see PlayerLeave
     */
    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Cerberus has closed down.");
        for (Player p : Bukkit.getOnlinePlayers()){
            if (wList.get(p) != null)
            {
                PlayerLeave.funcOnDisconnect(p);
            }
        }
        // Plugin shutdown logic
    }

    //Public Functions, non-startup and non-shutdown

    /**Obtains plugin
     * @see Cerberus
     */
    public static Cerberus getPlugin() {
        return plugin;
    }

    /**
     * To be used by PlayerJoin
     */
    public static void checkForUpdates(Player p)
    {
        boolean isOp = p.isOp();
        if (!isOp) {return;}
        uCheck.getVersion(version -> {
            if (plugin.getDescription().getVersion().equals(version)) {
                Bukkit.getLogger().info("There is not a new update available for "+plugin.getName()+".");
            } else {
                p.sendMessage(cData.successColor+"There is a new update available for "+plugin.getName()+"! Please update to "+cData.failColor+version);
                Bukkit.getLogger().info("There is a new update available for "+plugin.getName()+"! Please update to "+version);
            }
        });
    }

    /**Obtains config
     * @see FileConfiguration
    */
    public static FileConfiguration obtainConfig() {return config; }

    /**Obtains XP List, generated from onEnabled(), as double array
     */
    public static double [] obtainXPList() {return xpRequirementList; }


    /**
     * Private function to aid updateWolfList
     * @param p
     * @param pet
     */
    private static void updateHelper(Player p, Pet pet)
    {
        if (wList.get(p) != null)
        {
            wList.remove(p);
        }
        wList.put(p,pet);
    }
    /** Public mutator function, updates wList:
     - String callType serves to determine case
     */
    public static void updateWolfList(Pet pet, Player p, String callType)
    {
        switch(callType)
        {
            case "NoPetOwned":
                updateHelper(p,pet);
                break;
            case "PetRemoved":
                Pet pObtained = wList.get(p);
                if (pObtained != null) {
                    Wolf obtainedWolf = pObtained.getWolf();
                    wList.remove(p);
                    if (obtainedWolf != null)
                    {
                        updateHelper(p,new Pet(p));
                        obtainedWolf.remove();
                        p.sendMessage(cData.successColor+"Your pet data has been reset!"
                                +"\n"+cData.systemColor+"To register a new pet, "
                                +cData.successColor+"tame another wolf"
                                +"\n"+cData.systemColor+"or right-click a tamed wolf with a "+cData.successColor+"bone.");

                        break;
                    }

                }
                updateHelper(p,new Pet(p));
                break;
            case "PetAdded":
                Wolf w = null;
                String obtainedID = null;

                if (pet!=null)
                    w = pet.getWolf();
                if (w!=null)
                {
                    obtainedID = pet.getWolfUUID();
                    if (obtainedID != null && w.getUniqueId().toString().equalsIgnoreCase(obtainedID))
                    {
                        updateHelper(p,pet);
                    }
                    else if (pet.getWolfStatus().equals(1))
                    {
                        obtainedID = w.getUniqueId().toString();
                        pet.setWolfUUID(obtainedID);
                        if (wList.get(p) != null)
                        {
                            wList.remove(p);
                        }
                        wList.put(p,pet);
                    }
                }
                break;
            default:
                Bukkit.getLogger().info("ERROR");
        }

    }

    /**
     * Used to obtain Pet for given Player p
     * @param p
     * @return
     */
    public static Pet obtainPetData(Player p)
    {
        if (!wList.isEmpty())
        {
            Pet pet = wList.get(p);
            if (pet!= null)
            {
                return pet;
            }
        }
        return null;
    }
}
