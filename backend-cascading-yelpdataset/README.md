##### On terminal

```
$ gradle tasks
$ gradle build
> $ gradle clean jar
> $ hdfs dfs -rm -r /output
> $ hadoop jar ./build/libs/backend.jar hdfs://localhost:8020/data/rain.txt hdfs://localhost:8020/output/wc hdfs://localhost:8020/data/en.stop hdfs://localhost:8020/output/tfidf hdfs://localhost:8020/output/trap hdfs://localhost:8020/output/check
```

##### On Eclipse  

```
> $ rm -rf output
> data/rain.txt output/wc data/en.stop output/tfidf output/trap output/check
Note that WARNINGs are shown in Eclipse 
```

##### [Run it on Amazon AWS Elastic MapReduce service](http://docs.cascading.org/tutorials/cascading-aws/)   

* [Launch Amazon Redshift Cluster](http://docs.aws.amazon.com/redshift/latest/gsg/rs-gsg-prereq.html) - two month free trial of our dw2.large node, 750 hours/month   
    * [Download the Amazon Redshift JDBC Driver](http://docs.aws.amazon.com/redshift/latest/mgmt/configure-jdbc-connection.html#download-jdbc-driver)
        * RedshiftJDBC41-1.1.2.0002.jar
    * [Install SQL Workbench/J (MAC)](http://docs.aws.amazon.com/redshift/latest/mgmt/connecting-using-workbench.html)
    * [cascading-jdbc](https://github.com/Cascading/cascading-jdbc)
        * $ `cd ./[PATH]/[TO]/cascading-jdbc`
        * $ `gradle build -Dcascading.jdbc.url.redshift='jdbc:postgresql://[REDSHIFT_HOST]/[REDSHIFT_DB]?user=[USERNAME]&password=[PASSWORD]' -i`
* [Configure IAM Roles for Amazon EMR](http://docs.aws.amazon.com/ElasticMapReduce/latest/DeveloperGuide/emr-iam-roles-creatingroles.html#emr-iam-roles-createdefaultwithcli)
    * $ `aws emr create-default-roles` [IAM Roles](http://aws.amazon.com/iam/) [doc](https://groups.google.com/forum/#!msg/snowplow-user/R9q1Jzpj3sw/CqoMCGRxvyUJ)
    * Attach policies to the certain user
* Run programs on AWS Elastic MapReduce and Redshift
    * $ `cd [path]/[to]/backend-cascading-yelpdataset`    
    * setup global variables [REDSHIFT_URL] [REDSHIFT_USER] [REDSHIFT_PASSWORD] [S3_BUCKET] [AWS_ACCESS_KEY] [AWS_SECRET_KEY] [HHY_BACKEND_PATH] in ~/.bash_profile
    * $ `awsscripts/awsdownloaddata.sh`  
        * [Download Data](http://docs.cascading.org/tutorials/cascading-aws/prerequisites.html)     
    * $ `awsscripts/awsemr.sh`  
        * [aws configure](http://docs.aws.amazon.com/cli/latest/userguide/cli-chap-getting-started.html#cli-installing-specifying-region)
* Monitoring processes 
    * [AWS S3](https://console.aws.amazon.com/s3)
        * $ `aws s3 ls`  // list S3 buckets
        * $ `aws s3 sync s3://hhyaws-s3-bucket/logs/i-adfaOEH s3logs/`  
            * [BUCKET]/logs
            * [BUCKET]/output
    * [AWS EMR console](https://console.aws.amazon.com/elasticmapreduce/home) 
    * [Driven](https://driven.cascading.io/index.html)
    * [Redshift](http://aws.amazon.com/redshift/)
    * View new tables in Redshift using SQLWorkbenchJ


##### [Setup Hadoop Virtual Cluster with Vagrant](http://java.dzone.com/articles/setting-hadoop-virtual-cluster)

* [Demo: Data processing with Apache HBase via Cascading Lingual](http://docs.cascading.org/tutorials/lingual-hbase/)   

```
> git clone https://github.com/Cascading/vagrant-cascading-hadoop-cluster.git
> cd vagrant-cascading-hadoop-cluster
> vagrant box add cascading-hadoop-base http://files.vagrantup.com/precise64.box
> vagrant up
> vagrant ssh master
(master) sudo hadoop namenode -format -force
(master) sudo start-all.sh
# give it some time here to start all services up
(master) sudo start-hbase.sh
```

