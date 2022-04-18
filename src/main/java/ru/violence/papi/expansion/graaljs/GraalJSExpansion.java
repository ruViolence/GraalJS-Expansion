package ru.violence.papi.expansion.graaljs;

import me.clip.placeholderapi.expansion.Cacheable;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Relational;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.violence.papi.expansion.graaljs.evaluator.EvaluatorFactory;
import ru.violence.papi.expansion.graaljs.script.JavascriptPlaceholder;
import ru.violence.papi.expansion.graaljs.script.ScriptRegistry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

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
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            EvaluatorFactory evaluatorFactory = new EvaluatorFactory(this, this.getClass().getClassLoader());

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

                if (identifier.isEmpty()) {
                    log(Level.SEVERE, "Illegal script identifier: " + fileName);
                    continue;
                }

                try {
                    String script = new String(Files.readAllBytes(path), StandardCharsets.UTF_8).intern();
                    this.scriptRegistry.register(new JavascriptPlaceholder(evaluatorFactory, identifier, script));
                } catch (Exception e) {
                    log(Level.SEVERE, "An error occurred while parsing a script \"" + fileName + "\"", e);
                }
            }

            log(Level.INFO, this.scriptRegistry.getScriptMap().size() + " scripts loaded");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.register();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        JavascriptPlaceholder placeholder = getJSPlaceholder(identifier);
        if (placeholder == null) return "";

        try {
            String[] args = parseArgs(identifier, placeholder);

            if (args != null) {
                return placeholder.getParsedScript().onPlaceholderRequest(player, args);
            }

            return placeholder.getParsedScript().onPlaceholderRequest(player);
        } catch (Exception e) {
            log(Level.SEVERE, "An error occurred while executing a script \"" + getIdentifier() + "\"", e);
            return "Script error (see the console)";
        }
    }

    public String onPlaceholderRequest(Player one, Player two, String identifier) {
        JavascriptPlaceholder placeholder = getJSPlaceholder(identifier);
        if (placeholder == null) return "";

        try {
            String[] args = parseArgs(identifier, placeholder);

            if (args != null) {
                return placeholder.getParsedScript().onRelPlaceholderRequest(one, two, args);
            }

            return placeholder.getParsedScript().onRelPlaceholderRequest(one, two);
        } catch (Exception e) {
            log(Level.SEVERE, "An error occurred while executing a script \"" + getIdentifier() + "\"", e);
            return "Script error (see the console)";
        }
    }

    private @Nullable JavascriptPlaceholder getJSPlaceholder(String identifier) {
        JavascriptPlaceholder placeholder = this.scriptRegistry.get(identifier);
        if (placeholder != null) return placeholder;

        int separatorIndex = identifier.lastIndexOf('_');
        String scriptKey = separatorIndex != -1 ? identifier.substring(0, separatorIndex) : identifier;
        return this.scriptRegistry.get(scriptKey);
    }

    private @NotNull String @Nullable [] parseArgs(String identifier, JavascriptPlaceholder placeholder) {
        // Has arguments
        if (identifier.length() <= placeholder.getIdentifier().length()) return null;

        String rawArgs = identifier.substring(placeholder.getIdentifier().length() + 1); // Trim leading "identifier + '_'"
        return StringUtils.split(rawArgs, this.argumentSeparator);
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
