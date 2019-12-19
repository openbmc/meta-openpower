#!/bin/sh -eu

if [ $# -ne 1 ];
then
	echo "error, please input NIC name. eg: eth0"
	exit 0
fi

bmcIp="127.0.0.1"
NIC=$1

trap 'onCtrlC' INT
onCtrlC() {
    echo 'Ctrl+C is captured'
    exit 0
}

getBMCIp() {
	ipCount=`ip -o -4 addr list $NIC | awk 'END{print NR}'`
	if [ $ipCount -eq 0 ]; then
	    return
	fi

	if [ $ipCount -gt 1 ]; then
	    bmcIp=`ip -o -4 addr list $NIC | awk '{print$4}' | cut -d/ -f1 | awk 'NR==2{print}'`
	else
	    bmcIp=`ip -o -4 addr list $NIC | awk '{print$4}' | cut -d/ -f1 | awk 'NR==1{print}'`
	fi
}

getRespData() {
	respData="HTTP/1.1 308 Permanent Redirect\nLocation: https://$bmcIp\nContent-Type: text/html; charset=UTF-8\nStrict-Transport-Security:max-age=31536000;includeSubDomains\n\n"
}

main() {

	getBMCIp

	getRespData

	while true
	do
	    echo -e $respData | nc -l -p 80 | echo -e '\003' > /dev/null
	done
}

main
