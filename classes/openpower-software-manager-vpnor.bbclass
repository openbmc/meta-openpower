PACKAGECONFIG_append = " virtual_pnor"

SYSTEMD_SERVICE_${PN} += " \
        obmc-flash-bios-updatesymlinks.service \
        "
