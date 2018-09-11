FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SYSTEMD_ENVIRONMENT_FILE_${PN} += "obmc/cert/https"
OP_CERTS = "https"
SYSTEMD_LINK_${PN} += "${@compose_list(d, 'CERT_FMT', 'OP_CERTS')}"

