SUMMARY     = "eCMD plugin with pdbg backend"
DESCRIPTION = "The glue code necessary for pdbg to be used as an eCMD plugin"
PR = "r1"
PV = "1.0+git${SRCPV}"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=34400b68072d710fecd0a2940a0d1658"

inherit meson
inherit pkgconfig

SRC_URI = "git://github.com/open-power/ecmd-pdbg.git"
SRCREV = "350ff4cf028096ef4639e9984d77ef461b2d39be"

S = "${WORKDIR}/git"

DEPENDS = "pdbg zlib libyaml"

FILES_${PN} += "${prefix}/help"


