// Usage: %graaljs_example%
//    or: %graaljs_example#arg1,arg2%

function onInitialize() {
    print("Initialized");
}

function onPlaceholderRequest(player, args) {
    return "Some example placeholder with provided player " + player + " and arguments " + args;
}

function onRelPlaceholderRequest(playerOne, playerTwo, args) {
    return "Some relational example placeholder with provided player one " + player + ", player two " + playerTwo + " and arguments " + args;
}

function onTerminate() {
    print("Terminated");
}