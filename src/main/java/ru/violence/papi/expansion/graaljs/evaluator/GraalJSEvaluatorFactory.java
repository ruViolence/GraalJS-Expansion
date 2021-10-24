package ru.violence.papi.expansion.graaljs.evaluator;

public class GraalJSEvaluatorFactory {
    private final ClassLoader classLoader;

    public GraalJSEvaluatorFactory(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public GraalJSScriptEvaluator create() {
        return new GraalJSScriptEvaluator(this.classLoader);
    }
}
