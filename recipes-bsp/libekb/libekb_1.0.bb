HOMEPAGE =  "https://github.ibm.com/phal/libekb_p10/"

SUMMARY     = "Hardware Procedure Framework"
DESCRIPTION = "Provides infrastructure to run hardware procedures"
PR = "r1"
PV = "1.0+git${SRCPV}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

S = "${WORKDIR}/git"

SRCREV_FORMAT = "libekb_ekb"

SRCREV_libekb = "ce8c8c945ee11b7e057f9f056299cef978ca97e3"
SRCREV_ekb = "f3a4b9a215101a475a30e435923ea023a57574af"

SRC_URI += "git://git@github.ibm.com/phal/libekb_p10;name=libekb;protocol=ssh \
            git://git@github.ibm.com/aisaacs/ekb;destsuffix=git/ekb;name=ekb;branch=master-p10;protocol=ssh"

SRC_URI += "file://enable-istep0-procedures-only.patch"

DEPENDS += " \
        pdbg pdata \
        "
BBCLASSEXTEND = "native"

inherit autotools \
        pythonnative
