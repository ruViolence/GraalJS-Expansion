// Usage: %graaljs_example%

function onInitialize() {
    print("Initialized");
}

function onPlaceholderRequest(player, args) {
    return "Some example placeholder";
}

function onRelPlaceholderRequest(playerOne, playerTwo, args) {
    return "Some relational example placeholder";
}

function onTerminate() {
    print("Terminated");
}