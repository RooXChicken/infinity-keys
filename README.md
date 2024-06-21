# psychis-keys
## Explanation
If anybody is curious, or considering implementing a solution like this on their own server, please dm me (discord: rooxchicken). If you are a mod & plugin developer, though, feel free to use this source as a reference. (please credit me though :D )

## c2s

I implement c2s (client to server) communication via commands. While this may seem silly, it is a very easy and foolproof way of communicating with the server. All commands are first checked via the server (ex: /selectability can't open the gui unless the server says it's okay) to ensure that nobody can do illegal actions. Commands are sent via keybinds when needed. There are 3 command types for each ability.

1. srt - this type is sent when the key is first pressed. Initializes the action usually
2. rpt - this type is sent when the key is held. Used for charging actions usually
3. end - this type is sent when the key is released. Used for ending an action usually

## s2c

I implement s2c (server to client) communication via server messages (chat messages essentially). It uses a custom parsing format, which is detailed in the plugin source. In short, data is seperated by _, but can be any key really. The mod intercepts all chat messages received and checks for a certain string ("psyz91" in this case) and that marks the data as a 'packet' of sorts. The 'packets' **CANNOT** be sent by players, as their messages contain their username (ex: <RooXChicken> psyz91_XXX). Even if they could send server messages, all c2s messages are checked by the server to ensure they can do as they say, so it wouldn't be problematic or allow for exploits.

## More details

More details are in the source code. Points of interest are the OnChatMessage.java mixin, and the HandleData.java class.

I hope this was detailed enough. If you have questions then dm me or make an issue. Happy coding
