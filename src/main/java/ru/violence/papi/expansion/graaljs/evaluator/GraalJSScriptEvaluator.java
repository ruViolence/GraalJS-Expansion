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
            Thread.currentThread().setContextClassLoader(Context.class.getClassLoader());
            this.context = Context.newBuilder(JS_LANG_ID)
                    .allowAllAccess(true)
                    .build();
        } finally {
            Thread.currentThread().setContextClassLoader(oldCl);
        }
    }

    @Override
    public GraalJSParsedScript parse(String script) {
        return new GraalJSParsedScript(this.context, this.classLoader, script);
    }
}
