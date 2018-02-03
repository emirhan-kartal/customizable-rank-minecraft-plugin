package utils;

import java.io.*;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class Configuration {

    private FileConfiguration config;
    private File f;
    private String configName;

    public Configuration(JavaPlugin plugin, String name,boolean beCopy) {
        File file = new File(plugin.getDataFolder(), name);
        FileConfiguration c = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            try {
                    file.createNewFile();
                    if (beCopy) {
                        copy(plugin.getResource("ranklar.yml"),file);
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.f = file;
        this.config = c;
        this.configName = name;
        loadConfig();

    }
    public Configuration(JavaPlugin plugin) {
        File file = new File(plugin.getDataFolder(), "variables.db");
        FileConfiguration c = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }
        this.f = file;
        this.config = c;
    }


    public void saveConfig() {
        try {
            this.config.save(this.f);
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

    public void loadConfig() {
        try {
            this.config.load(this.f);
        } catch (IOException | InvalidConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public FileConfiguration getConfig() {
        return this.config;
    }
    private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte['?'];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception localException) {
        }
    }




}
