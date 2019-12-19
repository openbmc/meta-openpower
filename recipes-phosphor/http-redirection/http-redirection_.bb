SUMMARY = "HTTP redirection HTTPS"
DESCRIPTION = "Listen 80 port and HTTP redirection HTTPS to WebUI"
PR = "r1"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit systemd

SYSTEMD_SERVICE_${PN} = "http-redirection@.service"

SRC_URI = "file://${BPN}.sh \
           file://${BPN}@.service \
           "

S = "${WORKDIR}"
do_install() {
    install -d ${D}${bindir} ${D}${systemd_system_unitdir}
    install ${BPN}.sh ${D}${bindir}/
    install -m 644 ${BPN}@.service ${D}${systemd_system_unitdir}/
}
