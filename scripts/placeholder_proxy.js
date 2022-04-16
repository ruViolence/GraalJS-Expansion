// Usage: %graaljs_placeholder_proxy_player,name%

const String = Java.type("java.lang.String");
const PlaceholderAPI = bukkitClassLoader.loadClass("me.clip.placeholderapi.PlaceholderAPI").static;

function onPlaceholderRequest(player, args) {
    let placeholder = '%' + String.join("_", args) + '%';
    return PlaceholderAPI.setPlaceholders(player, placeholder);
}