package me.kristopher.realcraft.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigManager {
	private File file;
    private YamlConfiguration configuration;
    private boolean isCreated;

    public ConfigManager(Plugin plugin, String name) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        this.file = new File(plugin.getDataFolder(), name + ".yml");
        if (this.file.exists()) {
            this.isCreated = true;
            this.configuration = YamlConfiguration.loadConfiguration(this.file);
        }

    }

    public YamlConfiguration getConfiguration() {
        return !this.check() ? null : this.configuration;
    }

    public void save() {
        if (this.check()) {
            try {
                this.configuration.save(this.file);
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }

    }

    public void create() {
        if (!this.file.exists() || !this.isCreated || this.configuration == null) {
            this.isCreated = true;

            try {
                this.file.createNewFile();
            } catch (IOException var2) {
                var2.printStackTrace();
            }

            this.configuration = YamlConfiguration.loadConfiguration(this.file);
        }

    }

    public void set(String path, Object obj) {
        if (this.check()) {
            this.configuration.set(path, obj);
        }

    }

    private boolean check() {
        if (!this.isCreated) {
            try {
                throw new FileNotFoundException("File: " + this.file.getName() + " not found! Please, created file!");
            } catch (FileNotFoundException var2) {
                var2.printStackTrace();
                return false;
            }
        } else {
            return true;
        }
    }

    public static File createFolder(Plugin plugin, String path) {
        File file = new File(plugin.getDataFolder(), path);
        if (!file.exists()) {
            file.mkdir();
        }

        return file;
    }
}
