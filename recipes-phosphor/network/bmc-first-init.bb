SUMMARY = "Init BMC MAC address and UUID"
DESCRIPTION = "Setup BMC MAC address readed from VPD and UUID readed from /etc/machine-id"
PR = "r1"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${PHOSPHORBASE}/COPYING.apache-2.0;md5=34400b68072d710fecd0a2940a0d1658"

inherit obmc-phosphor-systemd

do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

RDEPENDS_${PN} = "bash"


S = "${WORKDIR}"
SRC_URI += "file://bmc-first-init.sh"

do_install() {
    install -d ${D}${sbindir}
    install -m 0755 ${S}/bmc-first-init.sh ${D}${sbindir}/
}

FMT = "bmc-first-init@{0}.service"
NIC_INSTANCES = "eth0"
SYSTEMD_SERVICE_${PN} = "bmc-first-init@.service"
SYSTEMD_SERVICE_${PN} += "${@compose_list(d, 'FMT', 'NIC_INSTANCES')}"
