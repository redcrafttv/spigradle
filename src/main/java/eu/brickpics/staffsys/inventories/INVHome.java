package eu.brickpics.staffsys.inventories;

import eu.brickpics.staffsys.StaffSys;
import eu.brickpics.staffsys.util.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInvsPlugin;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import net.geknxddelt.informationsystem.api.ColorAPI;
import net.geknxddelt.informationsystem.manager.ItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class INVHome implements InventoryProvider {

    private final StaffSys plugin;

    public INVHome(StaffSys plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fillBorders(ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE).setData((short) ColorAPI.getColorPlayer(player)).build()));
        //-Dfile.encoding=UTF-8
        contents.set(1, 1, ClickableItem.empty(new ItemBuilder(Material.ITEM_FRAME).setDisplayName("§rBillboard").build()));
        //TODO: MAP für Tasks in der DB, EMPTY_MAP else, con ? rettrue : retfalse
        contents.set(1, 2, ClickableItem.empty(new ItemBuilder(Material.MAP).setDisplayName("§rTasks").build()));
        contents.set(1, 3, ClickableItem.empty(new ItemBuilder(Material.BOOK_AND_QUILL).setDisplayName("§rCase History").build()));
        contents.set(1, 4,
                ClickableItem.empty(new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("geknxddelt").setData((short) 3).setDisplayName("§rStaffList").build()));
        contents.set(1, 5, ClickableItem.empty(new ItemBuilder(Material.SKULL_ITEM).setData((short) 0).setDisplayName("§rLookup Player").build()));
        contents.set(1, 6, ClickableItem.empty(new ItemBuilder(Material.COMMAND).setDisplayName("§rStatus").build()));
        contents.set(1, 7, ClickableItem.empty(new ItemBuilder(Material.ANVIL).setDisplayName("§rSettings").build()));
        contents.set(2, 8, ClickableItem.of(new ItemBuilder(Material.BARRIER).setDisplayName("§rQuit").build(),
                inventoryClickEvent -> {
                    player.closeInventory();
                }));
        contents.set(2, 7, ClickableItem.of(new ItemManager(Material.BOOK).build(), inventoryClickEvent -> {
            InventoryManager.open("databaseSelectorInv", (Player) inventoryClickEvent.getWhoClicked(),
                    SmartInvsPlugin.manager().getInventory((Player) inventoryClickEvent.getWhoClicked()).get(),
                    new String[]{}, plugin, 0);
        }));
        contents.set(2, 6, ClickableItem.of(new ItemManager(Material.SIGN).build(), inventoryClickEvent -> {
            plugin.inputRequest.requestInput(player);
            //TODO
        }));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
