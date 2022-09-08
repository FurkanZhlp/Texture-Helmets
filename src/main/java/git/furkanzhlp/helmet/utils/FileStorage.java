package com.minewebtr.rsapka.utils;

import com.minewebtr.rsapka.RSapka;
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
            RSapka.getInstance().saveResource(file.getName(),false);
        }
        this.config = (FileConfiguration) YamlConfiguration.loadConfiguration(this.file);
    }

    public FileStorage(final String name) {
        this(new File(RSapka.getInstance().getDataFolder(), name + ".yml"));
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

