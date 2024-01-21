package xyz.wagyourtail.soundcategories;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.mob.MobCategory;

import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

public class VolumeManager {
    private static final Minecraft minecraft = Minecraft.INSTANCE;
    private static double recordVolume = 1.0f;
    private static double weatherVolume = 1.0f;
    private static double blockVolume = 1.0f;
    private static double hostileVolume = 1.0f;
    private static double neutralVolume = 1.0f;
    private static double playerVolume = 1.0f;
    private static double ambientVolume = 1.0f;


    public static double getMasterVolume() {
        return minecraft.options.soundVolume;
    }

    public static void setMasterVolume(double volume) {
        minecraft.options.soundVolume = (float) volume;
    }

    public static double getMusicVolume() {
        return minecraft.options.musicVolume;
    }

    public static void setMusicVolume(double volume) {
        minecraft.options.musicVolume = (float) volume;
    }

    public static double getRecordVolume() {
        return recordVolume;
    }

    public static void setRecordVolume(double volume) {
        recordVolume = volume;
    }

    public static double getWeatherVolume() {
        return weatherVolume;
    }

    public static void setWeatherVolume(double volume) {
        weatherVolume = volume;
    }

    public static double getBlockVolume() {
        return blockVolume;
    }

    public static void setBlockVolume(double volume) {
        blockVolume = volume;
    }

    public static double getHostileVolume() {
        return hostileVolume;
    }

    public static void setHostileVolume(double volume) {
        hostileVolume = volume;
    }

    public static double getNeutralVolume() {
        return neutralVolume;
    }

    public static void setNeutralVolume(double volume) {
        neutralVolume = volume;
    }

    public static double getPlayerVolume() {
        return playerVolume;
    }

    public static void setPlayerVolume(double volume) {
        playerVolume = volume;
    }

    public static double getAmbientVolume() {
        return ambientVolume;
    }

    public static void setAmbientVolume(double volume) {
        ambientVolume = volume;
    }

    public static void save(PrintWriter writer) {
        writer.println("soundcategories.recordVolume:" + recordVolume);
        writer.println("soundcategories.weatherVolume:" + weatherVolume);
        writer.println("soundcategories.blockVolume:" + blockVolume);
        writer.println("soundcategories.hostileVolume:" + hostileVolume);
        writer.println("soundcategories.neutralVolume:" + neutralVolume);
        writer.println("soundcategories.playerVolume:" + playerVolume);
        writer.println("soundcategories.ambientVolume:" + ambientVolume);
    }

    public static void load(String[] line) {
        switch (line[0]) {
            case "soundcategories.recordVolume":
                recordVolume = Double.parseDouble(line[1]);
                break;
            case "soundcategories.weatherVolume":
                weatherVolume = Double.parseDouble(line[1]);
                break;
            case "soundcategories.blockVolume":
                blockVolume = Double.parseDouble(line[1]);
                break;
            case "soundcategories.hostileVolume":
                hostileVolume = Double.parseDouble(line[1]);
                break;
            case "soundcategories.neutralVolume":
                neutralVolume = Double.parseDouble(line[1]);
                break;
            case "soundcategories.playerVolume":
                playerVolume = Double.parseDouble(line[1]);
                break;
            case "soundcategories.ambientVolume":
                ambientVolume = Double.parseDouble(line[1]);
                break;
        }
    }

    public static float getVolume(String sound) {
        double vol = getMasterVolume();
        if (sound.startsWith("ambient.weather")) {
            return (float) (vol * getWeatherVolume());
        }
        if (sound.startsWith("ambient")) {
            return (float) (vol * getAmbientVolume());
        }
        if (sound.startsWith("damage")) {
            return (float) (vol * getPlayerVolume());
        }
        if (sound.startsWith("fire")) {
            return (float) (vol * getBlockVolume());
        }
        if (sound.startsWith("liquid")) {
            return (float) (vol * getBlockVolume());
        }
        if (sound.startsWith("mob")) {
            for (Map.Entry<String, Class<? extends Entity>> entry : ((Map<String, Class<? extends Entity>>) Entities.KEY_TO_TYPE).entrySet()) {
                if (sound.startsWith("mob." + entry.getKey().toLowerCase(Locale.ROOT))) {
                    if (MobCategory.MONSTER.getType().isAssignableFrom(entry.getValue())) {
                        return (float) (vol * getHostileVolume());
                    } else {
                        return (float) (vol * getNeutralVolume());
                    }
                }
            }
        }
        if (sound.startsWith("note")) {
            return (float) (vol * getRecordVolume());
        }
        if (sound.startsWith("portal")) {
            return (float) (vol * getBlockVolume());
        }
        if (sound.startsWith("random")) {
            if (!sound.equals("random.click")) {
                return (float) (vol * getBlockVolume());
            } else {
                return (float) vol;
            }
        }
        if (sound.startsWith(("step"))) {
            return (float) (vol * getBlockVolume());
        }
        if (sound.startsWith("tile")) {
            return (float) (vol * getBlockVolume());
        }
        if (sound.startsWith("vehicle")) {
            return (float) (vol * getAmbientVolume());
        }
        System.err.println("Unknown sound: " + sound);
        return (float) vol;
    }

    public enum SoundCategory {
        MASTER("master", VolumeManager::setMasterVolume, VolumeManager::getMasterVolume),
        MUSIC("music", VolumeManager::setMusicVolume, VolumeManager::getMusicVolume),
        RECORD("record", VolumeManager::setRecordVolume, VolumeManager::getRecordVolume),
        WEATHER("weather", VolumeManager::setWeatherVolume, VolumeManager::getWeatherVolume),
        BLOCK("block", VolumeManager::setBlockVolume, VolumeManager::getBlockVolume),
        HOSTILE("hostile", VolumeManager::setHostileVolume, VolumeManager::getHostileVolume),
        NEUTRAL("neutral", VolumeManager::setNeutralVolume, VolumeManager::getNeutralVolume),
        PLAYER("player", VolumeManager::setPlayerVolume, VolumeManager::getPlayerVolume),
        AMBIENT("ambient", VolumeManager::setAmbientVolume, VolumeManager::getAmbientVolume);
        

        private final String name;
        private final DoubleConsumer setter;
        private final DoubleSupplier getter;

        SoundCategory(String name, DoubleConsumer setter, DoubleSupplier getter) {
            this.name = name;
            this.setter = setter;
            this.getter = getter;
        }

        public String getKey() {
            return "soundcategories.category." + name;
        }

        public double getVolume() {
            return getter.getAsDouble();
        }

        public void setVolume(double volume) {
            setter.accept(volume);
        }

    }

}
