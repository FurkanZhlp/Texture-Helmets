package git.furkanzhlp.helmet.gui;

import git.furkanzhlp.helmet.TextureHemlets;
import git.furkanzhlp.helmet.gui.providers.HelmetMenuProvider;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;

public class HelmetMenu {
    public static void open(final Player player,final  Integer page) {
        final SmartInventory INVENTORY = SmartInventory.builder()
                .id("sapka_menu_"+player.getName())
                .provider(new HelmetMenuProvider(page))
                .closeable(true)
                .size(6,9)
                .title(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(TextureHemlets.config().getConfig().getString("menu-basligi"))))
                .build();
        INVENTORY.open(player);
    }
}
