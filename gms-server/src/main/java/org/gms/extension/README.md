# BeiDou Extension Runtime + SoloMapling

Feature branch: `feat/solomapling-plugin-host`

## Modules

| Module / package | Role |
|------------------|------|
| `extension-api` | Shared SPI (`ServerExtension`, `HostRuntime`, …) |
| `org.gms.extension.runtime` | Host runtime + `ExtensionLoader` |
| `gms-server/plugins/*.jar` | Drop zone for external plugins |
| **solomapling-plugin** (external repo) | Full SoloMapling framework jar |

SoloMapling sources are **not** a Maven module of this repository. Build the plugin separately against `extension-api` + `gms-server` (provided), then copy the shaded jar into `plugins/`.

## Load order

1. Spring Boot starts
2. `ServerManager` builds `BeiDouHostRuntime` and `ExtensionLoader.load(plugins/)`
3. `Server.init()`
4. `notifyServerReady()` → plugin `onServerReady` → optional EnvironmentManager waves

## Config

```yaml
solomapling:
  plugins-enabled: true
  plugins-dir: plugins
  spawn-bots-on-startup: true
```
