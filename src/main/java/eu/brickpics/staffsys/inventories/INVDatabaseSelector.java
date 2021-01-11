package eu.brickpics.staffsys.inventories;

import eu.brickpics.staffsys.StaffSys;
import eu.brickpics.staffsys.util.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInvsPlugin;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import net.geknxddelt.informationsystem.api.ColorAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class INVDatabaseSelector implements InventoryProvider {

    private final StaffSys plugin;

    public INVDatabaseSelector(StaffSys plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        ArrayList<String> tables = new ArrayList<>();
        //rows = tabellen count / 7 + 2
        contents.fillBorders(ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE).setData((short) ColorAPI.getColorPlayer(player)).setNoName().build()));
        Pagination pagination = contents.pagination();

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SHOW TABLES;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tables.add(rs.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        ClickableItem[] items = new ClickableItem[tables.size() + 101];


        for (int i = 0; i < items.length; i++) {
            items[i] = ClickableItem.empty(
                    new ItemBuilder(Material.IRON_BLOCK)
                            .setAmount(i + 1)
                            .setDisplayName("§r" + i)
                            .build()
            );
        }

        pagination.setItems(items);
        pagination.setItemsPerPage(28);


        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1).allowOverride(false));

        contents.set(5, 3, ClickableItem.of(new ItemBuilder(Material.ARROW).setDisplayName("§rPrevious").build(), inventoryClickEvent -> {
            InventoryManager.open("databaseSelectorInv", player, SmartInvsPlugin.manager().getInventory(player).get().getParent().get(), new String[]{}, plugin,
                    pagination.previous().getPage());
        }));
        contents.set(5, 5, ClickableItem.of(new ItemBuilder(Material.ARROW).setDisplayName("§rNext").build(), inventoryClickEvent -> {
            InventoryManager.open("databaseSelectorInv", player, SmartInvsPlugin.manager().getInventory(player).get().getParent().get(), new String[]{}, plugin,
                    pagination.next().getPage());
        }));



    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    public Connection getConnection() throws SQLException {
        return plugin.getDataSource().getConnection();
    }
}
