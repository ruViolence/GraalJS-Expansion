package ru.violence.papi.expansion.graaljs.evaluator;

public class DummyScriptEvaluator implements ScriptEvaluator {
    @Override
    public ParsedScript parse(String script) {
        return new DummyParsedScript();
    }
}
