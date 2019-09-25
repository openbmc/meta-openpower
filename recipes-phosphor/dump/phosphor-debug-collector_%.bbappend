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

do_install[postfuncs] += "install_openpower_plugins"
do_install[postfuncs] += "link_openpower_plugins"
