SUMMARY = "OpenPOWER Host Firmware Image"
DESCRIPTION = "Adds the OpenPOWER Host Firmware image to the BMC image"
PR = "r1"

S = "${WORKDIR}"

inherit allarch

HOST_FW_LICENSE ?= "Apache-2.0"
HOST_FW_LIC_FILES_CHKSUM ?= "file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
HOST_FW_SRC_URI ?= "file://pnor.xz.squashfs"

LICENSE = "${HOST_FW_LICENSE}"
LIC_FILES_CHKSUM = "${HOST_FW_LIC_FILES_CHKSUM}"
SRC_URI += "${HOST_FW_SRC_URI}"

DEPENDS += "squashfs-tools-native"

do_install() {
    rm -rf ${S}/squashfs-root

    install -d ${D}${datadir}
    unsquashfs -d ${D}${datadir}/host-fw/ pnor.xz.squashfs
}

FILES_${PN} += " \
    ${datadir}/host-fw/ATTR_PERM \
    ${datadir}/host-fw/ATTR_TMP \
    ${datadir}/host-fw/BMC_INV \
    ${datadir}/host-fw/BOOTKERNEL \
    ${datadir}/host-fw/CAPP \
    ${datadir}/host-fw/CVPD \
    ${datadir}/host-fw/DJVPD \
    ${datadir}/host-fw/FIRDATA \
    ${datadir}/host-fw/GUARD \
    ${datadir}/host-fw/HBB \
    ${datadir}/host-fw/HBBL \
    ${datadir}/host-fw/HBD \
    ${datadir}/host-fw/HBEL \
    ${datadir}/host-fw/HBI \
    ${datadir}/host-fw/HBRT \
    ${datadir}/host-fw/HB_VOLATILE \
    ${datadir}/host-fw/HCODE \
    ${datadir}/host-fw/HDAT \
    ${datadir}/host-fw/IMA_CATALOG \
    ${datadir}/host-fw/MEMD \
    ${datadir}/host-fw/MVPD \
    ${datadir}/host-fw/NVRAM \
    ${datadir}/host-fw/OCC \
    ${datadir}/host-fw/PAYLOAD \
    ${datadir}/host-fw/RINGOVD \
    ${datadir}/host-fw/SBE \
    ${datadir}/host-fw/SBKT \
    ${datadir}/host-fw/SECBOOT \
    ${datadir}/host-fw/UVISOR \
    ${datadir}/host-fw/VERSION \
    ${datadir}/host-fw/WOFDATA \
    ${datadir}/host-fw/part \
    ${datadir}/host-fw/pnor.toc \
    "

