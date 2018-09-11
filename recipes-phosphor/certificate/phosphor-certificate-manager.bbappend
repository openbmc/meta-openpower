FILESEXTRAPATHS_append := "${THISDIR}/${PN}:"

SYSTEMD_ENVIRONMENT_FILE_${PN} += "obmc/cert/https"

SYSTEMD_SERVICE_${PN} += "phosphor-certificate-manager-https.service"
