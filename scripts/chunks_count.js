// Usage: %graaljs_chunks_count%

const Bukkit = Java.type("org.bukkit.Bukkit");

function onPlaceholderRequest() {
    let worlds = Bukkit.getWorlds();
    let count = 0;
    
    for (const world of worlds) {
        count += world.getLoadedChunks().length;
    }
    
    return count;
}