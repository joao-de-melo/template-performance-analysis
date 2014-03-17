#!/bin/bash

VERSION=1.0-SNAPSHOT

if [ ! -d test-server ]; then
	echo "Must call this script from it's directory"
	exit
fi

if [ $# -ne 1 ]; then
	echo "Missing template system to test. Possible "
	ls | grep "\-performance" | grep -v "\." | sed 's/-performance//g' | sed 's/^/ - /g'
	exit
fi

if [ ! -d $1-performance ]; then
	echo "Unknown template system. Existing ones: "
	ls | grep "\-performance" | grep -v "\." | sed 's/-performance//g' | sed 's/^/ - /g'
        exit
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

echo "Starting Server with $1 template engine"
cd $1-performance
java -jar target/$1-performance-$VERSION.jar >> server.log 2>&1 &

sleep 30
cd ..

echo "Running Simple Case"
java -jar test-server/target/test-server-$VERSION-jar-with-dependencies.jar 2> results/$1-simple.csv
echo "Running Complex Case"
java -jar test-server/target/test-server-$VERSION-jar-with-dependencies.jar "complex" 2> results/$1-complex.csv

echo "Created files: results/$1-simple.csv and results/$1-complex.csv, send them to => jmelo@lyncode.com"
PID=`ps aux | grep java | grep performance-$VERSION | awk { print $2 }`
if [ "$PID" != "" ]; then
	kill -9 $PID
fi
PID=`lsof -i :8080 | grep java | awk '{ print $2 }' | head -n 1`
if [ "$PID" != "" ]; then
	kill -9 $PID
fi
echo "Thank you!"



