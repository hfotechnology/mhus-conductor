
#!/bin/sh

# Copyright Mike Hummel
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

if [ "x${CON_VERSION}" = "x" ] ; then
  CON_VERSION=$(curl -L -s https://api.github.com/repos/mhus/mhus-conductor/releases | \
                  grep tag_name | sed "s/ *\"tag_name\": *\"\\(.*\\)\",*/\\1/" | \
          grep -v -E "(alpha|beta|rc)\.[0-9]$" | sort -t"." -k 1,1 -k 2,2 -k 3,3 -k 4,4 | tail -n 1)
fi

if [ "x${CON_VERSION}" = "x" ] ; then
  printf "Unable to get latest Conductor version. Set CON_VERSION env var and re-run. For example: export CON_VERSION=1.0.0"
  exit;
fi

CON_JAR_NAME="conductor-launcher-${CON_VERSION}-con.jar"
URL="https://repo1.maven.org/maven2/de/mhus/conductor/conductor-launcher/1.1.0-SNAPSHOT/$CON_JAR_NAME"

printf "Downloading %s ..." "$URL"
if ! curl -fsLO "$URL"
then
    printf "\n\n"
    printf "Unable to download Conductor %s at this moment!\n" "$CON_VERSION"
    printf "Please verify the version you are trying to download.\n\n"
    exit
fi

CON_SH_NAME="conductor-launcher-${CON_VERSION}-con.sh"
URL="https://repo1.maven.org/maven2/de/mhus/conductor/conductor-launcher/1.1.0-SNAPSHOT/$CON_SH_NAME"

printf "Downloading %s ..." "$URL"
if ! curl -fsLO "$URL"
then
    printf "\n\n"
    printf "Unable to download Conductor %s at this moment!\n" "$CON_VERSION"
    printf "Please verify the version you are trying to download.\n\n"
    exit
fi

# install


mkdir ~/.conductor
mkdir ~/.conductor/lib
mkdir ~/.conductor/bin
mkdir ~/.conductor/tmp

cp $CON_JAR_NAME ~/.conductor/lib/con.jar
cp $CON_SH_NAME ~/.conductor/bin/con
chmod +x ~/.conductor/bin/con

rm $CON_JAR_NAME
rm $CON_SH_NAME

echo "Installed in ~/.conductor"
echo "Add directory to \$PATH or link ~/.conductor/bin/con"

~/.conductor/bin/con -version


