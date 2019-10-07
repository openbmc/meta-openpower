PACKAGECONFIG_append = " single_image_layout"

SYSTEMD_SERVICE_${PN} += " \
        obmc-flash-bios-symlink-ro.service \
        "
