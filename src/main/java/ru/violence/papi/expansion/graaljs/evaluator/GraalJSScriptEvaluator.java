package ru.violence.papi.expansion.graaljs.evaluator;

import lombok.SneakyThrows;
import org.graalvm.polyglot.Context;

public class GraalJSScriptEvaluator implements ScriptEvaluator {
    public static final String JS_LANG_ID = "js";
    private final Context context;
    private final ClassLoader classLoader;

    @SneakyThrows
    public GraalJSScriptEvaluator(ClassLoader classLoader) {
        this.classLoader = classLoader;
        ClassLoader oldCl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(Class.forName("org.graalvm.polyglot.Context").getClassLoader());
            this.context = Context.newBuilder(JS_LANG_ID)
                    .allowAllAccess(true)
                    .build();
            this.context.getBindings(JS_LANG_ID); // Check if JavaScript language is installed
        } finally {
            Thread.currentThread().setContextClassLoader(oldCl);
        }
    }

    @Override
    public GraalJSParsedScript parse(String script) {
        return new GraalJSParsedScript(this.context, this.classLoader, script);
    }
}
