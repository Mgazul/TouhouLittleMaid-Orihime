## [TouhouLittleMaid](https://github.com/TartaricAcid/TouhouLittleMaid) unofficial Fabric port.
Available on [Modrinth](https://modrinth.com/mod/touhoulittlemaid-orihime) and [CurseForge](https://curseforge.com/minecraft/mc-mods/touhoulittlemaid-orihime)
You can get the detail in TLM's [WIKI](https://tlmwiki.cfpa.team/).<br>

**Note:**
- **This mod requires [Forge Config API Port](https://modrinth.com/mod/forge-config-api-port)**
- **If you want to change some settings in-game, you should install [Cloth Config API](https://modrinth.com/mod/cloth-config)**
- **This mod is still experimental, perhaps there exist some bugs.**

**<br>
If you want to extend this mod, you can add an entry point of type "little_maid_extension" in your fabric.mod.json:**

```
  "entrypoints": {
    "little_maid_extension": [
      "com.example.yourmod.YourMaid"
    ]
  },
```

**and implement** ```ILittleMaid```
