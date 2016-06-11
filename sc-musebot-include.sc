(
var read_config = {
    var skip_empty_lines=true,
        skip_multiple_spaces=true,
        path="./config.txt";
    Dictionary.newFrom(
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
};

var make_heartbeat = {
    var beat; // private var
    (
        start: {
            beat = Routine({
                var wait = 1; // 1 second
                loop {
                    NetAddr.new(
                        ~config["mc_hostname"],
                        ~config["mc_listen_port"]
                    ).sendMsg("/agent/alive", ~config["id"]);
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
};

var boot_server = {
    var addr = NetAddr(~config["mc_hostname"], ~config["server_port"]);
    Server.default = Server.new(~config["id"], addr);
};

(
    register_listener: {
        | self, osc_path, func |

        if (~listeners.isNil,
           { ~listeners = Dictionary.new; }
        );
        ~listeners[osc_path] = OSCFunc(
            func, osc_path,
            NetAddr.new(~config["mc_hostname"]),
            ~config["my_listen_port"]
        );
        thisProcess.addOSCRecvFunc(~listeners[osc_path])
    },

    gain:  {
        | self, gain |
        Server.default.volume = gain.ampdb;
    },

    init: {
        | self, userfunc |
        "initializing musebot...".postln;
        ~config = read_config.valueEnvir;
        ~config["server_port"] = ~config["my_listen_port"] + 50000;



        thisProcess.openUDPPort(~config["my_listen_port"]);
        self.heartbeat = make_heartbeat.value;
        self.register_listener("/agent/off",  { | msg | self.shutdown });
        self.register_listener("/agent/quit", { | msg | self.shutdown });
        self.register_listener("/agent/kill", { | msg | self.shutdown });
        self.register_listener("/agent/gain", { | msg | self.gain(msg[1]); msg.postln; });
        // OSCFunc.trace(true); // Turn posting on*

        self.userfunc = userfunc;
        boot_server.valueEnvir;

        "Hello from ".post; ~config["id"].post; "!".postln;
        "- Listening to port ".post; ~config["my_listen_port"].postln;
        "- Sending to port ".post;   ~config["mc_listen_port"].postln;
    },

    run: {
        | self |
        self.heartbeat.start;
        Server.default.waitForBoot(
            onComplete: {
                Server.default.notify;
                Server.default.initTree;
                self.userfunc.value;
            },
            onFailure: { self.shutdown; false; }
        );
    },

    shutdown: {
        |self|
        "quitting musebot...".postln;
        self.heartbeat.kill;
        /*~listeners.do {
            | listener |
            thisProcess.removeOSCRecvFunc(listener);
            listener.free;
        };*/
        0.exit; // quit sclang
    }
);
)
