package git.furkanzhlp.helmet.utils;

import git.furkanzhlp.helmet.TextureHemlets;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileStorage {
    private File file;
    private FileConfiguration config;

    public FileStorage(final File file) {
        this.file = file;
        this.file.getParentFile().mkdirs();
        if (!file.exists()) {
            TextureHemlets.getInstance().saveResource(file.getName(),false);
        }
        this.config = (FileConfiguration) YamlConfiguration.loadConfiguration(this.file);
    }

    public FileStorage(final String name) {
        this(new File(TextureHemlets.getInstance().getDataFolder(), name + ".yml"));
    }

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void save() {
        try {
            this.config.save(this.file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

