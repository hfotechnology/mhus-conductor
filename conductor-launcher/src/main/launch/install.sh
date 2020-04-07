#!/bin/bash

if [  ! -f target/con.jar ]; then
  echo "Change to project root directory"
  exit
fi

mkdir ~/.conductor
mkdir ~/.conductor/lib
mkdir ~/.conductor/bin
mkdir ~/.conductor/tmp

cp target/con.jar ~/.conductor/lib/
cp src/main/launch/con.sh ~/.conductor/bin/con
chmod +x ~/.conductor/bin/con

echo "Installed in ~/.conductor"
echo "Add directory to \$PATH or link ~/.conductor/bin/con"
