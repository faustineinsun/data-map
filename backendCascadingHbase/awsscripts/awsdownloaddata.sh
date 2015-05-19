#!/bin/bash

#HHY_BACKEND_PATH=$1 # path to dir backendCascadingHbase

cd $HHY_BACKEND_PATH
wget http://data.cascading.org/cascading-aws-data.tgz
tar xf cascading-aws-data.tgz
rm -f cascading-aws-data.tgz
