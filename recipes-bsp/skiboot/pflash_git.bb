SUMMARY = "pflash programmer for OpenPower"
DESCRIPTION = "pflash firmware programming tool for OpenPower machines"

require skiboot.inc
EXTRA_OEMAKE_append = " PFLASH_VERSION=${PV} LINKAGE=dynamic"

#TODO: openbmc/openbmc#1361 - Fix GNU_HASH warnings in pflash
TARGET_CC_ARCH += "${LDFLAGS}"

# Users of libflash use custom makefiles that do not
# support the proper library version detection and linking.
# Because of that, a link must be created within the
# rootfs for applications to use this library.
# This next line tells bitbake to skip the check which
# ensures no links are put in the rootfs
INSANE_SKIP_${PN} += "dev-so"

# Ensure the library is not in the dev package
FILES_SOLIBSDEV = ""

# Now add the link to the production package
FILES_${PN} += "${libdir}/libflash.so"
