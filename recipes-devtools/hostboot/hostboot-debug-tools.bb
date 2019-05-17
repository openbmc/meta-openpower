SUMMARY = "OPENPOWER - Hostboot debug tools"
DESCRIPTION = "Tools to collect the hostboot debug data"
LICENSE = "Apache-2.0"
PR = "r1"

inherit allarch


RDEPENDS_${PN} = " \
    perl perl-module-pod-text perl-module-lib \
    perl-module-constant \
    perl-module-errno perl-module-fcntl \
    perl-module-file-basename perl-module-file-copy \
    perl-module-file-find perl-module-file-glob \
    perl-module-file-path perl-module-file-spec \
    perl-module-file-temp perl-module-getopt-long \
    perl-module-re perl-module-text-wrap \
    perl-module-posix perl-module-bigint \
    perl-module-integer \
    "
