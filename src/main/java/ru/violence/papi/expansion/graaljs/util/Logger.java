package ru.violence.papi.expansion.graaljs.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.logging.Level;

@UtilityClass
public class Logger {
    private final String LOG_PREFIX = "[PlaceholderAPI] [GraalJS-Expansion] ";

    public void info(String msg) {
        log(Level.INFO, msg);
    }

    public void severe(String msg) {
        log(Level.SEVERE, msg);
    }

    public void severe(String msg, Throwable thrown) {
        log(Level.SEVERE, msg, thrown);
    }

    public void log(Level level, String msg) {
        Bukkit.getLogger().log(level, LOG_PREFIX + msg);
    }

    public void log(Level level, String msg, Throwable thrown) {
        Bukkit.getLogger().log(level, LOG_PREFIX + msg, thrown);
    }
}
