MultiTime
=================
![](https://tokei.rs/b1/github/Maddin-M/MultiTime?category=code)
![](https://img.shields.io/bstats/servers/10918)

Minecraft Server plugin for world specific time utilities

A simple Plugin to set, get, lock and unlock time per world. Because when using Multiverse, the default `/time` command affects all worlds.

### Usage:

`/time set {0-24000/day/noon/night/midnight} {world/all}`  
`/time get {world/all}`  
`/time lock {world/all}`  
`/time unlock {world/all}`

The world parameter is always optional and defaults to the world the player is currently in.

### Permissions:

`multitime.admin`
