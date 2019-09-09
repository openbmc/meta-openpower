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
    # Unpack the host fw files to build/<tmp_dir>/
    install -d ${S}/build
    HOST_FW_FILES_DIR=$(mktemp -d -p ${S}/build ${BPN}.XXXXXX)/squashfs-root
    unsquashfs -d ${HOST_FW_FILES_DIR} ${S}/pnor.xz.squashfs

    install -d ${D}${datadir}/${BPN}
    install -m 0440 ${HOST_FW_FILES_DIR}/* ${D}${datadir}/${BPN}
}
