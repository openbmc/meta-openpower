HOMEPAGE =  "https://github.ibm.com/phal/pdata/"

SUMMARY     = "POWER Host data management"
DESCRIPTION = "Device tree based POWER host data management"
PR = "r1"
PV = "1.0+git${SRCPV}"
LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

S = "${WORKDIR}/git"

SRC_URI = "git://git@github.ibm.com/phal/pdata;protocol=ssh"
SRCREV = "pdata-1.0"

DEPENDS = "pdbg"
DEPENDS-native = "gdbm-native dtc-native"

inherit autotools
