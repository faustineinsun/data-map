# https://github.com/dmlc/xgboost
# xgboost: Large-scale and Distributed Gradient Boosting (GBDT, GBRT or GBM) Library, on single node, hadoop yarn and more.

rm(list = ls())

##### install packages
#install.packages("Ckmeans.1d.dp")
#install.packages("DiagrammeR")
#devtools::install_github('dmlc/xgboost',subdir='R-package')
#install.packages("XML", repos = "http://www.omegahat.org/R");

##### Preparation of the data
# https://github.com/dmlc/xgboost/blob/master/demo/kaggle-otto/otto_train_pred.R
# magrittr and data.table are here to make the code cleaner and much more rapid
require(data.table)
require(magrittr)
require(xgboost)
require(methods)

library(pmml)

dataDir <- "/Users/feiyu/workspace/data-map/cascading-yelpdataset/src/main/resources/ml/dataset/yelp-dataset/"
train <- fread(paste(dataDir, "business_categories_maxCount_train.csv", sep=""), header = T, stringsAsFactors = F)
test <- fread(paste(dataDir, "business_categories_maxCount_test.csv", sep=""), header=T, stringsAsFactors = F)
allData <- fread(paste(dataDir, "business_categories_maxCount_all.csv", sep=""), header=T, stringsAsFactors = F)

testBusinessId = test$BusinessId
allBusinessId = allData$BusinessId
# Delete ID column in training dataset
train[, BusinessId := NULL]
#train = train[,-1]
# Delete ID column in testing dataset
test[, BusinessId := NULL]
#test = test[,-1]
allData[, BusinessId := NULL]

# Check the content of the last column
#train[1:6, ncol(train), with  = F]
# Save the name of the last column
nameLastCol <- names(train)[ncol(train)]
y <- train[, nameLastCol, with = F][[1]] %>% as.matrix
# Display the first 5 levels
#y[1:5]

# Delete the last(target) column from training dataset
train[, nameLastCol:=NULL, with = F]
allData[, nameLastCol:=NULL, with = F]

trainMatrix <- train[,lapply(.SD,as.numeric)] %>% as.matrix
testMatrix <- test[,lapply(.SD,as.numeric)] %>% as.matrix
allMatrix <- allData[,lapply(.SD,as.numeric)] %>% as.matrix

##### Model training
numberOfClasses <- max(y) + 1
#set.seed(20739) 
param <- list("objective" = "multi:softprob",
              "eval_metric" = "mlogloss",
              "num_class" = numberOfClasses,
              "eta" = 0.6,
              "nthread" = 32)

# Before the learning we will use the cross validation to evaluate the our error rate
# Run Cross Valication
cv.nround <- 10
cv.nfold <- 10
bst.cv = xgb.cv(param=param, data = trainMatrix, label = y, nfold = cv.nfold, nrounds = cv.nround)
# As we can see the error rate is low on the test dataset (for a 5mn trained model). 

# Finally, we are ready to train the real model!!!
nround = 200 # build a model made of 50 trees
bst = xgboost(param=param, data = trainMatrix, label = y, nrounds=nround)

##### Model understanding

### Feature importance
# Not all splits are equally important
# the first tree will do the big work and the following trees will focus on the remaining, on the parts not correctly learned by the previous trees
model <- xgb.dump(bst, with.stats = T)
model[1:10]
# Get the feature real names
names <- dimnames(trainMatrix)[[2]]
# Compute feature importance matrix
importance_matrix <- xgb.importance(names, model = bst)
# Nice graph
xgb.plot.importance(importance_matrix[1:10,])

### Tree graph
# just displaying the first two trees here.
xgb.plot.tree(feature_names = names, model = bst, n_first_tree = 2)


##### predict testing data
# Make prediction
pred = predict(bst,allMatrix)
pred = matrix(pred,numberOfClasses,length(pred)/numberOfClasses)
pred = t(pred)

# Output submission
pred = format(pred, digits=2,scientific=F) # shrink the size of submission
pred = data.frame(allBusinessId,pred)

names(pred) = c('BusinessId', paste0('DayInWeek_',0:(numberOfClasses-1)))
write.csv(pred,file=paste(dataDir, "XGBoostModel_yelp_all.txt", sep=""), quote=FALSE,row.names=FALSE)

##### export XGBoost model to PMML
#saveXML(pmml(bst),file=paste(dataDir, "XGBoostModel_yelpChallenge.xml", sep="/"))
# no applicable method for 'pmml' applied to an object of class "xgb.Booster"
# https://github.com/cran/pmml/tree/master/R
# https://github.com/dmlc/xgboost/issues/196

