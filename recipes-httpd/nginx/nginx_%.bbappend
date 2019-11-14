FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

inherit obmc-phosphor-systemd

SRC_URI += " \
    file://nginx.conf \
    file://nginx.service \
    "
do_install_append() {

    install -m 644 ${WORKDIR}/nginx.conf ${D}${sysconfdir}/nginx
}

SYSTEMD_SERVICE_${PN} += " nginx.service"
