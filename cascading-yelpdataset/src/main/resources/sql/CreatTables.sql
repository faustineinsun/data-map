DROP TABLE IF EXISTS Businesses, Categories, Checkin; 

CREATE TABLE IF NOT EXISTS Businesses(BusinessId VARCHAR(255) PRIMARY KEY, 
    Name TEXT, 
    FullAddress MEDIUMTEXT,
    City TEXT,
    State TINYTEXT,
    Latitude FLOAT,
    Longitude FLOAT) 
    ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS Categories(Category VARCHAR(255) PRIMARY KEY, 
	BusinessId VARCHAR(255))
    ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS Checkin(BusinessId VARCHAR(255) PRIMARY KEY, 
	CheckinInfo LONGTEXT)
    ENGINE=InnoDB;