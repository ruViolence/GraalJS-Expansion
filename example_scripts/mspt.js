// Usage: %graaljs_mspt%
// This script only works on Bukkit 1.12.2

const FORMAT = new (bukkitClassLoader.loadClass("java.text.DecimalFormat"))("###.00");
const MC_SERVER = bukkitClassLoader.loadClass("net.minecraft.server.v1_12_R1.MinecraftServer").static.getServer();
const TICK_TIME_ARRAY = MC_SERVER.h;

function onPlaceholderRequest() {
    return color(round(toMilliseconds(average(TICK_TIME_ARRAY))));
}

function color(mspt) {
    if (mspt <= 25.0) {
        return "§a" + mspt;
    } else if (mspt <= 40) {
        return "§e" + mspt;
    } else {
        return "§c" + mspt;
    }
}

function average(arr) {
    let i = 0;
    for (const l of arr) {
        i += l;
    }
    return i / arr.length;
}

function toMilliseconds(time) {
    return time * 1.0E-6;
}

function round(value) {
    const formatted = FORMAT.format(value);
    if (formatted.startsWith(".")) {
        return "0" + formatted;
    }
    return formatted;
}