package me.chrisx97.mobhuntevent.commands;

import me.chrisx97.mobhuntevent.managers.GameManager;
import me.chrisx97.mobhuntevent.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MobHuntCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                return false;
            }
            if (args[0].equalsIgnoreCase("start")) {
                if (player.isOp()) {
                    if (GameManager.getInstance().getState() == null) {
                        GameManager.getInstance().startGame();
                        return true;
                    }
                    player.sendMessage(Messages.eventAlreadyRunningMsg);
                    return false;
                }
                player.sendMessage(Messages.noPermissionMsg);
                return false;
            }
            player.sendMessage(Messages.invalidCommandMsg);
            return false;
        }
        sender.sendMessage(Messages.notaPlayerMsg);
        return false;
    }
}
