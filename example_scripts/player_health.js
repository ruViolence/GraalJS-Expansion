// Usage: %graaljs_player_health%

function onPlaceholderRequest(player) {
    if (player === undefined) {
        return "Player is null!";
    }

    return player.getHealth();
}