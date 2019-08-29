SUMMARY = "OpenPower error logging facilities"
DESCRIPTION = "Adds additional error logging functionality for OpenPower systems"
PR = "r1"
PV = "1.0+git${SRCPV}"
HOMEPAGE = "https://github.com/openbmc/openpower-logging"

LICENSE ?= "Apache-2.0"
LIC_FILES_CHKSUM ?= "file://${PHOSPHORBASE}/COPYING.apache-2.0;md5=34400b68072d710fecd0a2940a0d1658"
inherit pythonnative
inherit allarch

DEPENDS += " \
         python-native \
         python-jsonschema-native \
         "

S = "${WORKDIR}/git"

SRC_URI = "git://github.com/openbmc/openpower-logging"
SRCREV = "3df077fc9ff2eee77579f21c231fbbd9d3363058"

FILES_${PN} = "${datadir}/phosphor-logging/pels/message_registry.json"

do_install() {

    # Validate the registry against the schema
    ${S}/pels/registry/tools/process_registry.py -v \
        -r ${S}/pels/registry/message_registry.json \
        -s ${S}/pels/registry/schema/schema.json

    install -d ${D}${datadir}/phosphor-logging/pels
    install -m 0644 ${S}/pels/registry/message_registry.json \
        ${D}${datadir}/phosphor-logging/pels/
}
