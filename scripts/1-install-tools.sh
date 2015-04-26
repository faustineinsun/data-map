#! /bin/bash

sudo apt-get clean 
sudo apt-get -f install
sudo apt-get update

# install java
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java7-installer
sudo apt-get install oracle-java7-set-default
# install ant
sudo apt-get install ant
# install Maven
sudo apt-get install maven
# install Node.js
sudo apt-get install nodejs
sudo apt-get install npm
sudo ln -s /usr/bin/nodejs /usr/bin/node
# install Heroku
sudo wget -qO- https://toolbelt.heroku.com/install-ubuntu.sh | sh

