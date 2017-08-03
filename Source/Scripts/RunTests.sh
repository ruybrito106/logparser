#!/bin/bash

# Compile the whole project and run the tests over all classes

ROOTDIR="$(dirname "$(dirname "$(pwd)")")"
OUTPUTPATH=${ROOTDIR}/Outputs
JUNITPATH=${ROOTDIR}/lib/junit-4.12.jar
HAMCRESTPATH=${ROOTDIR}/lib/hamcrest-core-1.3.jar
CLASSESPATH=${ROOTDIR}/Source/Parser/

javac -cp .:${JUNITPATH} ${CLASSESPATH}/*.java
cd ${CLASSESPATH}
java -cp .:${JUNITPATH}:${HAMCRESTPATH} org.junit.runner.JUnitCore LogHandlerTest > ${OUTPUTPATH}/TestStatus/LogHandlerStatus.txt
java -cp .:${JUNITPATH}:${HAMCRESTPATH} org.junit.runner.JUnitCore DataTest > ${OUTPUTPATH}/TestStatus/DataStatus.txt
java -cp .:${JUNITPATH}:${HAMCRESTPATH} org.junit.runner.JUnitCore GameStatusTest > ${OUTPUTPATH}/TestStatus/GameStatusStatus.txt
java -cp .:${JUNITPATH}:${HAMCRESTPATH} org.junit.runner.JUnitCore RankingTest > ${OUTPUTPATH}/TestStatus/RankingStatus.txt
java -cp .:${JUNITPATH}:${HAMCRESTPATH} org.junit.runner.JUnitCore UtilTest > ${OUTPUTPATH}/TestStatus/UtilStatus.txt
java -cp .:${JUNITPATH}:${HAMCRESTPATH} org.junit.runner.JUnitCore MatchStatusTest > ${OUTPUTPATH}/TestStatus/MatchStatusStatus.txt
java -cp .:${JUNITPATH}:${HAMCRESTPATH} org.junit.runner.JUnitCore ParserThreadTest > ${OUTPUTPATH}/TestStatus/ParserThreadStatus.txt

echo "The results can be found at: ${OUTPUTPATH}/TestStatus"
