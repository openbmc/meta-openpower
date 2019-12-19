#!/bin/bash

bmcIp=""
bmcPath="/"

readBMCIp() {
	while true
	do
		read line<&${COPROC[0]}
		reqKey=`echo $line | awk '{print$1}' | awk -F : '{print$1}'`

		if [ -z "$reqKey" ]; then
			break
		fi

		if [ "${reqKey}" == "GET" ]; then
			bmcPath=`echo $line | awk '{print$2}' | awk '{gsub(/^\s+|\s+$/, "");print}'`
		fi

		if [ "${reqKey}" == "Host" ]; then
			bmcIp=`echo $line | awk -F : '{print$2}' | awk '{gsub(/^\s+|\s+$/, "");print}'`
		fi
	done

	if [ -z "$bmcIp" ]; then
		exit -2
	fi
}

sendRespData() {
	echo >&${COPROC[1]} "HTTP/1.1 308 Permanent Redirect"
	echo >&${COPROC[1]} "Location: https://$bmcIp$bmcPath"
	echo >&${COPROC[1]} "Content-Type: text/html; charset=UTF-8"
	echo >&${COPROC[1]} "Strict-Transport-Security:max-age=31536000;includeSubDomains"
}

main() {
	while true
	do
		coproc ( nc -l -p 80 )
		{
			readBMCIp
			sendRespData
		}
		kill "$COPROC_PID"
		sleep 0.1
	done
}

main
