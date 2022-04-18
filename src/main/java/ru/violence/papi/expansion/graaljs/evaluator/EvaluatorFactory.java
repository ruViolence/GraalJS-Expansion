package ru.violence.papi.expansion.graaljs.evaluator;

import org.graalvm.polyglot.Context;
import ru.violence.papi.expansion.graaljs.GraalJSExpansion;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.logging.Level;

public class EvaluatorFactory {
    private final GraalJSExpansion expansion;
    private final ClassLoader classLoader;

    public EvaluatorFactory(GraalJSExpansion expansion, ClassLoader classLoader) {
        this.expansion = expansion;
        this.classLoader = classLoader;
    }

    public ScriptEvaluator create() {
        ClassLoader oldCl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(Context.class.getClassLoader());

            ScriptEngine engine = new ScriptEngineManager().getEngineByName("graal.js");
            if (engine != null && engine.getClass().getName().equals("com.oracle.truffle.js.scriptengine.GraalJSScriptEngine")) {
                return new GraalJSScriptEvaluator(this.classLoader);
            } else {
                this.expansion.log(Level.SEVERE, "This server is not running on GraalVM!");
                return new DummyScriptEvaluator();
            }
        } finally {
            Thread.currentThread().setContextClassLoader(oldCl);
        }
    }
}
