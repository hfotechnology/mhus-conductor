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

LOCAL_REPO_PATH_JAR = "~/.m2/repository/de/mhus/conductor/conductor-launcher/{{project.version}}/conductor-launcher-{{project.version}}-con.jar"

if [ ! -x $LOCAL_REPO_PATH_JAR ]; then
mvn org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
    -DrepoUrl=https://repo1.maven.org/maven2 \
    -Dartifact=de.mhus.conductor:conductor-launcher:{{project.version}}:jar:con
fi

if [ ! -x $LOCAL_REPO_PATH_JAR ]; then
  echo "Can't download conductor binary"
  exit 1
fi

LOCAL_REPO_PATH_SH = "~/.m2/repository/de/mhus/conductor/conductor-launcher/{{project.version}}/conductor-launcher-{{project.version}}-con.sh"

if [ ! -x $LOCAL_REPO_PATH_SH ]; then
mvn org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
    -DrepoUrl=https://repo1.maven.org/maven2 \
    -Dartifact=de.mhus.conductor:conductor-launcher:{{project.version}}:sh:con
fi

if [ ! -x $LOCAL_REPO_PATH_SH ]; then
  echo "Can't download conductor start script"
  exit 1
fi

mkdir ~/.conductor
mkdir ~/.conductor/lib
mkdir ~/.conductor/lib/{{project.version}}
mkdir ~/.conductor/bin
mkdir ~/.conductor/bin/{{project.version}}
mkdir ~/.conductor/tmp

cp $LOCAL_REPO_PATH_JAR ~/.conductor/lib/{{project.version}}/con.jar
cp $LOCAL_REPO_PATH_SH ~/.conductor/bin/{{project.version}}/con
chmod +x ~/.conductor/bin/{{project.version}}/con
rm ~/.conductor/bin/con
ln -s ~/.conductor/bin/{{project.version}}/con ~/.conductor/bin/con

echo "Installed in ~/.conductor"
echo "Add directory to \$PATH or link ~/.conductor/bin/con"
