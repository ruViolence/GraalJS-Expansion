package ru.violence.papi.expansion.graaljs.evaluator;

import lombok.SneakyThrows;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class GraalJSParsedScript implements ParsedScript {
    private final Context context;
    private final Value bindings;
    private final Value onPlaceholderRequest;
    private final Value onRelPlaceholderRequest;

    @SneakyThrows
    public GraalJSParsedScript(Context context, ClassLoader classLoader, String script) {
        ClassLoader oldCl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(Class.forName("org.graalvm.polyglot.Context").getClassLoader());

            this.context = context;
            this.bindings = context.getBindings(GraalJSScriptEvaluator.JS_LANG_ID);
            this.bindings.putMember("bukkitClassLoader", classLoader);
            context.eval(Source.create(GraalJSScriptEvaluator.JS_LANG_ID, script)); // First initialize
            Value onInitialize = this.bindings.getMember("onInitialize");
            if (onInitialize != null) onInitialize.executeVoid();
            this.onPlaceholderRequest = this.bindings.getMember("onPlaceholderRequest");
            this.onRelPlaceholderRequest = this.bindings.getMember("onRelPlaceholderRequest");
            if (this.onPlaceholderRequest == null && this.onRelPlaceholderRequest == null) {
                throw new RuntimeException("Script does not contain any onPlaceholder function");
            }
        } finally {
            Thread.currentThread().setContextClassLoader(oldCl);
        }
    }

    @Override
    public String onPlaceholderRequest(Object... params) {
        synchronized (this) {
            return this.onPlaceholderRequest.execute(params).toString();
        }
    }

    @Override
    public String onRelPlaceholderRequest(Object... params) {
        synchronized (this) {
            return this.onRelPlaceholderRequest.execute(params).toString();
        }
    }

    @Override
    public void terminate() {
        synchronized (this) {
            Value onTerminate = this.bindings.getMember("onTerminate");
            if (onTerminate != null) onTerminate.executeVoid();
            this.context.close(true);
        }
    }
}
