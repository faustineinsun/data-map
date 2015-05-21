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
# install htop
sudo apt-get install htop
# install tree
sudo apt-get install tree

###########################
# install hadoop

mkdir $HOME/tools
TOOLSHOME=$HOME/tools/
cd $TOOLSHOME
wget http://apache.mirrors.pair.com/hadoop/common/stable/hadoop-2.6.0.tar.gz
tar -zxvf hadoop-2.6.0.tar.gz
rm hadoop-2.6.0.tar.gz

#vim ~/.profile
#export HADOOP_HOME=/home/ubuntu/tools/hadoop-2.6.0
#export PATH=$PATH:$HADOOP_HOME/bin
#source ~/.profile

# follow hadoop configuration http://faustineinsun.blogspot.com/2014/01/setup-hadoop-220-yarn-on-single-node.html
#cd $HADOOP_HOME/etc/hadoop
#vim hadoop-env.sh
#export JAVA_HOME=/usr/lib/jvm/java-7-oracle/jre/

#http://amodernstory.com/2014/09/23/installing-hadoop-on-mac-osx-yosemite/#localhost
#ssh-keygen -t rsa
#cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
#ssh localhost
#exit

#mkdir -p $HADOOP_HOME/mydata/tmp
#mkdir -p $HADOOP_HOME/mydata/mapred/temp
#mkdir -p $HADOOP_HOME/mydata/mapred/local
#mkdir -p $HADOOP_HOME/mydata/hdfs/namenode
#mkdir -p $HADOOP_HOME/mydata/hdfs/datanode

###########################
