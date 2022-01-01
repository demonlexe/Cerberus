package lzxus.cerberus.Commands;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Structs.ConfigFunctions;
import lzxus.cerberus.Structs.PetData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDebugData implements CommandExecutor {

    private static String successColor = null;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            if (successColor == null){
                successColor = ConfigFunctions.getChatColor("successChatColor");
            }

            Player p = (Player) sender;
            PetData pet = Cerberus.obtainPetData(p);
            assert pet != null;
            p.sendMessage(successColor+ "wolf-xp: "+ pet.getWolfXp()+
                    "\n"+"wolf-lvl: "+pet.getWolfLvl()+
                    "\n"+"wolf-status: "+pet.getWolfStatus()+
                    "\n"+"wolf-uuid: "+pet.getWolfUUID()+
                    "\n"+"damageEnabled: "+pet.getDamageEnabled()+
                    "\n"+"wolf-name: "+pet.getWolfName() +
                    "\n"+"wolf-owned: "+pet.getWolfOwned() +
                    "\n"+"wolf-attack-status: "+pet.getAttackStatus() +
                    "\n"+"wolf-attack-type: "+pet.getAttackType()

            );
            return true;
        }
        return false;
    }
}
