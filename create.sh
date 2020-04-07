#!/bin/bash
mvn install || exit 1
cd conductor-launcher
./src/main/launch/install.sh

