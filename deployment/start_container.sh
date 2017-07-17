#!/bin/bash
chown -R ubuntu:ubuntu /home/ubuntu/actor/stringanalyseractor/
cd /home/ubuntu/actor/stringanalyseractor/

echo "build new image aucobo/stringanalyseractor"
sudo -u ubuntu docker build -t aucobo/stringanalyseractor . --no-cache

container=$(sudo -u ubuntu docker container ls -a --filter name=aucobo-stringanalyseractor$ -q)
if [ "$container" != "" ];then
  echo "stop container aucobo-stringanalyseractor"
  sudo -u ubuntu docker stop aucobo-stringanalyseractor
  sudo -u ubuntu docker rm -f aucobo-stringanalyseractor
fi
unset container

echo "create container aucobo-stringanalyseractor"
sudo -u ubuntu docker create --name aucobo-stringanalyseractor --restart=always aucobo/stringanalyseractor
sudo -u ubuntu docker start aucobo-stringanalyseractor