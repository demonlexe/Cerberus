package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.petdata.Pet;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Should be constructed as an object by MainCommand.
 * Toggles if the pet is allowed to attack, and what it can attack.
 */
public class CommandAttack extends CerberusCommand {

    /**
     * CAN be overridden by subclass.
     * Returns a formatted string describing command function.
     * @return
     */
    public String getDescription()
    {
        String formattedString = (cData.successColor + "/ce attack"
                + cData.systemColor + " - " + cData.dataColor + "Toggles if pet is allowed to attack."
                + "\n" + cData.successColor + "/ce attack <AttackType>"
                + cData.systemColor + " - " + cData.dataColor + "Valid AttackTypes: "+cData.displayTypeList());
        return formattedString;
    }

    /**
     * Code to be run when a Player executes this command.
     * If player has a wolf, and does not provide parameters, toggles if pet can attack.
     * If player has a wolf and provides parameters, changes attack type.
     * CAN be overridden by subclass.
     */
    public boolean onCommand(final CommandSender sender, final String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Pet pet = Cerberus.obtainPetData(p);

            if (pet!=null && pet.getWolfStatus().equals(1))
            {

                if (!ArrayUtils.isEmpty(args)){
                    String attackType = args[0];
                    attackType = attackType.toLowerCase();
                    if (ArrayUtils.contains(cData.attackTypeList,attackType))
                    {
                        pet.setAttackType(attackType);
                        p.sendMessage(cData.successColor+"Your pet will now attack: "+ cData.dataColor +attackType);
                    }
                    else
                    {
                        //FIXME
                        p.sendMessage(cData.failColor+"That is not a valid attack type! Valid types are "+cData.displayTypeList());
                    }
                }
                else
                {
                    if (pet.getAttackStatus().equals(0))
                    {
                        pet.setAttackStatus(1);
                        p.sendMessage(cData.successColor+ "Your pet will now attack mobs!");
                    }
                    else
                    {
                        pet.setAttackStatus(0);
                        p.sendMessage(cData.failColor+"Your pet will no longer attack any mobs.");
                    }
                }

                return true;
            }
            else
            {
                commandFailedMessage(p);
                return true;
            }
        }
        return false;
    }


    /**
     * Constructor to be called by MainCommand. MUST call super();
     * Overwrites command info, such as Description, CommandName, Aliases.
     */
    public CommandAttack(){
        super();
        Description = getDescription();
        CommandName = "attack";
        Aliases.add("attack");
    }
}
