package lzxus.cerberus.Commands;

import lzxus.cerberus.Structs.ConfigFunctions;
import lzxus.cerberus.Structs.PetData;
import lzxus.cerberus.Structs.PlayerWolfData;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAttack implements CommandExecutor {
    private static String [] validAttackTypes;
    private static String failColor = null;
    private static String successColor = null;
    private static String dataColor = null;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (validAttackTypes == null)
            {
                validAttackTypes = PetData.getTypeList();
            }
            if (failColor == null){
                failColor = ConfigFunctions.getChatColor("failureChatColor");
            }
            if (successColor == null){
                successColor = ConfigFunctions.getChatColor("successChatColor");
            }
            if (dataColor == null){
                dataColor = ConfigFunctions.getChatColor("dataChatColor");
            }

            if (!ArrayUtils.isEmpty(args)){
                String attackType = (args[0]).substring(0,1);
                attackType = attackType.toLowerCase();
                if (ArrayUtils.contains(validAttackTypes,attackType))
                {
                    PlayerWolfData.setAttackType(p,attackType);
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
                if (PlayerWolfData.getAttackStatus(p).equals(0))
                {
                    PlayerWolfData.setAttackStatus(p,1);
                    p.sendMessage(successColor+ "Your pet will now attack mobs!");
                }
                else
                {
                    PlayerWolfData.setAttackStatus(p,0);
                    p.sendMessage(failColor+"Your pet will no longer attack any mobs.");
                }
            }

            return true;
        }
        return false;
    }
}
