package eu.brickpics.staffsys.commands;

import eu.brickpics.staffsys.StaffSys;
import eu.brickpics.staffsys.inventories.InventoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDStaffSys implements CommandExecutor {

    private final StaffSys plugin;

    public CMDStaffSys(StaffSys plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        InventoryManager.open("homeInv", (Player) commandSender, null, new String[]{}, plugin, 0);
        return true;
    }
}
