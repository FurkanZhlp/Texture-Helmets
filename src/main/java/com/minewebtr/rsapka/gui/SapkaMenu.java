package com.minewebtr.rsapka.gui;

import com.minewebtr.rsapka.RSapka;
import com.minewebtr.rsapka.gui.providers.SapkaMenuProvider;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SapkaMenu {
    public static void open(final Player player,final  Integer page) {
        final SmartInventory INVENTORY = SmartInventory.builder()
                .id("sapka_menu_"+player.getName())
                .provider(new SapkaMenuProvider(page))
                .closeable(true)
                .size(6,9)
                .title(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(RSapka.config().getConfig().getString("menu-basligi"))))
                .build();
        INVENTORY.open(player);
    }
}
