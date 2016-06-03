# Musebot Template for Supercollider (sclang)

_**Note: This template is a work in progress and subject to breaking changes**_
_**Note: So far, the template assumes your are using OS X, and has only been tested under Yosemite (10.10.5) and SuperCollider 3.7.1**_

A template for creating MusebBots with Supercollider.

## Using the template

0. Familiarize yourself with how to work with MuseBots by checking out the links below.
1. Copy the template template directory into your `Musebots` directory. Give the directory the same name that you will be calling your new MuseBot (e.g. noisebot). You may want to prefix the name with your initials to distinguish it from other people's MuseBots (e.g. tb_noisebot).
2. Open the config.txt file and change the first line to `id <your bot's name>`. This should match the directory name (e.g. `id tb_noisebot`).
3. Define how your MuseBot will generate sound within the `mb = { ... }` function definition in `sc-musebot.sc`.
4. Register any OSC listeners for (_more description on this process to come_).

## Caveats

The Supercollider server cannot boot when your input and output sample rates are set differently. When this happens you will see an output such as the following:

```
Number of Devices: 3
   0 : "Built-in Microph"
   1 : "Built-in Input"
   2 : "Built-in Output"

input and output sample rates do not match. 44100 != 48000
could not initialize audio.
RESULT = 1
ERROR: server failed to start
For advice: [http://supercollider.sf.net/wiki/index.php/ERROR:_server_failed_to_start]
```

To rectify the situation, open the `Audio MIDI Setup.app` utility in `/Applications/Utilities`, select `Show Audio Devices` from the `Window` menu, then cycle through the list of audio devices in the left pane of the `Audio Devices` window, setting each device to use the same sample rate (e.g. 44100.0 Hz).

## More Info

- [Musebots](http://musicalmetacreation.org/musebots/)
- [MuseBot communication spec](https://docs.google.com/document/d/1UtdLYsOErzXKNFxrM7utHeFXgPNcC_w40lTtUxtCYO8)
- [Supercollider](https://supercollider.github.io/)
