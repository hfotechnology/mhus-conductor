#!/bin/bash
mvn clean install || exit 1
cd deploy-launcher
./src/main/launch/install.sh 

