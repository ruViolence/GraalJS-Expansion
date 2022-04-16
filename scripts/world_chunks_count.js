// Usage: %graaljs_world_chunks_count_<world_name>%

const Bukkit = bukkitClassLoader.loadClass("org.bukkit.Bukkit").static;

function onPlaceholderRequest(player, args) {
    if (args === undefined || args.length === 0) {
        return "Not provided a world name!";
    }

    let world = Bukkit.getWorld(args[0]);

    if (world === null) {
        return "World not found!";
    }

    return world.getLoadedChunks().length;
}