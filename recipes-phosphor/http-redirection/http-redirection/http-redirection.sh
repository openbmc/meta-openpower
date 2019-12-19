#!/bin/bash

bmcIp=""
bmcPath="/"

readRequest() {
	while true
	do
		read line<&${COPROC[0]}
		reqKey=`echo $line | awk -F '[ :]' '{print$1}' | sed $'s/\r//'`

		if [ -z "$reqKey" ]; then
			break
		fi

		if [ "${reqKey}" == "GET" ]; then
			bmcPath=`echo $line | awk '{print$2}'`
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
	echo "HTTP/1.1 308 Permanent Redirect"
	echo "Content-Length:23"
	echo "Location: https://www.baidu.com"
	echo "Content-Type: text/html; charset=UTF-8"
	echo "Strict-Transport-Security:max-age=31536000;includeSubDomains"
	echo ""
	echo "308 Permanent Redirect"
}

main() {
	while true
	do
		coproc ( nc -l -p 80 )
		{
			readRequest
			sendRespData
		} <&${COPROC[0]} >&${COPROC[1]}
		sleep 0.1
		[[ ! -z "$COPROC_PID" ]] && kill "$COPROC_PID"
        done
}

main
