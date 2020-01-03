SUMMARY = "eCMD"
DESCRIPTION = "eCMD is a hardware access API for IBM Systems"
LICENSE= "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/NOTICE;md5=fee220301a2af3faf8f211524b4248ea"

SRC_URI = "git://github.com/shenki/eCMD.git;branch=fsi-master-path"
SRCREV = "d164fed92f22e0097f76947038d607e9f4b8ee18"
DEPENDS += "python-native zlib"

SRC_URI += "file://croserver.service"

S = "${WORKDIR}/git"

# Add the hash style option here to Work around this warning:
#   "QA Issue: No GNU_HASH in the elf binary"
#
# The recipe cannot set LDFLAGS in the environment as it overrides the
# internal settings.
do_configure() {
   LD="${CXX} -Wl,--hash-style=gnu" ${S}/config.py --without-swig --output-root ${B} --target obj --extensions "cmd cip" --build-verbose
}

do_compile() {
    cd ${S}/dllNetwork/server
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 out_obj/lib/server1p ${D}${bindir}/croserver

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/croserver.service ${D}${systemd_system_unitdir}/
}

FILES_${PN} += "${systemd_system_unitdir}/croserver.service"
