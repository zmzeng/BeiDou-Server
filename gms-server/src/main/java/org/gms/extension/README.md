# BeiDou Extension Runtime

## Modules

| Module / package | Role |
|------------------|------|
| `extension-api` | Shared SPI: `ServerExtension`, `HostRuntime`, `ArtificialCharacters`, lifecycle events |
| `org.gms.extension.runtime` | `ExtensionLoader`, `BeiDouHostRuntime`, `HostHooks` |
| `org.gms.extension.event` | Host gameplay events (`CharacterMapEnteredEvent`, `CharacterChatEvent`, `PartyInviteEvent`, …) |
| `gms-server/plugins/*.jar` | Drop zone for external plugins |
| **solomapling-plugin** (external repo) | Artificial-player framework jar |

SoloMapling sources are **not** a Maven module of this repository.

## Host capabilities (no plugin package imports)

Engine code must not `import soloMapling.*`. Use:

| Capability | API |
|------------|-----|
| Is this character plugin-owned? | `HostHooks.isArtificial(chr)` / `ArtificialCharacters.isArtificial(id)` |
| Publish gameplay events | `HostHooks.publish(new CharacterMapEnteredEvent(...))` etc. |
| Pending trades to headless chars | `org.gms.server.trade.PendingTradeInvites` |
| Bot performance tier on `Character` | `org.gms.client.BotTier` |
| Headless session | `org.gms.client.BotClient` |
| Plugin lifecycle | `ServerExtension` + `ExtensionLoader` |

Plugins register a `CharacterClassifier` in `onLoad` so the host can treat their characters specially (timeout, map scripts, shops).

## Load order

1. Spring Boot starts
2. `ServerManager` builds `BeiDouHostRuntime` and `ExtensionLoader.load(plugins/)` → each extension `onLoad` (classifiers + command registration)
3. `Server.init()`
4. `notifyServerReady()` → `onServerReady` → optional SoloMapling world population
5. On shutdown: `onUnload` → `ArtificialCharacters.clear()`

## Config

```yaml
solomapling:
  plugins-enabled: true
  plugins-dir: plugins
  spawn-bots-on-startup: true
```

## Build

```bash
mvn -pl extension-api,gms-server -am install -DskipTests
# then build solomapling-plugin and copy jar into gms-server/plugins/
```
