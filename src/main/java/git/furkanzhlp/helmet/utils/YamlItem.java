package com.minewebtr.rsapka.utils;


import com.minewebtr.rsapka.RSapka;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class YamlItem {
    private Material type;
    private String name;
    private List<String> lore;
    private int amount;
    private short dataValue;
    private boolean glow;
    private String nbt;
    private String path;
    private int slot;

    public YamlItem(final String path) {
        this.path = path;
        this.setType(((RSapka.config().getConfig().get(path + ".item") instanceof String)) ? Material.valueOf(RSapka.config().getConfig().getString(path + ".item").toUpperCase()) : Material.AIR);
        this.setName(((RSapka.config().getConfig().get(path + ".name") instanceof String)) ? RSapka.config().getConfig().getString(path + ".name") : null);
        this.setLore(RSapka.config().getConfig().getStringList(path + ".lore"));
        this.setSlot(((RSapka.config().getConfig().get(path + ".slot") instanceof Integer)) ? RSapka.config().getConfig().getInt(path + ".slot") : 0);
        this.setGlow(((RSapka.config().getConfig().get(path + ".glow") instanceof Boolean)) ? RSapka.config().getConfig().getBoolean(path + ".glow") : false);
    }

    public String getKey(String key) {
        return RSapka.config().getConfig().getString(this.path + "."+key);
    }
    public Material getType() {
        return this.type;
    }
    public YamlItem setType(final Material type) {
        this.type = type;
        return this;
    }

    public Boolean getGlow() {
        return this.glow;
    }
    public YamlItem setGlow(final Boolean type) {
        this.glow = type;
        return this;
    }

    public Integer getAmount() {
        return 1;
    }
    public YamlItem setAmount(final Integer amount) {
        this.amount = amount;
        return this;
    }

    public String getName() {
        return this.name;
    }
    public YamlItem setName(final String name) {
        this.name = name;
        return this;
    }

    // slot = 10
    // 10/9 = 1
    // 1 * 9 - 1 = 8 / 10 - 8 = 2
    public int getRow(){ return (int)Math.floor(this.slot/9);}
    public int getColumn(){ return (int)Math.floor(this.slot%9);}
    public int getSlot() {
        return this.slot;
    }
    public YamlItem setSlot(final int slot) {
        this.slot = slot;
        return this;
    }

    public List<String> getLore() {
        return this.lore;
    }
    public YamlItem setLore(final List<String> lore) {
        this.lore = lore;
        return this;
    }
    public YamlItem clearLore() {
        this.lore.clear();
        return this;
    }
    public YamlItem replaceLore(final String search,final String replace){
        final List<String> list = new ArrayList<String>();
        for (final String lores : this.getLore()) {
            list.add(RSapkaUtils.colorizeRGB(lores.replace(search,(replace.equals("")) ? "-" : replace)));
        }
        this.setLore(list);
        return this;
    }

    public ItemStack complete(){
        final ItemStack item = new ItemStack(this.getType(), this.getAmount());
        final ItemMeta imeta = item.getItemMeta();
        if(this.getName() != null) imeta.setDisplayName(RSapkaUtils.colorizeRGB(this.getName()));
        final List<String> list = new ArrayList<String>();
        for (final String lores : this.getLore()) {
            list.add(RSapkaUtils.colorizeRGB(lores));
        }
        imeta.setLore((List)list);
        if (this.getGlow()) {
            imeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_ENCHANTS });
            imeta.addEnchant(Enchantment.LUCK, 1, false);
        }
        item.setItemMeta(imeta);
        if(((RSapka.config().getConfig().get(path + ".model"))) instanceof Integer){
            final net.minecraft.server.v1_16_R3.ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
            final NBTTagCompound nbtset = new NBTTagCompound();
            if (nmsitem.hasTag()) {
                nbtset.a(nmsitem.getTag());
            }
            nbtset.setInt("CustomModelData", RSapka.config().getConfig().getInt(path + ".model"));
            nbtset.setBoolean("RSapkaItem", true);
            nmsitem.setTag(nbtset);
            return CraftItemStack.asBukkitCopy(nmsitem);
        }
        return item;
    }
}