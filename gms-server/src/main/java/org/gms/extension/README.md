# BeiDou Extension Runtime

## Modules

| Module / package | Role |
|------------------|------|
| `extension-api` | Shared SPI: `ServerExtension`, `HostRuntime`, `ArtificialCharacters`, `TradeParticipantHook` / `TradeParticipants`, `HostCharacterProvisioner`, lifecycle events |
| `org.gms.extension.runtime` | `ExtensionLoader`, `BeiDouHostRuntime`, `HostHooks` |
| `org.gms.extension.event` | Host gameplay events (`CharacterMapEnteredEvent`, `CharacterChatEvent`, `PartyInviteEvent`, `TradeInviteEvent`, …) |
| `gms-server/plugins/*.jar` | Drop zone for jars that implement `ServerExtension` |

Engine code must not import plugin packages. Host integrations go through the SPI and `HostHooks`.

## SPI capabilities

| Capability | API |
|------------|-----|
| Plugin lifecycle | `ServerExtension` (`onLoad` / `onServerReady` / `onUnload`) + `ExtensionLoader` |
| Host services | `HostRuntime`: config, events, commands, optional provisioner / item / drop APIs |
| Atomic account + character creation | `HostCharacterProvisioner` / `HostCharacterProvisionRequest` |
| Is this character plugin-owned? | `HostHooks.isArtificial(chr)` / `ArtificialCharacters.isArtificial(id)` |
| Trade participant rules | `TradeParticipants` / `HostHooks.trade*` + `TradeInviteEvent` |
| Publish gameplay events | `HostHooks.publish(...)` |
| Headless session / performance tier | `org.gms.client.BotClient`, `org.gms.client.BotTier` |

Plugins register a `CharacterClassifier` and optionally a `TradeParticipantHook` in `onLoad`.

## Host Hooks

`HostHooks` is the engine-facing facade over registered classifiers and trade hooks:

- skip timeout / map scripts / object placement for artificial characters
- refuse artificial characters as monster controllers (`MOVE_LIFE` requires a real client)
- consult trade hooks before applying native trade rules
- publish map / chat / party / trade events onto `HostEventBus`

## Load order

1. Spring Boot starts
2. `ServerManager` builds `BeiDouHostRuntime` and `ExtensionLoader.load(plugins/)` → each extension `onLoad`
3. `Server.init()`
4. `notifyServerReady()` → `onServerReady`
5. On shutdown: `onUnload` → `ArtificialCharacters.clear()` / `TradeParticipants.clear()`

## Config

Host loader keys in `application.yml`:

```yaml
extension:
  plugins-enabled: true
  plugins-dir: plugins
```

Plugin-specific keys also live in `application.yml` and are read through `HostConfig`. Example for **solomapling-plugin**:

```yaml
solomapling:
  spawn-bots-on-startup: true
  companions:
    enabled: false
  # language: zh-CN
  # population-config: ...
  llm:
    enabled: false
    api-key: ${DEEPSEEK_API_KEY:}
    model: deepseek-v4-flash
    max-tokens: 80
    timeout-ms: 10000
    history-turns: 8
    fallback-to-yaml: true
```

## Build

```bash
mvn -pl extension-api,gms-server -am install -DskipTests
```
