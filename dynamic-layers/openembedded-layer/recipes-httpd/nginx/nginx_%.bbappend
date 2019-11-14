FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

inherit obmc-phosphor-systemd

SRC_URI += " \
    file://nginx.conf \
    "

do_install_append() {

    install -m 644 ${WORKDIR}/nginx.conf ${D}${sysconfdir}/nginx
}
