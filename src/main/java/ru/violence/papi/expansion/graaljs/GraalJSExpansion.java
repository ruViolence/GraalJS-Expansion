package ru.violence.papi.expansion.graaljs;

import me.clip.placeholderapi.expansion.Cacheable;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Relational;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.violence.papi.expansion.graaljs.evaluator.GraalJSEvaluatorFactory;
import ru.violence.papi.expansion.graaljs.script.JavascriptPlaceholder;
import ru.violence.papi.expansion.graaljs.script.ScriptRegistry;
import ru.violence.papi.expansion.graaljs.util.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GraalJSExpansion extends PlaceholderExpansion implements Relational, Configurable, Cacheable {
    private final String VERSION = getClass().getPackage().getImplementationVersion();
    private final ScriptRegistry scriptRegistry = new ScriptRegistry();
    private String argumentSeparator;

    public @NotNull String getIdentifier() {
        return "graaljs";
    }

    public @NotNull String getAuthor() {
        return "ruViolence";
    }

    public @NotNull String getVersion() {
        return this.VERSION;
    }

    @Override
    public boolean register() {
        this.argumentSeparator = getString("argument_split", ",");
        if (this.argumentSeparator.equals("_")) {
            this.argumentSeparator = ",";
        }

        Path scriptDirectoryPath = getPlaceholderAPI().getDataFolder().toPath().resolve("graaljsscripts");
        try {
            Files.createDirectories(scriptDirectoryPath);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        try {
            GraalJSEvaluatorFactory evaluatorFactory = new GraalJSEvaluatorFactory(this.getClass().getClassLoader());

            Iterator<Path> iterator = Files.walk(scriptDirectoryPath, 1)
                    .skip(1)
                    .filter(path -> {
                        if (!Files.isRegularFile(path)) return false;
                        String fileName = path.getFileName().toString();
                        return fileName.length() > 3 && fileName.endsWith(".js");
                    })
                    .iterator();

            while (iterator.hasNext()) {
                Path path = iterator.next();
                String fileName = path.getFileName().toString();
                String identifier = fileName.substring(0, fileName.length() - 3); // Strip ".js"
                try {
                    String script = new String(Files.readAllBytes(path), StandardCharsets.UTF_8).intern();
                    this.scriptRegistry.register(new JavascriptPlaceholder(evaluatorFactory, identifier, script));
                } catch (Exception e) {
                    Logger.severe("An error occurred while parsing a script \"" + fileName + "\"", e);
                }
            }

            Logger.info(this.scriptRegistry.getScriptMap().size() + " scripts loaded");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.register();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        int separatorIndex = identifier.indexOf('_');
        String scriptKey = separatorIndex != -1 ? identifier.substring(0, separatorIndex) : identifier;

        JavascriptPlaceholder placeholder = this.scriptRegistry.get(scriptKey);
        if (placeholder == null) return "";

        try {
            // Has arguments
            if (separatorIndex != -1) {
                String[] args = StringUtils.split(
                        identifier.substring(scriptKey.length() + 1), // identifier + '_'
                        this.argumentSeparator
                );

                return placeholder.getParsedScript().onPlaceholderRequest(player, args);
            }

            return placeholder.getParsedScript().onPlaceholderRequest(player);
        } catch (Exception e) {
            Logger.severe("An error occurred while executing a script \"" + getIdentifier() + "\"", e);
            return "Script error (see the console)";
        }
    }

    public String onPlaceholderRequest(Player one, Player two, String identifier) {
        int separatorIndex = identifier.indexOf('_');
        String scriptKey = separatorIndex != -1 ? identifier.substring(0, separatorIndex) : identifier;

        JavascriptPlaceholder placeholder = this.scriptRegistry.get(scriptKey);
        if (placeholder == null) return "";

        try {
            // Has arguments
            if (separatorIndex != -1) {
                String[] args = StringUtils.split(
                        identifier.substring(scriptKey.length() + 1), // identifier + '_'
                        this.argumentSeparator
                );

                return placeholder.getParsedScript().onRelPlaceholderRequest(one, two, args);
            }

            return placeholder.getParsedScript().onRelPlaceholderRequest(one, two);
        } catch (Exception e) {
            Logger.severe("An error occurred while executing a script \"" + getIdentifier() + "\"", e);
            return "Script error (see the console)";
        }
    }

    public void clear() {
        this.scriptRegistry.unregisterAll();
    }

    @Override
    public Map<String, Object> getDefaults() {
        Map<String, Object> defaults = new HashMap<>();
        defaults.put("argument_split", ",");
        return defaults;
    }
}
