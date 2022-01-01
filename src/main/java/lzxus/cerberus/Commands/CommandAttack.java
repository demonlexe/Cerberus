package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.CerberusCommand;
import lzxus.cerberus.Structs.PetData;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandAttack extends CerberusCommand {

    public String getDescription()
    {
        String formattedString = (successColor + "/ce attack"
                + systemColor + " - " + dataColor + "Toggles if pet is allowed to attack."
                + "\n" + successColor + "/ce attack <AttackType>"
                + systemColor + " - " + dataColor + "Valid AttackTypes: a, p, m");
        return formattedString;
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            PetData pet = Cerberus.obtainPetData(p);
            assert pet!=null;

            if (pet.getWolfStatus().equals(1))
            {

                if (!ArrayUtils.isEmpty(args)){
                    String attackType = (args[0]).substring(0,1);
                    attackType = attackType.toLowerCase();
                    if (ArrayUtils.contains(validAttackTypes,attackType))
                    {
                        pet.setAttackType(attackType);
                        p.sendMessage(successColor+"Your pet will now attack: "+ dataColor +attackType);
                    }
                    else
                    {
                        //FIXME
                        p.sendMessage(failColor+"That is not a valid attack type! Valid types are all, passive, monsters");
                    }
                }
                else
                {
                    if (pet.getAttackStatus().equals(0))
                    {
                        pet.setAttackStatus(1);
                        p.sendMessage(successColor+ "Your pet will now attack mobs!");
                    }
                    else
                    {
                        pet.setAttackStatus(0);
                        p.sendMessage(failColor+"Your pet will no longer attack any mobs.");
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

    public CommandAttack(){
        super();
        Description = getDescription();
        CommandName = "attack";
        Aliases.add("attack");
    }
}
