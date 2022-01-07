package lzxus.cerberus.commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.attacks.SpecialAttack;
import lzxus.cerberus.commands.CerberusCommand;
import lzxus.cerberus.configdata.ConfigFunctions;
import lzxus.cerberus.petdata.Pet;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.Wolf;
import org.bukkit.util.Vector;

import java.util.ArrayList;

/**
 * Should be constructed as an object by CommandMain. View all learnable Special Attacks.
 */
public class CommandLearn extends CerberusCommand {
/**
 * CAN be overridden by subclass.
 * Returns a formatted string describing command function.
 * @return
 */
public String getDescription()
        {
        String formattedString = (cData.successColor + "/ce " + CommandName
        + cData.systemColor + " - " + cData.dataColor + "View all learnable Special Attacks.");
        return formattedString;
        }

    public boolean commandSuccessMessage(Player p, String newAtk) {
        if (p!=null)
        {
            p.sendMessage(cData.successColor+"You have learned the Special Attack "+cData.systemColor+ChatColor.ITALIC+newAtk);
            return true;
        }
        return false;
    }

    /**
 * Code to be run when a Player executes this command.
 * View all learnable Special Attacks.
 * CAN be overridden by subclass.
 */

public boolean onCommand(final CommandSender sender, final String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Pet pet = Cerberus.obtainPetData(p);

            if (pet!=null)
                {
                    if (pet.getWolfStatus().equals(1))
                    {
                        ArrayList<SpecialAttack> aList = pet.getAttackList();
                        String attackToLearn = null;
                        if (args.length >= 1) {attackToLearn = args[0];}

                        if (attackToLearn == null)
                        {
                            if (aList== null) {return false;}

                            String s = cData.systemColor+"------------------"+ "\n" + cData.successColor + ChatColor.BOLD+ "All Learnable Attacks:"+
                                    "\n" ;

                            for (SpecialAttack atk : aList)
                            {
                                s+= atk.getAttackInfo() + "\n";
                            }
                            p.sendMessage(s+"------------------");

                        }
                        else
                        {
                            for (SpecialAttack atk : aList)
                            {
                                if (attackToLearn.equalsIgnoreCase(atk.getNameInData()))
                                {
                                    if (pet.getSpecial1().equals(""))
                                    {
                                        pet.setSpecial1(attackToLearn.toLowerCase());
                                        return commandSuccessMessage(p,atk.getNameInData());
                                    }
                                    else if (pet.getSpecial2().equals(""))
                                    {
                                        pet.setSpecial2(attackToLearn.toLowerCase());
                                        return commandSuccessMessage(p,atk.getNameInData());
                                    }
                                    else if (pet.getSpecial3().equals(""))
                                    {
                                        pet.setSpecial3(attackToLearn.toLowerCase());
                                        return commandSuccessMessage(p,atk.getNameInData());
                                    }
                                    else
                                    {
                                        p.sendMessage(cData.failColor+"You already have learned 3 Special Attacks!");
                                    }
                                }
                            }
                        }
                    }
                    else
                        {
                        commandFailedMessage(p);
                    }
                    return true;
                }
            }
            return false;
        }

/**
 * Constructor to be called by CommandMain. MUST call super();
 * Overwrites command info, such as Description, CommandName, Aliases.
 */
public CommandLearn()
    {
            super();
            Description = getDescription();
            CommandName = "learn";
            Aliases.add("learn");
            Aliases.add("train");
            Aliases.add("l");
            Aliases.add("learnattacks");
            Aliases.add("viewattacks");
            Aliases.add("seeattacks");
            Aliases.add("attacks");
            }

}
