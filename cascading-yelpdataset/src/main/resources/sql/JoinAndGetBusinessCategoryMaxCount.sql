SELECT Businesses.`BusinessId`, Businesses.`Category`, Checkin.`MaxCheckinCountDayInWeek`
FROM  Businesses Left JOIN Checkin
ON Businesses.`BusinessId` = Checkin.`BusinessId`;