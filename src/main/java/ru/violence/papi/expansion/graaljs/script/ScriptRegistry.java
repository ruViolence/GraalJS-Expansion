package ru.violence.papi.expansion.graaljs.script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptRegistry {
    private final Map<String, JavascriptPlaceholder> scriptMap = new HashMap<>();

    public void register(JavascriptPlaceholder placeholder) {
        this.scriptMap.put(placeholder.getIdentifier(), placeholder);
    }

    public JavascriptPlaceholder get(String scriptKey) {
        return this.scriptMap.get(scriptKey);
    }

    public void unregister(JavascriptPlaceholder placeholder) {
        this.scriptMap.remove(placeholder.getIdentifier());
        placeholder.getParsedScript().terminate();
    }

    public void unregisterAll() {
        List<JavascriptPlaceholder> placeholders = new ArrayList<>(this.scriptMap.values());
        this.scriptMap.clear();
        for (JavascriptPlaceholder placeholder : placeholders) {
            placeholder.getParsedScript().terminate();
        }
    }

    public Map<String, JavascriptPlaceholder> getScriptMap() {
        return new HashMap<>(this.scriptMap);
    }
}
