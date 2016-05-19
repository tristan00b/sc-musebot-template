#!/usr/bin/env bash

################################################################################
#
# sclang usage:
#
# Usage:
#    sclang [options] [file..] [-]
#
# Options:
# -v                             Print supercollider version and exit
# -d <path>                      Set runtime directory
# -D                             Enter daemon mode (no input)
# -g <memory-growth>[km]         Set heap growth (default 256k)
# -h                             Display this message and exit
# -l <path>                      Set library configuration file
# -m <memory-space>[km]          Set initial heap size (default 2m)
# -r                             Call Main.run on startup
# -s                             Call Main.stop on shutdown
# -u <network-port-number>       Set UDP listening port (default 57120)
# -i <ide-name>                  Specify IDE name (for enabling IDE-specific class code, default "none")
# -a                             Standalone mode
#
################################################################################

cd "`dirname "$0"`"

SC_BIN="/Applications/SuperCollider/SuperCollider.app/Contents/MacOS/sclang"
MB_FILE="sc-musebot.sc"


${SC_BIN} -d . ${MB_FILE}
