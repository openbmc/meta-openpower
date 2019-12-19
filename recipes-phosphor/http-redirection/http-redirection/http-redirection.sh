#!/bin/bash

getBMCIp() {
	while :; do
		read line<&${COPROC[0]}
		host=`echo $line | awk -F : '{print$1}'`

		if [ "${host}" == "Host" ]; then
			bmcIp=`echo $line | awk -F : '{print$2}' | awk '{gsub(/^\s+|\s+$/, "");print}'`
			break
		else
			bmcIp=""
		fi
	done

	if [ -z "$bmcIp" ]; then
		exit -2
	fi
}

sendRespData() {
	echo >&${COPROC[1]} "HTTP/1.1 308 Permanent Redirect"
	echo >&${COPROC[1]} "Location: https://$bmcIp"
	echo >&${COPROC[1]} "Content-Type: text/html; charset=UTF-8"
	echo >&${COPROC[1]} "Strict-Transport-Security:max-age=31536000;includeSubDomains"
}

main() {
	while true
	do
	    coproc ( nc -l -p 80 )
		{
			getBMCIp
			sendRespData
		}
		kill "$COPROC_PID"
		sleep 0.1
	done
}

main
