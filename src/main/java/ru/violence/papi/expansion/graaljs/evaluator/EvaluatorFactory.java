package ru.violence.papi.expansion.graaljs.evaluator;

import ru.violence.papi.expansion.graaljs.util.Logger;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class EvaluatorFactory {
    private final ClassLoader classLoader;

    public EvaluatorFactory(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ScriptEvaluator create() {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("graal.js");
        if (engine != null && engine.getClass().getName().equals("com.oracle.truffle.js.scriptengine.GraalJSScriptEngine")) {
            return new GraalJSScriptEvaluator(this.classLoader);
        } else {
            Logger.severe("This server is not running on GraalVM!");
            return new DummyScriptEvaluator();
        }
    }
}
