package git.furkanzhlp.helmet;

import git.furkanzhlp.helmet.gui.HelmetMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelmetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        HelmetMenu.open((Player) commandSender,1);
        return false;
    }
}
