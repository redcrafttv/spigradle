package eu.brickpics.staffsys.commands;

import eu.brickpics.staffsys.StaffSys;
import eu.brickpics.staffsys.inventories.InventoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDInspect implements CommandExecutor {

    private final StaffSys plugin;

    public CMDInspect(StaffSys plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        InventoryManager.open("playerInspectorInv", (Player) commandSender, null, new String[]{strings[0]}, plugin, 0);
        return true;
    }
}
