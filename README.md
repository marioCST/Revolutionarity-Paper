Revolutionarity
-
---

Minecraft/Paper: 1.18.1

Java: 17

**Revolutionarity is an AntiCheat targeting Geyser server compatibility**

**Bedrock Checks:**

- EditionFaker (doesn't work because Geyser throws LinkageError while calling getDeviceOs)
- ToolBox (doesn't work because Geyser throws LinkageError while calling getDeviceOs)

**Checks:**

- FlySpeed
- HighJump
- IllegalBlockInteraction
- Reach (might false flag, change max range in the config)
- Speed

**Java Checks:**

- AirJump
- OnGround spoof


AirJump and OnGround spoof need different checks due to Paper not correctly setting the onGround boolean for BE players.
Not really fixable due to Geyser not setting their onGround boolean correctly.