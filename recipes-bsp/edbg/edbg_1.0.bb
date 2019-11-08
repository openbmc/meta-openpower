SUMMARY     = "eCMD plugin with pdbg backend"
DESCRIPTION = "The glue code necessary for pdbg to be used as an eCMD plugin"
PR = "r1"
PV = "1.0+git${SRCPV}"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=34400b68072d710fecd0a2940a0d1658"

inherit meson
inherit pkgconfig

SRC_URI += "git://github.com/open-power/ecmd-pdbg.git"
SRCREV = "87372d573c969391a1cededd39552d2659850a5b"

S = "${WORKDIR}/git"

DEPENDS += "pdbg zlib libyaml"

FILES_${PN} += "${helpdir}"


