FILESEXTRAPATHS_append := "${THISDIR}/${PN}:"

CERT_PACKAGES += " \
        phosphor-cert-https \
        "

SYSTEMD_ENVIRONMENT_FILE_phosphor-cert-https = "obmc/cert/https"

OP_CERTS = "https"
SYSTEMD_LINK_phosphor-cert-https = "${@compose_list(d, 'CERT_FMT', 'OP_CERTS')}"
