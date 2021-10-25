package ru.violence.papi.expansion.graaljs.evaluator;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class GraalJSParsedScript {
    private final Context context;
    private final Value bindings;
    private final Value onPlaceholderRequest;
    private final Value onRelPlaceholderRequest;

    public GraalJSParsedScript(Context context, String script) {
        this.context = context;
        this.bindings = context.getBindings(GraalJSScriptEvaluator.JS_LANG_ID);
        context.eval(Source.create(GraalJSScriptEvaluator.JS_LANG_ID, script)); // First initialize
        Value onInitialize = this.bindings.getMember("onInitialize");
        if (onInitialize != null) onInitialize.executeVoid();
        this.onPlaceholderRequest = this.bindings.getMember("onPlaceholderRequest");
        this.onRelPlaceholderRequest = this.bindings.getMember("onRelPlaceholderRequest");
    }

    public String onPlaceholderRequest(Object... params) {
        synchronized (this) {
            return this.onPlaceholderRequest.execute(params).toString();
        }
    }

    public String onRelPlaceholderRequest(Object... params) {
        synchronized (this) {
            return this.onRelPlaceholderRequest.execute(params).toString();
        }
    }

    public void terminate() {
        synchronized (this) {
            Value onTerminate = this.bindings.getMember("onTerminate");
            if (onTerminate != null) onTerminate.executeVoid();
            this.context.close(true);
        }
    }
}
