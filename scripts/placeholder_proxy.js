// Usage: %graaljs_placeholder_proxy_player,name%

const String = Java.type("java.lang.String");
const PlaceholderAPI = Java.type("me.clip.placeholderapi.PlaceholderAPI");

function onPlaceholderRequest(player, args) {
    let placeholder = '%' + String.join("_", args) + '%';
    return PlaceholderAPI.setPlaceholders(player, placeholder);
}