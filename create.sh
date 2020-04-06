#!/bin/bash
mvn clean install || exit 1
cd conductor-launcher
./src/main/launch/install.sh 

