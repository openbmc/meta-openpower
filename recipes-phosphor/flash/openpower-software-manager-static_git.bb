require openpower-software-manager.inc

RDEPENDS_${PN} += "pflash"

SYSTEMD_SERVICE_${PN} += "openpower-pnor-update@.service"

