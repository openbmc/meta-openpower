SUMMARY = "OpenPOWER Host Firmware Build"
HOMEPAGE = "http://tbd/host-firmware"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

SRC_URI = "http://tbd/host-firmware/witherspoon.pnor.squashfs.tar"
SRC_URI[md5sum] = "8922a9970edb2bc6dd43d0a9355e0d34"
SRC_URI[sha256sum] = "e44ac6f16480be845436b98244869e5c3a067d6eed982b17c8aefcf684b3c6fa"

inherit allarch
inherit native

DEPENDS += "squashfs-tools-native"

S = "${WORKDIR}"

do_install() {
    rm -rf ${S}/squashfs-root
    unsquashfs ${S}/pnor.xz.squashfs

    install -d ${D}${datadir}/pnor/
    install -m 664 MANIFEST ${D}${datadir}/pnor/
    install -m 664 MANIFEST.sig ${D}${datadir}/pnor/
    install -m 664 publickey ${D}${datadir}/pnor/
    install -m 664 publickey.sig ${D}${datadir}/pnor/

    install -d ${D}${datadir}/pnor/squashfs-root/
    install -m 664 ${S}/squashfs-root/* ${D}${datadir}/pnor/squashfs-root/
}

