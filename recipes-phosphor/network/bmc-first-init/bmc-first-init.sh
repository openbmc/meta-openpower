#!/bin/bash -eu

show_error() {
    logger -p user.error -t bmc-first-init $@
}

sync_mac() {

    MAPPER_IFACE='xyz.openbmc_project.ObjectMapper'
    MAPPER_PATH='/xyz/openbmc_project/object_mapper'
    INVENTORY_PATH='/xyz/openbmc_project/inventory'
    NETWORK_ITEM_IFACE='xyz.openbmc_project.Inventory.Item.NetworkInterface'

    NETWORK_ITEM_PATH=$(busctl --no-pager --verbose call \
                            ${MAPPER_IFACE} \
                            ${MAPPER_PATH} \
                            ${MAPPER_IFACE} \
                            GetSubTree sias \
                            ${INVENTORY_PATH} 0 1 ${NETWORK_ITEM_IFACE} \
                        2>/dev/null | grep ${INVENTORY_PATH} || true)

    # '     STRING "/xyz/openbmc_project/inventory/system/chassis/ethernet";'
    NETWORK_ITEM_PATH=${NETWORK_ITEM_PATH#*\"}
    NETWORK_ITEM_PATH=${NETWORK_ITEM_PATH%\"*}

    NETWORK_ITEM_SERVICE=$(mapper get-service \
                                ${NETWORK_ITEM_PATH} 2>/dev/null || true)

    if [[ -z "${NETWORK_ITEM_SERVICE}" ]]; then
        show_error 'No Ethernet interface found in the Inventory. Is VPD EEPROM empty?'
        exit 1
    fi

    MAC_ADDR=$(busctl get-property ${NETWORK_ITEM_SERVICE} \
                                ${NETWORK_ITEM_PATH} \
                                ${NETWORK_ITEM_IFACE} MACAddress)

    # 's "54:52:01:02:03:04"'
    MAC_ADDR=${MAC_ADDR#*\"}
    MAC_ADDR=${MAC_ADDR%\"*}

    if [[ -n "${MAC_ADDR}" ]]; then
        busctl set-property xyz.openbmc_project.Network \
                            /xyz/openbmc_project/network/$1 \
                            xyz.openbmc_project.Network.MACAddress \
                            MACAddress s ${MAC_ADDR}
    fi
}

sync_uuid(){

    MACHINE_ID_File="/etc/machine-id"
    MAPPER_IFACE='xyz.openbmc_project.ObjectMapper'
    MAPPER_PATH='/xyz/openbmc_project/object_mapper'
    INVENTORY_PATH='/xyz/openbmc_project/inventory'
    UUID_ITEM_IFACE='xyz.openbmc_project.Common.UUID'

    UUID_ITEM_PATH=$(busctl --no-pager --verbose call \
                            ${MAPPER_IFACE} \
                            ${MAPPER_PATH} \
                            ${MAPPER_IFACE} \
                            GetSubTree sias \
                                    ${INVENTORY_PATH} 0 1 ${UUID_ITEM_IFACE} \
                        2>/dev/null | grep ${INVENTORY_PATH} || true)

    # 'STRING "/xyz/openbmc_project/inventory/system/chassis/motherboard/boxelder/bmc";'
    UUID_ITEM_PATH=${UUID_ITEM_PATH#*\"}
    UUID_ITEM_PATH=${UUID_ITEM_PATH%\"*}

    UUID_ITEM_SERVICE=$(mapper get-service ${UUID_ITEM_PATH} 2>/dev/null || true)

    if [[ -z "${UUID_ITEM_SERVICE}" ]]; then
        show_error 'No UUID interface found in the Inventory'
        exit 1
    fi

    MACHINE_ID=$(cat "$MACHINE_ID_File")
    if [[ -n "${MACHINE_ID}" ]]; then
        busctl  set-property $UUID_ITEM_SERVICE $UUID_ITEM_PATH \
                            $UUID_ITEM_IFACE UUID s ${MACHINE_ID}
    fi
}

if [ $# -eq 0 ]
  then echo 'No Ethernet interface name is given'
  exit
fi

sync_mac $1
sync_uuid

systemctl disable bmc-first-init@${1}.service
