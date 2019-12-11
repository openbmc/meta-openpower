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
   LD="${LDD} --hash-style=gnu" ${S}/config.py --without-swig --target armv5e --output-root ${B} --extensions "cmd cip" --build-verbose --install-path ${S}
}

do_compile() {
    cd ${S}
    oe_runmake
}

do_install() {
    install -d ${D}${libdir}
    install -m 0755 out_armv5e/lib/libecmd.so ${D}${libdir}/libecmd.so
    install -m 0755 out_armv5e/lib/libcip.so ${D}${libdir}/libcip.so
    install -m 0755 out_armv5e/lib/stub.dll ${D}${libdir}/libstub.so
}

FILES_${PN} = "${libdir}/*"

#For dev packages only
FILES_${PN}-dev = "${includedir}"

# Added this for "QA Issue: No GNU_HASH in the ELF binary"
INSANE_SKIP_${PN} = "ldflags"

#For dev packages only
INSANE_SKIP_${PN}-dev = "ldflags"
