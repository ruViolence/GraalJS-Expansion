package ru.violence.papi.expansion.graaljs.script;

import lombok.Getter;
import ru.violence.papi.expansion.graaljs.evaluator.EvaluatorFactory;
import ru.violence.papi.expansion.graaljs.evaluator.ParsedScript;

public class JavascriptPlaceholder {
    private final @Getter String identifier;
    private final @Getter ParsedScript parsedScript;

    public JavascriptPlaceholder(EvaluatorFactory evaluatorFactory, String identifier, String script) {
        this.identifier = identifier;
        this.parsedScript = evaluatorFactory.create().parse(script);
    }
}
