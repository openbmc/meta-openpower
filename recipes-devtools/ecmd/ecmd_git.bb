SUMMARY = "eCMD"
LICENSE= "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/NOTICE;md5=fee220301a2af3faf8f211524b4248ea"

SRC_URI = "git://github.com/open-power/eCMD.git"
SRCREV = "6c0348b12c95b3bd6e8d8003f9ff743d25400ae2"
DEPENDS += "python-native zlib"

S = "${WORKDIR}/git"

do_configure() {
   LD="${CXX}" ${S}/config.py --without-swig --target obj --extensions "cmd cip"
}

do_compile() {
    cd ${S}/dllNetwork/server
    make
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 out_obj/croserver ${D}${bindir}
}
