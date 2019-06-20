# Overwrite the config.json to turn off/on Web UI panels
# E.g. Turn off the Redfish Event Log Panel since we use D-Bus Event Log Panel
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " file://config.json"

do_compile_prepend() {
    # Overwrite the config.json to turn off/on Web UI panels
    # E.g. Turn off the Redfish Event Log Panel since openpower systems use
    # the D-Bus Event Log Panel
    cp -r ${WORKDIR}/config.json ${S}/
}
