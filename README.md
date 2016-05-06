# Musebot Template for Supercollider (sclang)

A template for creating Musebots with Supercollider.

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

To rectify this situation, open the `Audio MIDI Setup.app` utility in `/Applications/Utilities`, select `Show Audio Devices` from the `Window` menu, then cycle through the list of audio devices in the left pane of the `Audio Devices` window, setting each device to use the same sample rate (e.g. 44100.0 Hz).

## More Info

- [Musebots](http://musicalmetacreation.org/musebots/)
- [Supercollider](https://supercollider.github.io/)
