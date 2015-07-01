#install.packages('randomForest')

rm(list = ls())

library(randomForest)
library(pmml)

# get data
dataDir <- "/Users/feiyu/workspace/data-map/cascading-yelpdataset/src/main/resources/ml/dataset/yelp-dataset/"
df_train <- read.csv(paste(dataDir, "business_categories_maxCount_train.csv", sep=""))
df_test <- read.csv(paste(dataDir, "business_categories_maxCount_test.csv", sep=""))
allData <- read.csv(paste(dataDir, "business_categories_maxCount_all.csv", sep=""))

train = df_train[, -1]
numberOfClasses = 7

# train model

# To set the same random number labeled 931 
# so that the program will produce the same result when it runs each time
set.seed(931) 

fit <- randomForest(as.factor(MaxCheckinDayInWeek)~., data=train, importance=TRUE, ntree=100)

# plot model result
varImpPlot(fit)

# predict
predict <- predict(fit,allData,type="prob")
result <- data.frame(id = allData$BusinessId, predict)

names(result) = c('BusinessId', paste0('DayInWeek_',0:(numberOfClasses-1)))
# save result to file
write.csv(result, file = paste(dataDir, "yelp_RandomForest_all.txt", sep=""), quote=FALSE,row.names=FALSE)

# get pmml file
saveXML(pmml(fit), file="/Users/feiyu/workspace/data-map/cascading-yelpdataset/src/main/resources/ml/rf_yelpChallenge.xml")

