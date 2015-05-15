##### On terminal

```
$ gradle tasks
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

##### Run it on Amazon AWS Elastic MapReduce service later  

