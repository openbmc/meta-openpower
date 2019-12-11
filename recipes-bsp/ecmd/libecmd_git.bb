SUMMARY = "eCMD"
DESCRIPTION = "eCMD is a hardware access API for IBM Systems"
LICENSE= "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/NOTICE;md5=fee220301a2af3faf8f211524b4248ea"

SRC_URI = "git://github.com/open-power/eCMD.git"
SRCREV = "6c0348b12c95b3bd6e8d8003f9ff743d25400ae2"
DEPENDS += "python-native zlib"

S = "${WORKDIR}/git"

# Add the hash style option here to Work around this warning:
#   "QA Issue: No GNU_HASH in the elf binary"
#
# The recipe cannot set LDFLAGS in the environment as it overrides the
# internal settings.

do_configure() {
   LD="${LDD} -Wl,--hash-style=gnu" ${S}/config.py --without-swig --output-root ${B} --target obj --extensions "cmd cip" --build-verbose
}

do_compile() {
    cd ${S}
    oe_runmake
}

do_install() {
    install -d ${D}${libdir}
    install -m 0755 out_obj/lib/ecmdClientCapi.a ${D}${libdir}/ecmdClientCapi.a
}

