package eu.brickpics.staffsys.inventories;

import eu.brickpics.staffsys.StaffSys;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.SmartInvsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class InventoryManager{

    private static SmartInventory getNewSmartInventory(Player opener, String id, SmartInventory parent, StaffSys plugin) {
        SmartInventory.Builder inventoryBuilder = SmartInventory.builder();
        switch (id) {
            case "homeInv":
                inventoryBuilder
                        .id("homeInv" + opener.getName())
                        .provider(new INVHome(plugin))
                        .size(3, 9)
                        .title("StaffSys - Home");
                break;
            case "createCaseInv":
            case "caseInspectorInv":
            case "caseLogInv":
            case "billboardInv":
            case "tasksInv":
            case "staffListInv":
            case "lookupPlayerInv":
            case "statusInv":
                break;
            case "playerInspectorInv":
                inventoryBuilder
                        .id("playerInspectorInv" + opener.getName())
                        .provider(new INVPlayerInspector())
                        .size(3, 9)
                        .title("Player Inspector: ");
                break;
            case "databaseSelectorInv":
                inventoryBuilder
                        .id("databaseSelectorInv" + opener.getName())
                        .provider(new INVDatabaseSelector(plugin))
                        .size(6, 9)
                        .title("Player Inspector: ");

        }

        if (parent != null)
            inventoryBuilder.parent(parent);

        return inventoryBuilder.build();
    }

    @SuppressWarnings("deprecation")
    public static SmartInventory open(String id, Player player, SmartInventory parent, String[] args, StaffSys plugin, int page) {
        fr.minuskube.inv.InventoryManager manager = SmartInvsPlugin.manager();
        SmartInventory newinv = getNewSmartInventory(player, id, parent, plugin);

        if (newinv.getId().startsWith("playerInspectorInv")) {
            //newinv.getProvider().getClass().getField("player").set(newinv.getProvider(), Bukkit.getOfflinePlayer(args[0]));
            ((INVPlayerInspector) newinv.getProvider()).setPlayer(Bukkit.getOfflinePlayer(args[0]));
        }


        newinv.open(player, page);

        return newinv;
    }


}
