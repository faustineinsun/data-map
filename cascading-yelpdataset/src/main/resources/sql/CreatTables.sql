DROP TABLE IF EXISTS Businesses, Categories, Checkin, CheckinPredicted, CheckinTimeWindow, Cities, Business_Category, Business_Checkin; 

CREATE TABLE IF NOT EXISTS Businesses(BusinessId VARCHAR(255) PRIMARY KEY, 
    Name TEXT, 
    FullAddress MEDIUMTEXT,
    City VARCHAR(255),
    State TINYTEXT,
    Latitude FLOAT,
    Longitude FLOAT,
    Category MEDIUMTEXT) 
    ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS Categories(Category VARCHAR(255) PRIMARY KEY) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS Business_Category(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	BusinessId VARCHAR(255), 
    State TINYTEXT,
	Category VARCHAR(255))
    ENGINE=InnoDB, 
    AUTO_INCREMENT=1;
CREATE TABLE IF NOT EXISTS Cities(City VARCHAR(255) PRIMARY KEY,
	State TINYTEXT) 
	ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Checkin(BusinessId VARCHAR(255) PRIMARY KEY, 
    CheckinTimeWindowArray MEDIUMTEXT,
    MaxCheckinCountDayInWeek VARCHAR(2))
    ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS CheckinPredicted(BusinessId VARCHAR(255) PRIMARY KEY, 
    CheckinTimeWindowArrayPredictedXGBoost MEDIUMTEXT,
    CheckinTimeWindowArrayPredictedRandomForest MEDIUMTEXT,
    CheckinTimeWindowArrayPredictedH2ODeepLearning MEDIUMTEXT)
    ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS CheckinTimeWindow(HourWeekTimeWindow VARCHAR(255) PRIMARY KEY) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS Business_Checkin(id INT NOT NULL AUTO_INCREMENT,
	HourWeekTimeWindow VARCHAR(255),
	Hour INT,
	Week INT,
	BusinessId VARCHAR(255), 
	Count INT,
	PRIMARY KEY (id,Hour))
PARTITION BY HASH(Hour)
PARTITIONS 24;
ALTER TABLE Business_Checkin
    ENGINE=InnoDB, 
    AUTO_INCREMENT=1;