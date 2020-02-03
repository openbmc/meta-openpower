inherit meson

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI = "git://github.com/projects/openbmc/openpower-hw-diags"

PV = "0.1+git${SRCPV}"
SRCREV = "73ac368a7e435b27d1100037eb76ee65f6a85ce6"

S = "${WORKDIR}/git"

DEPENDS = "boost libgpiod pdbg phosphor-logging sdbusplus"
