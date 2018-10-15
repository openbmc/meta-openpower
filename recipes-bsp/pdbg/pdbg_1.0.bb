SUMMARY     = "PowerPC FSI Debugger"
DESCRIPTION = "pdbg allows JTAG-like debugging of the host POWER processors"
LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI += "git://github.com/open-power/pdbg.git;branch=master"
SRCREV = "2ba1e634c71404514889dd4be0a38b5a80faf760"

DEPENDS += "dtc-native"

S = "${WORKDIR}/git"

inherit autotools
