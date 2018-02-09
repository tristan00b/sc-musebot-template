# Musebot Template for SuperCollider (sclang)

_**Note: Template tested under macOS 10.12.6 and SuperCollider 3.9.1**_

A template for creating Musebots in SuperCollider.

## Using the Template

0. Learn about working with Musebots by checking out the info links below
1. Copy the template directory into your `Musebots` directory and rename it what you wish to call your new Musebot (e.g. `tb_noisebot`). Prefixing the name with your initials to distinguish it from other people's Musebots is generally a good idea 😊
2. Open the `config.txt` file and change `id <name>` in the first line so that `<name>` matches the name you just gave to parent directory (e.g. `id tb_noisebot`)
3. Be sure to edit the path to `sclang` in the `run.command` file if you have it installed somewhere other than:

   ```
   /Applications/SuperCollider/SuperCollider.app/Contents/MacOS/sclang
   ```
4. Define your Musebot in `musebot.sc`

## Caveats

The SuperCollider server cannot boot when your input and output sample rates are set differently. When this happens you will see an error similar to the following:

```
Number of Devices: 3
   0 : "Built-in Microph"
   1 : "Built-in Input"
   2 : "Built-in Output"

ERROR: Input sample rate is 41000, but output is 48000. Mismatched sample rates are not supported. To disable input, set the number of input channels to 0.
could not initialize audio.
RESULT = 0
```

To fix this, open `Audio MIDI Setup.app` located in `/Applications/Utilities`, select `Show Audio Devices` from the `Window` menu, then cycle through the list of audio devices in the left pane of the `Audio Devices` window, setting each device to use the same sample rate (e.g. 44100.0 Hz).

## More Info

- [Musebots](http://musicalmetacreation.org/musebots/)
- [Musebot communication spec](https://docs.google.com/document/d/1UtdLYsOErzXKNFxrM7utHeFXgPNcC_w40lTtUxtCYO8)
- [MuMe Mailing list](https://groups.google.com/forum/#!forum/musicalmetacreation)
- [SuperCollider](https://supercollider.github.io/)
