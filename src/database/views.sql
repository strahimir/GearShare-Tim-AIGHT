CREATE VIEW available_listings AS

SELECT * FROM listing

WHERE listinguuid NOT IN

(SELECT listinguuid
	FROM rental
	WHERE review IS NULL
	AND CURRENT_TIMESTAMP BETWEEN rentingstartdatetime AND rentingenddatetime);