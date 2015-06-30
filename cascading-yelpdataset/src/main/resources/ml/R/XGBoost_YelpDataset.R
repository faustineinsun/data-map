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

# delete BusinessId column
train[, BusinessId := NULL] #train = train[,-1]
test[, BusinessId := NULL]
allData[, BusinessId := NULL]

# get the last column for labeling records
nameLastCol <- names(train)[ncol(train)]
y <- train[, nameLastCol, with = F][[1]] %>% as.matrix

# Delete the last column from training dataset
train[, nameLastCol:=NULL, with = F]
allData[, nameLastCol:=NULL, with = F]

# get the matrices
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

# Before the learning, cross validation is used to evaluate the error rate
# Find if the error rate is low (like train-mlogloss:0.421538 test-mlogloss:0.321538) on the test dataset
cv.nround <- 10
cv.nfold <- 10
bst.cv = xgb.cv(param=param, data = trainMatrix, label = y, nfold = cv.nfold, nrounds = cv.nround)

# Now let's train the real model
nround = 200 # build a model made of nround trees
bst = xgboost(param=param, data = trainMatrix, label = y, nrounds=nround)

##### Model understanding

### Feature importance
# Not all splits are equally important
# the first tree will do the big work and 
# the following trees will focus on the remaining, 
# on the parts not correctly learned by the previous trees
model <- xgb.dump(bst, with.stats = T)
model[1:10]
# Get the feature's real names
names <- dimnames(trainMatrix)[[2]]
# Compute feature importance matrix
importance_matrix <- xgb.importance(names, model = bst)
# get graph
xgb.plot.importance(importance_matrix[1:10,])

### Tree graph
# just displaying the first n_first_tree trees
xgb.plot.tree(feature_names = names, model = bst, n_first_tree = 3)


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

