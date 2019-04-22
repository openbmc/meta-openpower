SUMMARY = "Memory Preserving IPL"
DESCRIPTION = "Service to start memory preserving IPL"
PR = "r1"
LICENSE = "Apache-2.0"

inherit obmc-phosphor-systemd

FILESEXTRAPATHS_prepend := "${THISDIR}/op-host-control:"

PROVIDES += 'virtual/obmc-host-ctl'
RPROVIDES_${PN} += 'virtual-obmc-host-ctl'

S = "${WORKDIR}"

TMPL = "host-mpipl@.service"
INSTFMT = "host-mpipl@{0}.service"
TGTFMT = "obmc-mpipl@{0}.target"
FMT = "../${TMPL}:${TGTFMT}.requires/${INSTFMT}"

SYSTEMD_SERVICE_${PN} += "${TMPL}"
SYSTEMD_LINK_${PN} += "${@compose_list(d, 'FMT', 'OBMC_CHASSIS_INSTANCES')}"
