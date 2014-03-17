#!/bin/bash

VERSION=1.0-SNAPSHOT
CASES="jtwig velocity freemarker jsp"

if [ ! -d test-server ]; then
	echo "Must call this script from it's directory"
	exit
fi


if [ $# -eq 1 ]; then
	if [ ! -d $1-performance ]; then
		echo "Unknown template system. Existing ones: "
		ls | grep "\-performance" | grep -v "\." | sed 's/-performance//g' | sed 's/^/ - /g'
	        exit
	fi
	CASES="$1"
fi

if [ ! -d results ]; then
	mkdir results
fi

if [ ! -f test-server/target/test-server-$VERSION-jar-with-dependencies.jar ]; then
	echo "Building..."
	mvn package

	cd test-server
	mvn package assembly:single
	cd ..
fi

for CASE in $CASES; do

	echo "Starting Server with $CASE template engine"
	cd $CASE-performance
	java -jar target/$CASE-performance-$VERSION.jar >> server.log 2>&1 &

	sleep 30
	cd ..

	echo "Running Simple Case for $CASE"
	java -jar test-server/target/test-server-$VERSION-jar-with-dependencies.jar 2> results/$CASE-simple.csv
	echo "Running Complex Case for $CASE"
	java -jar test-server/target/test-server-$VERSION-jar-with-dependencies.jar "complex" 2> results/$CASE-complex.csv
	PID=`ps aux | grep java | grep performance-$VERSION | awk { print $2 }`
	if [ "$PID" != "" ]; then
		kill -9 $PID
	fi
	PID=`lsof -i :8080 | grep java | awk '{ print $2 }' | head -n 1`
	if [ "$PID" != "" ]; then
		kill -9 $PID
	fi
done

echo "Send files in results directory to jmelo@lyncode.com"
echo "Thank you!"



