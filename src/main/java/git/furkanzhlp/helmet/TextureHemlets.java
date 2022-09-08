package git.furkanzhlp.helmet;

import git.furkanzhlp.helmet.listeners.HelmetListeners;
import git.furkanzhlp.helmet.utils.FileStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class TextureHemlets extends JavaPlugin {


    public static TextureHemlets instance;
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
        Objects.requireNonNull(this.getCommand("helmet")).setExecutor((CommandExecutor) new HelmetCommand());
        Bukkit.getPluginManager().registerEvents(new HelmetListeners(), this);


        //-----------------------------------------------------------------------------
        Bukkit.getConsoleSender().sendMessage("rSapka - Plugin started.");
        Bukkit.getConsoleSender().sendMessage("");
        //-----------------------------------------------------------------------------
    }
    public static FileStorage config() { return config; }
    public static TextureHemlets getInstance() {
        return instance;
    }
}
