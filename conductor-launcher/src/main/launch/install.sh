#!/bin/bash
#
# Copyright 2018 Mike Hummel
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

LOCAL_REPO_PATH_ZIP="$HOME/.m2/repository/de/mhus/conductor/conductor-launcher/1.1.0-SNAPSHOT/conductor-launcher-1.1.0-SNAPSHOT-install.zip"

if [ ! -e $LOCAL_REPO_PATH_ZIP ]; then
mvn org.apache.maven.plugins:maven-dependency-plugin:3.1.2:get \
    -Dartifact=de.mhus.conductor:conductor-launcher:1.1.0-SNAPSHOT:zip:install
fi

if [ ! -e $LOCAL_REPO_PATH_ZIP ]; then
  echo "Can't download conductor install zip"
  exit 1
fi

if [ ! -d $HOME/.conductor/bin/1.1.0-SNAPSHOT ]; then
  mkdir -p $HOME/.conductor/bin/1.1.0-SNAPSHOT
fi
if [ ! -d $HOME/.conductor/tmp ]; then
  mkdir -p $HOME/.conductor/tmp
fi

cd $HOME/.conductor/bin/1.1.0-SNAPSHOT
unzip -o $LOCAL_REPO_PATH_ZIP
chmod +x $HOME/.conductor/bin/1.1.0-SNAPSHOT/*.sh

if [ -e $HOME/.conductor/bin/con ]; then
  rm $HOME/.conductor/bin/con
fi
ln -s $HOME/.conductor/bin/1.1.0-SNAPSHOT/con.sh $HOME/.conductor/bin/con

echo "Installed 1.1.0-SNAPSHOT in $HOME/.conductor"
echo "Add directory $HOME/.conductor/bin to \$PATH or link $HOME/.conductor/bin/con in a binary directory like /usr/local/bin"
