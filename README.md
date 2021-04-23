![](https://raw.githubusercontent.com/Maddin-M/MultiTime/master/icon.png)
MultiTime ![](https://tokei.rs/b1/github/Maddin-M/MultiTime?category=code) ![](https://img.shields.io/bstats/servers/10918) ![](https://img.shields.io/spiget/downloads/90642)
=================

Minecraft Server plugin for world specific time utilities

A simple Plugin to set, get, lock and unlock time per world. Because when using [Multiverse](https://github.com/Multiverse), the default `/time` command affects all worlds.

Download
---

[Download latest release here](https://www.spigotmc.org/resources/multitime.90642/)

Usage
---

`/time set {ticks/day/noon/night/midnight} {world/all}`  
`/time add {ticks} {world/all}`  
`/time get {world/all}`  
`/time lock {world/all}`  
`/time unlock {world/all}`

- The world parameter is always optional and defaults to the world the player is currently in.
- You can subtract time by using `/time add` with a negative number
- Locked worlds will still be affected by `/time set` or `/time add`

Permissions
---

`multitime.admin` for all commands (default for ops)

Other
---

- Tested on [PaperMC](https://papermc.io/downloads)
- Compatible with [Java 11+](https://adoptopenjdk.net/)
- [bStats](https://bstats.org/plugin/bukkit/MultiTime/10918)
