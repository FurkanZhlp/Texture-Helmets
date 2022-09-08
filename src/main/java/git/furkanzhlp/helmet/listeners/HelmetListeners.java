package git.furkanzhlp.helmet.listeners;

import git.furkanzhlp.helmet.TextureHemlets;
import git.furkanzhlp.helmet.utils.RSapkaUtils;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class HelmetListeners implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        for (ItemStack i : new ArrayList<ItemStack>(e.getDrops())) {
            final net.minecraft.server.v1_16_R3.ItemStack nmsitem = CraftItemStack.asNMSCopy(i);
            final NBTTagCompound nbtset = new NBTTagCompound();
            if (nmsitem.hasTag()) {
                nbtset.a(nmsitem.getTag());
            }
            if(nbtset.getBoolean("RSapkaItem")){
                e.getDrops().remove(i);
            }
        }
    }

    @EventHandler(priority =  EventPriority.MONITOR, ignoreCancelled = true)
    public void onHelmetRemove(InventoryClickEvent e){

        if(e.getInventory().getType() != InventoryType.CRAFTING && e.getInventory().getType() != InventoryType.PLAYER) return;
        if(e.getSlotType() != InventoryType.SlotType.ARMOR && e.getSlotType() != InventoryType.SlotType.QUICKBAR && e.getSlotType() != InventoryType.SlotType.CONTAINER) return;


        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        final net.minecraft.server.v1_16_R3.ItemStack nmsitem = CraftItemStack.asNMSCopy(clickedItem);
        final NBTTagCompound nbtset = new NBTTagCompound();
        if (nmsitem.hasTag()) {
            nbtset.a(nmsitem.getTag());
        }
        if(nbtset.getBoolean("RSapkaItem")){
            e.setCancelled(true);
            e.setCurrentItem(new ItemStack(Material.AIR,1));
            p.sendMessage(RSapkaUtils.colorizeRGB(TextureHemlets.config().getConfig().getString("Messages.Successfully-Removed")));
            return;
        }
    }

//    @EventHandler
//    public void ArmorChange(CMIArmorChangeEvent e) {
//        Player p = e.getPlayer();
//        if(e.getPlayer().getDisplayName().equals("FurkanZhlp")){
//            p.sendMessage(e.getOldArmorPiece().getType().toString());
//            if(e.getType().equals(ArmorTypes.HELMET)){
//                p.sendMessage(e.getOldArmorPiece().getType().toString());
//            }
//        }
//    }


}
