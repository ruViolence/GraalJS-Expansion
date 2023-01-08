// Usage: %graaljs_essentials_vanish%

const Bukkit = bukkitClassLoader.loadClass("org.bukkit.Bukkit").static;
const essentials = Bukkit.getPluginManager().getPlugin("Essentials");

function onPlaceholderRequest(player) {
    return essentials.getUser(player).isVanished() ? "Vanished" : "Not vanished";
}