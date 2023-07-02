// Usage: %graaljs_mspt%
// This script tested on Paper 1.20
// It supports RGB and smoothly changes color from green to yellow and to red

const Bukkit = bukkitClassLoader.loadClass("org.bukkit.Bukkit").static;
const MiniMessage = bukkitClassLoader.loadClass("net.kyori.adventure.text.minimessage.MiniMessage").static;
const LegacyComponentSerializer = bukkitClassLoader.loadClass("net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer").static;

function onPlaceholderRequest() {
    let tickTime = round(Bukkit.getAverageTickTime());
    let phase = tickTime / 50;

    if (phase > 1) phase = 1;
    else if (phase < 0) phase = 0;

    let component = MiniMessage.miniMessage().deserialize("<transition:#00ff00:#ffff00:#ff0000:" + phase + ">" + tickTime + "</transition>");
    return LegacyComponentSerializer.builder().hexColors().character('&').build().serialize(component);
}

function round(value) {
    return value | 0;
}
