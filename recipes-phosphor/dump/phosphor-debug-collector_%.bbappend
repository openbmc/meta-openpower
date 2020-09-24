FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://plugins.d/guard"

install_openpower_plugins() {
    install -m 0755 ${WORKDIR}/plugins.d/guard ${D}${dreport_plugin_dir}
}

#Link in the plugins so dreport run them at the appropriate time
python link_openpower_plugins() {
    workdir = d.getVar('WORKDIR', True)
    script = os.path.join(workdir, 'plugins.d', 'guard')
    install_dreport_user_script(script, d)
}

# Install dreport/ibm.d
install_dreport_header_script() {
    install -d ${D}${dreport_include_dir}
    install -m 0755 ${S}/tools/dreport.d/ibm.d/* ${D}${dreport_include_dir}/
}

DEBUG_COLLECTOR_INSTALL_POSTFUNCS ?= ""
DEBUG_COLLECTOR_INSTALL_POSTFUNCS_df-openpower ?= "install_openpower_plugins \
link_openpower_plugins install_dreport_header_script"

do_install[postfuncs] += "${DEBUG_COLLECTOR_INSTALL_POSTFUNCS}"
