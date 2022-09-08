package com.minewebtr.rsapka;

import com.minewebtr.rsapka.listeners.SapkaListeners;
import com.minewebtr.rsapka.utils.FileStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class RSapka extends JavaPlugin {


    public static RSapka instance;
    public static FileStorage config;

    @Override
    public void onEnable() {
        this.initalize();
    }

    public void initalize(){
        //-----------------------------------------------------------------------------
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("rSapka - Plugin starting...");
        //-----------------------------------------------------------------------------

        instance = this;
        config = new FileStorage("config");
        Objects.requireNonNull(this.getCommand("rsapka")).setExecutor((CommandExecutor) new RSapkaCommand());
        Bukkit.getPluginManager().registerEvents(new SapkaListeners(), this);


        //-----------------------------------------------------------------------------
        Bukkit.getConsoleSender().sendMessage("rSapka - Plugin started.");
        Bukkit.getConsoleSender().sendMessage("");
        //-----------------------------------------------------------------------------
    }
    public static FileStorage config() { return config; }
    public static RSapka getInstance() {
        return instance;
    }
}
