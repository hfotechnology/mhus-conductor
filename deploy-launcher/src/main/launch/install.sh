#!/bin/bash

if [  ! -f target/cur.jar ]; then
  echo "Change to project root directory"
  exit
fi

mkdir ~/.conductor
mkdir ~/.conductor/lib
cp target/cur.jar ~/.conductor/lib/
cp src/main/launch/cur.sh ~/.conductor/cur
chmod +x ~/.conductor/cur

echo "Installed in ~/.conductor"
echo "Add directory to \$PATH or link it"
