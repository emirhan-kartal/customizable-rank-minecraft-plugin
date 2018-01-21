package Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Configuration {


    private FileConfiguration config;
    private File f;

    public Configuration(JavaPlugin plugin, String name) {

        File file = new File(plugin.getDataFolder(), name);
        FileConfiguration c = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {


            try {

                if (file.createNewFile()) ;
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


}
