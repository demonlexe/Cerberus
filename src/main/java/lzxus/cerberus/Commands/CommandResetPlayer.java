package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.CerberusCommand;
import lzxus.cerberus.Structs.ConfigFunctions;
import lzxus.cerberus.Structs.PetData;
import lzxus.cerberus.Structs.PlayerReset;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandResetPlayer extends CerberusCommand {


    public static String getDescription()
    {
        initializeData();
        String formattedString = (successColor + "/ce reset"
                + systemColor + " - " + dataColor + "Kills your current pet.");
        return formattedString;
    }

    public static boolean onCommand(CommandSender sender, String[] args) {
        initializeData();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            PetData pet = Cerberus.obtainPetData(p);
            if (pet != null)
            {
                pet = null;
                Cerberus.updateWolfList(pet,p,false);
            }
            PlayerReset.resetP(p,null);
            p.sendMessage(successColor+"Your pet data has been reset!");
            return true;
        }
        return false;
    }
}
