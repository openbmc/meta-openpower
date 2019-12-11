SUMMARY = "eCMD"
DESCRIPTION = "eCMD is a hardware access API for POWER Systems"
LICENSE= "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/NOTICE;md5=fee220301a2af3faf8f211524b4248ea"

SRC_URI = "git://github.com/open-power/eCMD.git"
SRCREV = "63e2c614079429d45ecd83d74c1f4f73982123c7"

inherit pythonnative
DEPENDS = "zlib"

S = "${WORKDIR}/git"

export LD="${CXX}"
export SLDFLAGS="${LDFLAGS}"

do_configure() {
   ${S}/config.py --without-swig --without-python --without-python3 --without-perl \
       --without-pyecmd --install-path ${D}${prefix} --output-root ${B} --target ${TARGET_ARCH} \
       --extensions "cmd cip"
}

do_compile() {
    oe_runmake all
}

do_install() {
    oe_runmake install

    # we don't need the target scripts or ecmd setup
    rm ${D}${bindir}/target.* ${D}${bindir}/ecmdsetup.pl

    # ecmd installs to atypical places in the filesystem.
    # move all the installed files to more conventional locations.
    install -d ${D}${includedir} ${D}${datadir}/${BPN}/help ${D}${bindir} ${D}${libdir}
    mv ${D}${prefix}/capi/**  ${D}${includedir}
    mv ${D}${prefix}/ext/** ${D}${includedir}
    mv ${D}${prefix}/help/**  ${D}${datadir}/${BPN}/help
    mv ${D}${prefix}/${TARGET_ARCH}/bin/**  ${D}${bindir}
    mv ${D}${prefix}/${TARGET_ARCH}/lib/**  ${D}${libdir}

    rmdir ${D}${prefix}/ext \
        ${D}${prefix}/capi \
        ${D}${prefix}/help \
        ${D}${prefix}/${TARGET_ARCH}/lib \
        ${D}${prefix}/${TARGET_ARCH}/bin \
        ${D}${prefix}/${TARGET_ARCH}/perl \
        ${D}${prefix}/${TARGET_ARCH}
}

# ecmd makefile assumes that dependencies are built from left to right.
PARALLEL_MAKE = ""

# ecmd doesn't have proper library versioning
FILES_${PN}-dev_remove = "${libdir}/lib*.so"
FILES_${PN} += "${libdir}/lib*.so"

PACKAGE_BEFORE_PN = "libecmd-bin"
FILES_${PN}-bin = "${bindir}"
