package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.PetData;
import lzxus.cerberus.Structs.PlayerReset;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class CommandResetPlayer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            PetData pet = Cerberus.obtainPetData(p);
            if (pet != null)
            {
                pet = null;
                Cerberus.updateWolfList(pet,p,false);
            }
            PlayerReset.resetP(p,null);
            return true;
        }
        return false;
    }
}
