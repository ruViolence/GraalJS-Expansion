package ru.violence.papi.expansion.graaljs.script;

import lombok.Getter;
import ru.violence.papi.expansion.graaljs.evaluator.GraalJSEvaluatorFactory;
import ru.violence.papi.expansion.graaljs.evaluator.GraalJSParsedScript;

public class JavascriptPlaceholder {
    private final @Getter String identifier;
    private final @Getter GraalJSParsedScript parsedScript;

    public JavascriptPlaceholder(GraalJSEvaluatorFactory evaluatorFactory, String identifier, String script) {
        this.identifier = identifier;
        this.parsedScript = evaluatorFactory.create().parse(script);
    }
}
