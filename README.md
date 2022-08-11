# GraalJS Expansion

A powerful and high-performance alternative to [Javascript](https://github.com/PlaceholderAPI/Javascript-Expansion)
extension which runs scripts on the [GraalVM](https://www.graalvm.org/javascript) Polyglot engine. This extension allows
you to code almost any kind of placeholders you want in javascript.

# Installation

This extension uses the GraalVM Polyglot engine, which means that it will not work if the server is not running on
GraalVM JVM.

To make the server run on the GraalVM JVM you first need to [download it from here](https://www.graalvm.org/downloads)
and unzip the archive wherever you want. Please note that since version 22.0 they dropped support of Java 8, so if you
need it, you need to download version 21.3.

The next step is to edit your server startup file, replacing the usual word java with the path to java from your
unpacked GraalVM, like this:

```shell
"path-to-graalvm-folder\bin\java" -jar spigot-server.jar
```

In some cases GraalVM does not have a "JavaScript" language by default, so you need to install it as explained
[here](https://www.graalvm.org/22.2/reference-manual/graalvm-updater/#install-components-on-graalvm-community).

That's it, your server will now run on the GraalVM JVM. You can start using this extension and coding your placeholders!

# Where the scripts are stored

The scripts are loaded from the `plugins/PlaceholderAPI/graaljsscripts` folder. On first use, this extension does not
create any example scripts in this folder. So you can get them from the `scripts` folder in this repository. Just put
some scripts in this folder, run the command `/placeholderapi reload` and this scripts will be loaded!