(
    var mb;
    var make_control = this.compileFile("./musebot-include.sc");
    ~mbctl = make_control.valueEnvir; // instantiate the backend

    //--------------------------------------------------------------------------
    // globals

    ~gain = 1.0;

    //--------------------------------------------------------------------------
    // synth defs

    SynthDef.new(\multi, {
        // Adapted from one of Eli Fieldsteel's awesome SC tutorials:
        // https://tinyurl.com/j7bjf3p
        var sig, amp, env;
        env = EnvGen.kr(
            Env.new([0,1,0],[10,10],[1,-1]),
            doneAction:2
        );
        amp = SinOsc.kr({ExpRand(0.2, 12)}!8).range(0,1);
        sig = SinOsc.ar({ExpRand(50,1200)}!8);
        sig = sig * env * amp;
        sig = Splay.ar(sig) * 0.5;
        Out.ar(0, sig);
    }).add;

    //--------------------------------------------------------------------------
    // define your MuseBot behaviour here

    mb = Routine({
        var delta = 5;
        loop {
            x = Synth.new(\multi);
            delta.yield;
        }
    });

    ~mbctl.init(mb); // mb is triggered internally with the `value` message

    //--------------------------------------------------------------------------
    // register OSC listeners here

    ~mbctl.register_listener("/agent/some_message", { |msg| msg.postln; });

    //--------------------------------------------------------------------------
    // all systems go!

    ~mbctl.run;
)
