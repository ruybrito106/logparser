#!/bin/bash

LIBPATH="$(dirname "$(dirname "$(pwd)")")"/lib

# Tries to add JUnit 4.12 jar file, in case is not already installed

if [ "$(uname)" == "Darwin" ];
then
  if [ ! -f ${LIBPATH}/junit-4.12.jar ];
  then
    curl -o ${LIBPATH}/junit-4.12.jar 'http://central.maven.org/maven2/junit/junit/4.12/junit-4.12.jar'
  else
    echo "JUnit-4.12 already downloaded."
  fi

  # Tries to add Hamcrest 1.3 jar file, in case is not already installed

  if [ ! -f ${LIBPATH}/hamcrest-core-1.3.jar ];
  then
    curl -o ${LIBPATH}/hamcrest-core-1.3.jar 'http://central.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar'
  else
    echo "Hamcrest-core-1.3 already downloaded."
  fi
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ];
then
  if [ ! -f ${LIBPATH}/junit-4.12.jar ];
  then
    wget -P ${LIBPATH} http://central.maven.org/maven2/junit/junit/4.12/junit-4.12.jar
  else
    echo "Dependency already downloaded."
  fi

  # Tries to add Hamcrest 1.3 jar file, in case is not already installed

  if [ ! -f ${LIBPATH}/hamcrest-core-1.3.jar ];
  then
    wget -P ${LIBPATH} http://central.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
  else
    echo "Dependency already downloaded."
  fi
fi

echo "Dependencies installed in: ${LIBPATH}"
