# onPlayerJoinQuitEvent
A Minecraft [Spigot](https://www.spigotmc.org/) plugin that allow you to execute some actions (like commands) when a player change join or leave.  Support PlaceHolderAPI

## Requirements 
* [PlaceHolderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) (optional) if you want to use [PlaceHolderAPI placeholders](https://github.com/PlaceholderAPI/PlaceholderAPI/wiki/Placeholders) (see configuration). 
* [Spigot](https://getbukkit.org/download/spigot) or [Paper 1.16.4](https://papermc.io/downloads#Paper-1.16) (others version not tested)

## Configuration 
```yaml
onPlayerEvent:
  nameThisLikeYouWant:

    # if true event will trigger when player join
    onJoin: true

    # if true event will trigger when player quit
    onQuit: true

    requirements:

      # Apply this section to players listed here
      playerName:
        # If true, players will require one of the name listed here
        required: false
        playerNames:
          - test

      # Apply this section to players that has a specific permission
      permission:
        # If true, players will require one of the permission listed here
        required: false
        # true default will always grant the player the permission.
        # false default will not grant the player the permission.
        # op default will be true if the player is op.
        # notop is the opposite behavior of op.
        default: notop
        permissions:
          - test

    # Does the plugin should modify quit Message
    quitMessage:
      enabled: false
      # set "" to not send message when a player quit
      # Placeholders :
      # {playerName}
      # {playerDisplayName}
      # Or any PlaceHolderAPI placeholder
      message: §e[-]{playerDisplayName}


    # Does the plugin should modify quit Message
    joinMessage:
      enabled: false
      # set "" to not send message when a player quit
      # Placeholders :
      # {playerName}
      # {playerDisplayName}
      # Or any PlaceHolderAPI placeholder
      message: §e[+]{playerDisplayName}

    # Placeholders :
    # {playerName}
    # {playerDisplayName}
    # Or any PlaceHolderAPI placeholder
    commands_as_console:
      - say hello {playerName}

    # Works only on join event
    commands_as_player:
      - say hello guys

```

## Commands and permissions 
`onPlayerJoinQuitReload` reload configuration

## Download
https://github.com/Over2Craft-mc/onPlayerJoinQuit/raw/main/target/onWorldChange.jar