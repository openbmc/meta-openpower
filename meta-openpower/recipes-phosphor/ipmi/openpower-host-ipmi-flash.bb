SUMMARY = "Phosphor IPMI plugin for the Host I/O Mapping Protocol"
DESCRIPTION = "Phosphor IPMI plugin for the Host I/O Mapping Protocol"
HOMEPAGE = "https://github.com/openbmc/openpower-host-ipmi-oem"
PR = "r1"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ff331e820fda701d36a8f0efc98adc58"

inherit autotools pkgconfig
inherit obmc-phosphor-ipmiprovider-symlink

DEPENDS += "phosphor-ipmi-host"
DEPENDS += "autoconf-archive-native"
DEPENDS += "sdbusplus sdbusplus-native"
DEPENDS += "phosphor-logging"
DEPENDS += "phosphor-dbus-interfaces phosphor-dbus-interfaces-native"
DEPENDS += "openpower-dbus-interfaces openpower-dbus-interfaces-native"
DEPENDS += "sdbus++-native"

RDEPENDS_${PN} += " \
        sdbusplus \
        phosphor-logging \
        openpower-dbus-interfaces \
        phosphor-dbus-interfaces \
        "

TARGET_CFLAGS += "-fpic"

HIOMAP_PROVIDER_LIBRARY += "libhiomap.so"

S = "${WORKDIR}/git"

SRC_URI += "git://github.com/openbmc/openpower-host-ipmi-flash;nobranch=1"
SRCREV = "eda20fcef7891f2d540b2c1e62dcad921db94a4b"

FILES_${PN}_append = " ${libdir}/ipmid-providers/lib*${SOLIBS}"
FILES_${PN}_append = " ${libdir}/host-ipmid/lib*${SOLIBS}"
FILES_${PN}-dev_append = " ${libdir}/ipmid-providers/lib*${SOLIBSDEV} ${libdir}/ipmid-providers/*.la"
