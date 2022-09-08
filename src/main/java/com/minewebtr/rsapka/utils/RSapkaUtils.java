package com.minewebtr.rsapka.utils;

import com.minewebtr.rsapka.RSapka;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.MojangsonParser;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RSapkaUtils {
    public ItemStack setNbt(final ItemStack item, final String nbt) {
        final net.minecraft.server.v1_16_R3.ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        final NBTTagCompound nbtset = new NBTTagCompound();
        if (nmsitem.hasTag()) {
            nbtset.a(nmsitem.getTag());
        }
        try {
            nbtset.a(MojangsonParser.parse(nbt));
        }
        catch (CommandSyntaxException e) {
            //e.printStackTrace();
        }
        nmsitem.setTag(nbtset);
        return CraftItemStack.asBukkitCopy(nmsitem);
    }
    public String getNBT(ItemStack item){
        final net.minecraft.server.v1_16_R3.ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        final NBTTagCompound nbt = nmsitem.getTag();
        Boolean hasNMS = nbt != null;
        String nbtString = "";
        if(hasNMS && nmsitem.hasTag()) nbtString = nbt.toString();
        return nbtString;
    }
    public static Boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String colorizeRGB(String s) {
        if (!Bukkit.getVersion().contains("1.16")) {
            return colorize(s);
        }
        final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        for (Matcher match = pattern.matcher(s); match.find(); match = pattern.matcher(s)) {
            final String hexColor = s.substring(match.start(), match.end());
            s = s.replace(hexColor, ChatColor.of(hexColor).toString());
        }
        return colorize(s);
    }
    public static void SapkaGiydir(String key, Player player){
        if(player.hasPermission("rsapka.kullan."+key)){
            if(player.getInventory().getHelmet() != null){
                ItemStack helmet = player.getInventory().getHelmet();
                final net.minecraft.server.v1_16_R3.ItemStack nmsitem = CraftItemStack.asNMSCopy(helmet);
                final NBTTagCompound nbtset = new NBTTagCompound();
                if (nmsitem.hasTag()) {
                    nbtset.a(nmsitem.getTag());
                }
                if(!nbtset.getBoolean("RSapkaItem")){
                    player.sendMessage(RSapkaUtils.colorizeRGB(RSapka.config().getConfig().getString("Messages.Remove-Helmet")));
                    player.closeInventory();
                    return;
                }
            }
            YamlItem rHelmet = new YamlItem("sapkalar."+key);
            rHelmet.clearLore();
            player.getInventory().setHelmet(rHelmet.complete());
            player.closeInventory();
            player.sendMessage(RSapkaUtils.colorizeRGB(RSapka.config().getConfig().getString("Messages.Successfully-Weared")));
        }else{
            player.sendMessage(RSapkaUtils.colorizeRGB(RSapka.config().getConfig().getString("Messages.No-Permission")));
        }

    }

    public static String colorize(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
