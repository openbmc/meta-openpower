FILESEXTRAPATHS_append := "${THISDIR}/${PN}:"

CERT_PACKAGES += " \
        ${PN}-https \
        "

SYSTEMD_ENVIRONMENT_FILE_${PN}-https = "obmc/cert/https"

OP_CERTS = "https"
SYSTEMD_LINK_${PN}-https = "${@compose_list(d, 'CERT_FMT', 'OP_CERTS')}"
