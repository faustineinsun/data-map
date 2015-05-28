SELECT Businesses.*, Business_Category.`Category`, Business_checkin.`Count`, Business_checkin.`Hour`,  Business_checkin.`Week` 
FROM Business_Category INNER JOIN Businesses
ON Businesses.`BusinessId` = Business_Category.`BusinessId`
INNER JOIN Business_checkin
ON Businesses.`BusinessId` = Business_checkin.`BusinessId`
WHERE Business_Category.`State` = 'NV' AND Businesses.`City` = 'Las Vegas'
AND Business_Category.`Category`='Restaurants'
AND Business_checkin.`Week` = '3' AND Business_checkin.`Hour` = '18';

SELECT Businesses.*, Business_checkin.`Count`, Business_checkin.`Hour`,  Business_checkin.`Week` 
FROM  Businesses INNER JOIN Business_checkin
ON Businesses.`BusinessId` = Business_checkin.`BusinessId`
WHERE Businesses.`State` = 'NV'
AND Business_checkin.`Week` = '5' AND Business_checkin.`Hour` = '19';