package git.furkanzhlp.helmet.gui.providers;

import git.furkanzhlp.helmet.TextureHemlets;
import git.furkanzhlp.helmet.gui.HelmetMenu;
import git.furkanzhlp.helmet.utils.RSapkaUtils;
import git.furkanzhlp.helmet.utils.YamlItem;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HelmetMenuProvider implements InventoryProvider {

    private Integer page;
    public HelmetMenuProvider(Integer page){
        this.page = page;
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        int size = TextureHemlets.config().getConfig().getConfigurationSection("sapkalar").getKeys(false).size();
        int page = this.page;
        int limit = 28;
        int pageCount = (int) Math.ceil((double) size / limit);
        int start = (page * limit) - limit;
        int finish = start + limit;
        int borderCounter = 0;
        int pageChecker = 0;
        YamlItem item;
        for (final String key : TextureHemlets.config().getConfig().getConfigurationSection("sapkalar").getKeys(false)) {
            if (borderCounter == 8 || borderCounter == 17 || borderCounter == 26 || borderCounter == 35 || borderCounter == 44 || borderCounter == 53) borderCounter++;
            if (borderCounter % 9 == 0) borderCounter++;
            pageChecker++;
            if(pageChecker >= start && pageChecker <= finish){
                item = new YamlItem("sapkalar."+key);
                if(player.hasPermission("rsapka.kullan."+key)){
                    item.replaceLore("%status", TextureHemlets.config().getConfig().getString("acik"));
                }else{
                    item.replaceLore("%status", TextureHemlets.config().getConfig().getString("kilitli"));
                    if(TextureHemlets.config.getConfig().getBoolean("kilitli-esya-gorunum.status")){
                        item.setType(Material.valueOf(TextureHemlets.config().getConfig().getString("kilitli-esya-gorunum.item").toUpperCase()));
                    }
                }
                item.setSlot(borderCounter);
                contents.set(item.getRow(), item.getColumn(), ClickableItem.of(item.complete(), e -> {
                    if(player.hasPermission("rsapka.kullan."+key)){
                        RSapkaUtils.SapkaGiydir(key,player);
                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                    }
                }));
                borderCounter++;
            }
        }
        if (page > 1) {
            item = new YamlItem("Previous-Page");
            contents.set(5, 3, ClickableItem.of(item.complete(), e -> {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                HelmetMenu.open(player, this.page - 1);
            }));
        }
        if (pageCount > 1 && page != pageCount) {
            item = new YamlItem("Next-Page");
            contents.set(5, 5, ClickableItem.of(item.complete(), e -> {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                HelmetMenu.open(player, this.page + 1);
            }));
        }
        ItemStack pHelmet = player.getInventory().getHelmet();
        final net.minecraft.server.v1_16_R3.ItemStack nmsitem = CraftItemStack.asNMSCopy(pHelmet);
        final NBTTagCompound nbtset = new NBTTagCompound();
        if (nmsitem.hasTag()) {
            nbtset.a(nmsitem.getTag());
        }
        if(nbtset.getBoolean("RSapkaItem")){
            item = new YamlItem("Remove-Helmet");
            contents.set(5, 4, ClickableItem.of(item.complete(), e -> {
                player.getInventory().setHelmet(new ItemStack(Material.AIR,1));
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                player.closeInventory();
                player.sendMessage(RSapkaUtils.colorizeRGB(TextureHemlets.config().getConfig().getString("Messages.Successfully-Removed")));
            }));
        }


    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
