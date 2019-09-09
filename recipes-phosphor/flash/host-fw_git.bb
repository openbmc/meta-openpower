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
SRC_URI = "${HOST_FW_SRC_URI}"

DEPENDS = "squashfs-tools-native"

do_install() {
    rm -rf ${S}/squashfs-root/
    unsquashfs ${S}/pnor.xz.squashfs

    install -d ${D}${datadir}/${PN}
    install -m 0644 ${S}/squashfs-root/* ${D}${datadir}/${PN}
}
