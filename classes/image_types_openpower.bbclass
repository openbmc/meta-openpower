inherit image_types_phosphor

IMAGE_TYPES += "mmc-system-tar"
IMAGE_TYPEDEP_mmc-system-tar = "${FLASH_EXT4_BASETYPE}"
IMAGE_TYPES_MASKED += "mmc-system-tar"

do_generate_mmc_tar() {
    cd ${STAGING_DIR_NATIVE}${datadir}/pnor/
    mksquashfs squashfs-root/* pnor.xz.squashfs -all-root
    tar --exclude './squashfs-root' -cvf witherspoon.pnor.squashfs.tar MANIFEST publickey pnor.xz.squashfs *.sig
}
do_generate_mmc_tar[dirs] = " ${S}/ext4"
do_generate_mmc_tar[depends] += " \
        ${PN}:do_image_${FLASH_EXT4_BASETYPE} \
        virtual/kernel:do_deploy \
        u-boot:do_populate_sysroot \
        openssl-native:do_populate_sysroot \
        ${SIGNING_KEY_DEPENDS} \
        ${PN}:do_copy_signing_pubkey \
        host-firmware-native:do_populate_sysroot \
        "

python() {
    types = d.getVar('IMAGE_FSTYPES', True).split()

    if 'mmc-system-tar' in types:
        bb.build.addtask(
                'do_generate_mmc_tar',
                'do_image_complete',
                'do_generate_rwfs_ext4 do_generate_phosphor_manifest', d)
}
