inherit meson

SUMMARY = "Hardware Error Isolator Library"
DESCRIPTION = "Hardware Error Isolator Library (libhei)"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI = "git://github.com/openbmc/openpower-libhei"

PV = "0.1+git${SRCPV}"
SRCREV = "d39aa5771cd42d552e5e435c0532e990e0cc7b80"

S = "${WORKDIR}/git"
