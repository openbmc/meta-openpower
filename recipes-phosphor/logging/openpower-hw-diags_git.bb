inherit meson systemd

SUMMARY = "ATTN and HwDiags Support"
DESCRIPTION = "Attention Handler and Hardware Diagnostics"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI = "git://github.com/projects/openbmc/openpower-hw-diags"

SYSTEMD_SERVICE_${PN} = "attn_handler.service"
PV = "0.1+git${SRCPV}"
SRCREV = "73ac368a7e435b27d1100037eb76ee65f6a85ce6"

SRC_URI += "file://attn_handler.service"
S = "${WORKDIR}/git"

DEPENDS = "boost libgpiod pdbg phosphor-logging sdbusplus sdbus++-native"
UNITDIR = "${systemd_system_unitdir}"
do_install_append() {
    install -d ${D}${UNITDIR}
    install -m 0644 ${WORKDIR}/attn_handler.service ${D}${UNITDIR}/
}
FILES_${PN} += "${UNITDIR}/attn_handler.service"
