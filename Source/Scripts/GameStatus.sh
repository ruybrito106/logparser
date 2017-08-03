#!/bin/bash

# Compile and run gameStatus

ROOTDIR="$(dirname "$(dirname "$(pwd)")")"
PARSERPATH="$(dirname "$(pwd)")"/Parser
OUTPUTPATH=${ROOTDIR}/Outputs
JUNITPATH=${ROOTDIR}/lib/junit-4.12.jar

javac -cp .:${JUNITPATH} ${PARSERPATH}/*.java
cd ${PARSERPATH}

if [ $# -lt 3 ]
then
  java GameStatus "$1" "$2" > ${OUTPUTPATH}/gameStatus.txt
  echo "File outputted at ${OUTPUTPATH}/gameStatus.txt"
elif [ $# -eq 3 ]
then
  java GameStatus "$1" "$2" > ${3:1:${#3}-1}
  echo "File outputted at ${3:1:${#3}-1}"
else
  echo "Too many parameters"
fi
