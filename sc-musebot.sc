(

~mb_control = (

    read_config: {
        arg self,
        skip_empty_lines=true,
        skip_multiple_spaces=true,
        path="/Users/kaneda/Developer/repos/mume/sc-musebot-template/config.txt";

        ~config=Dictionary.newFrom(
            FileReader.read(
                path,
                skip_empty_lines,
                skip_multiple_spaces
            ).flatten(1)
        ).keysValuesChange({
            | k,v |
            switch (k,
                "mc_listen_port",  v.asInt,
                "my_listen_port",  v.asInt,
                "output_channels", v.asInt,
                v)
        });
    },

    register_listeners: {
        | self |
        ~osc_listeners = Dictionary.new;
        ~osc_listeners["kill"] = OSCFunc({ | msg | self.shutdown; },
            "/agent/kill",
            NetAddr.new(~config["mc_hostname"]),
            ~config["my_listen_port"]
        );
        thisProcess.addOSCRecvFunc(~osc_listeners["kill"]);
    },

    heartbeat: {
        var beat = "beat"; // private var
        (
            start: {
                beat = Routine({
                    var wait = 1; // 1 second
                    loop {
                        NetAddr.new(
                            ~config["mc_hostname"],
                            ~config["mc_listen_port"]
                        ).sendMsg("/agent/alive", ~config["id"]);
                        //"/agent/alive ".post; ~config["id"].postln;
                        wait.yield;
                    }
                });
                beat.next;
                TempoClock.default.sched(0, beat);
                nil;
            },
            kill: {
                beat.stop;
                nil;
            }
        );
    }.valueEnvir,

    init:{
        | self |
        self.read_config;
        self.register_listeners;
        thisProcess.openUDPPort(~config["my_listen_port"]);
        //thisProcess.openPorts.postln;
        ~config["id"].postln;
        "Listening to port: ".post; ~config["my_listen_port"].postln;
        "Sending to port: ".post;   ~config["mc_listen_port"].postln;
    },

    run: {
        | self, userfunc |
        self.heartbeat.start;
        /* run user func here... */
        nil;
    },

    shutdown: {
        |self|
        "Shutting down.".postln;
        self.heartbeat.kill;
        thisProcess.removeOSCRecvFunc(~osc_listeners["kill"]);
        ~osc_listeners["kill"].free;
        0.exit; // quit sclang
    },

);

~mb_control.init;
~mb_control.run;
// ~mb_control.shutdown;
)
