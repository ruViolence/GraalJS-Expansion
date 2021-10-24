package ru.violence.papi.expansion.graaljs.evaluator;

import org.graalvm.polyglot.Context;

public class GraalJSScriptEvaluator {
    public static final String JS_LANG_ID = "js";
    private final Context context;

    public GraalJSScriptEvaluator(ClassLoader classLoader) {
        this.context = Context.newBuilder(JS_LANG_ID)
                .hostClassLoader(classLoader)
                .allowAllAccess(true)
                .build();
    }

    public GraalJSParsedScript parse(String script) {
        return new GraalJSParsedScript(this.context, script);
    }
}
