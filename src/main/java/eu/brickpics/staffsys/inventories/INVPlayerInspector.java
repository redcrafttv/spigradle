package eu.brickpics.staffsys.inventories;

import eu.brickpics.staffsys.util.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import net.geknxddelt.informationsystem.api.ColorAPI;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class INVPlayerInspector implements InventoryProvider {

    private OfflinePlayer player;

    public void setPlayer(OfflinePlayer player) { this.player = player; }
    public OfflinePlayer getPlayer() { return player; }



    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fillBorders(ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE).setData((short) ColorAPI.getColorPlayer(player)).build()));
        contents.set(1, 1, ClickableItem.empty(
                new ItemBuilder(Material.SKULL_ITEM)
                        .setData((short) 3)
                        .setSkullOwner(this.getPlayer().getName())
                        .setDisplayName(getPlayer().getName())
                        .build()
        ));

        //InfoSys: Coins, Time, Language
        //StaffSystem: Cases
        contents.set(1, 2, ClickableItem.of(new ItemBuilder(Material.BOOK).setDisplayName("§rSearch Databases:").build(), inventoryClickEvent -> {

        }));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
