# reference http://0xdata.com/blog/2014/09/r-h2o-domino/#tutorial3
# install.packages("h2o")
# http://h2o.ai/blog/2015/02/deep-learning-performance/

# 4 hidden layers
#MSE: (Extract with `h2o.mse`) 0.604482
#R^2: (Extract with `h2o.r2`) 0.8091318
#Logloss: (Extract with `h2o.logloss`) 1.673903

rm(list = ls())
setwd("/Users/feiyu/workspace/data-map/cascading-yelpdataset/src/main/resources/ml/R")
library(h2o)
localH2O <- h2o.init(nthread = 256, max_mem_size = "4g")

output <- read.csv("../dataset/yelp-dataset/yelp_h2oDeepLearning_sampleOutput.txt")
train <- read.csv("../dataset/yelp-dataset/business_categories_maxCount_train.csv",colClasses="factor")
test <- read.csv("../dataset/yelp-dataset/business_categories_maxCount_all.csv")
#output <- read.csv("../dataset/yelp-dataset/sampleSubmission.csv")
#train <- read.csv("../dataset/yelp-dataset/train.csv")
#test <- read.csv("../dataset/yelp-dataset/test.csv")

nColOutput = ncol(output)
nColTrainWithoutLabel = ncol(train) -1
nomalizeValueLamda = 0.375
nIt = 25

output[,2:nColOutput] <- 0
test <- test[, 1:nColTrainWithoutLabel]

for(i in 2:nColTrainWithoutLabel){
  train[,i] <- as.numeric(train[,i])
  train[,i] <- sqrt(train[,i]+nomalizeValueLamda)
  test[,i] <- as.numeric(test[,i])
  test[,i] <- sqrt(test[,i]+nomalizeValueLamda)
}

train.hex <- as.h2o(localH2O,train)
test.hex <- as.h2o(localH2O,test[,2:nColTrainWithoutLabel])
predictors <- 2:(ncol(train.hex)-1)
response <- ncol(train.hex)

for(i in 1:nIt){
  print(i)
  model <- h2o.deeplearning(x=predictors,
                            y=response,
                            training_frame=train.hex,
                            activation="RectifierWithDropout",
                            classification_stop = -1,
                            hidden=c(1024,526,1024,526),
                            hidden_dropout_ratio=c(0.5,0.5,0.5,0.5),
                            input_dropout_ratio=0.05,
                            epochs=50,
                            l1=1e-5,
                            l2=1e-5,
                            rho=0.99,
                            epsilon=1e-8,
                            train_samples_per_iteration=2000,
                            max_w2=10,
                            seed=1)
  output[,2:nColOutput] <- output[,2:nColOutput] + as.data.frame(h2o.predict(model,test.hex))[,2:nColOutput]
  write.csv(output,file="../dataset/yelp-dataset/yelp_h2oDeepLearning_all_benchmark.txt",quote=FALSE,row.names=FALSE) 
}      