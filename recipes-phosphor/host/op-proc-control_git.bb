SUMMARY = "OpenPower procedure control"
DESCRIPTION = "Provides procedures that run against the host chipset"
PR = "r1"
PV = "1.0+git${SRCPV}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

S = "${WORKDIR}/git"

inherit autotools obmc-phosphor-utils pkgconfig pythonnative
inherit obmc-phosphor-systemd

#SRC_URI += "git://github.com/openbmc/openpower-proc-control"
#SRCREV = "2e249abd6089123eac9873d53ba6d45c79fdba94"
# TODO DELETE ME
SRC_URI += "git://github.com/openbmc/openpower-proc-control;nobranch=1"
SRCREV = "eda5dbeb890637ed3535a411de5b21ffcfbd9c4e"

DEPENDS += " \
        autoconf-archive-native \
        phosphor-logging \
        phosphor-dbus-interfaces \
        openpower-dbus-interfaces \
        libgpiod \
        phosphor-state-manager \
        "

# For libpdbg, provided by the pdbg package
DEPENDS += "pdbg"

TEMPLATE = "pcie-poweroff@.service"
INSTANCE_FORMAT = "pcie-poweroff@{}.service"
INSTANCES = "${@compose_list(d, 'INSTANCE_FORMAT', 'OBMC_CHASSIS_INSTANCES')}"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "${TEMPLATE} ${INSTANCES}"

# Install stop instructions service into host stop
STOPINST_HOST_STOP_TGT = "obmc-host-stop@{0}.target"
STOPINST_SVC = "op-stop-instructions@.service"
STOPINST_SVC_INST = "op-stop-instructions@{0}.service"
STOPINST_FMT = "../${STOPINST_SVC}:${STOPINST_HOST_STOP_TGT}.requires/${STOPINST_SVC_INST}"
SYSTEMD_SERVICE_${PN} += "${STOPINST_SVC}"
SYSTEMD_LINK_${PN} += "${@compose_list_zip(d, 'STOPINST_FMT', 'OBMC_HOST_INSTANCES')}"

# Install cfam reset service into force warm reboot target
CFAM_WARM_REBOOT_TGT = "obmc-host-force-warm-reboot@{0}.target"
CFAM_SVC = "op-cfam-reset.service"
CFAM_FMT = "../${CFAM_SVC}:${CFAM_WARM_REBOOT_TGT}.requires/${CFAM_SVC}"
SYSTEMD_SERVICE_${PN} += "${CFAM_SVC}"
SYSTEMD_LINK_${PN} += "${@compose_list_zip(d, 'CFAM_FMT', 'OBMC_HOST_INSTANCES')}"

SYSTEMD_SERVICE_${PN} +=  " \
                         xyz.openbmc_project.Control.Host.NMI.service \
                         "
