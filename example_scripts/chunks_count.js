// Usage: %graaljs_chunks_count%

const Bukkit = bukkitClassLoader.loadClass("org.bukkit.Bukkit").static;

function onPlaceholderRequest() {
    let worlds = Bukkit.getWorlds();
    let count = 0;

    for (const world of worlds) {
        count += world.getLoadedChunks().length;
    }

    return count;
}