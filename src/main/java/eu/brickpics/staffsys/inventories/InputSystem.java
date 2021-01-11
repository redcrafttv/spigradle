package eu.brickpics.staffsys.inventories;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.ChunkPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import eu.brickpics.staffsys.StaffSys;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


public class InputSystem {

    private final ProtocolManager pm;
    private final StaffSys plugin;

    public InputSystem(ProtocolManager pm, StaffSys pl) {
        this.pm = pm;
        this.plugin = pl;
        createListeners();
    }

    public void createListeners() {
        pm.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.UPDATE_SIGN) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                for (Field field : packet.getStrings().getFields()) {
                    Bukkit.getLogger().info(field.getName());
                }
            }
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                for (Field field : packet.getStrings().getFields()) {
                    Bukkit.getLogger().info(field.getName());
                }
            }
        });
    }


    public void requestInput(Player p) {
        PacketContainer openSign = new PacketContainer(PacketType.Play.Server.OPEN_SIGN_EDITOR);
        openSign.getBlockPositionModifier().write(0, new BlockPosition(1000, 1000, 1000));

        try {
            pm.sendServerPacket(p, openSign);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(
                    "Cannot send packet " + openSign, e);
        }
    }
}
