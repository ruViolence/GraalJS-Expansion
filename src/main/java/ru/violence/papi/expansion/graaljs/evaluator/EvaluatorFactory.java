package ru.violence.papi.expansion.graaljs.evaluator;

import ru.violence.papi.expansion.graaljs.GraalJSExpansion;

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
            Thread.currentThread().setContextClassLoader(Class.forName("org.graalvm.polyglot.Context").getClassLoader());

            return new GraalJSScriptEvaluator(this.classLoader);
        } catch (ClassNotFoundException e) {
            this.expansion.log(Level.SEVERE, "This server is not running on GraalVM!", e);
            return new DummyScriptEvaluator();
        } catch (IllegalArgumentException e) {
            this.expansion.log(Level.SEVERE, "JavaScript language is not installed in GraalVM!", e);
            return new DummyScriptEvaluator();
        } finally {
            Thread.currentThread().setContextClassLoader(oldCl);
        }
    }
}
