CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE country (
    countrycode CHAR(2) PRIMARY KEY,
    cityname VARCHAR(100) NOT NULL
);

CREATE TABLE city (
    postalcode VARCHAR(6) NOT NULL,
    countryname VARCHAR(100) NOT NULL,
    countrycode CHAR(2) REFERENCES country(countrycode) ON DELETE RESTRICT,
    PRIMARY KEY (postalcode, countrycode)
);

CREATE TABLE client (
    clientuuid UUID PRIMARY KEY,
    username VARCHAR(30) UNIQUE NOT NULL CHECK (username ~ '^[A-Za-z_][A-Za-z0-9._]*$'),
    email VARCHAR(255) UNIQUE NOT NULL,
    datejoined DATE NOT NULL DEFAULT CURRENT_DATE,
    firstname VARCHAR(30) NOT NULL,
    lastname VARCHAR(30),
    phonenumber VARCHAR(20) DEFAULT NULL,
    provider VARCHAR(50),
    providerId VARCHAR(255),
    reportcount SMALLINT DEFAULT 0,
    role VARCHAR(50) DEFAULT 'user'
);

CREATE TABLE seller (
    selleruuid UUID NOT NULL REFERENCES client(clientuuid),
    subscriptionstartdatetime TIMESTAMP NOT NULL DEFAULT NOW(),
    subscriptionenddatetime TIMESTAMP NOT NULL DEFAULT (NOW() + '1 YEAR'::INTERVAL),
    autorenewal BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (selleruuid, subscriptionstartdatetime)
);

CREATE TABLE report (
    reportuuid UUID PRIMARY KEY,
    selleruuid UUID NOT NULL REFERENCES client(clientuuid), -- reporter
    clientuuid UUID NOT NULL REFERENCES client(clientuuid), -- reportee
    reportdatetime TIMESTAMP NOT NULL DEFAULT NOW(),
    reason TEXT NOT NULL,
    adminuuid UUID NOT NULL REFERENCES client(clientuuid), -- reviewer
    reviewdatetime TIMESTAMP DEFAULT NULL,
    outcome BOOLEAN DEFAULT FALSE,
    UNIQUE (clientuuid, selleruuid, reportdatetime)
);

CREATE TABLE suspension (
    suspensionuuid UUID PRIMARY KEY,
    clientuuid UUID NOT NULL REFERENCES client(clientuuid), -- suspendee
    suspensionstartdatetime TIMESTAMP UNIQUE NOT NULL,
    suspensionlength SMALLINT NOT NULL, -- -1 = permaban
    UNIQUE (clientuuid, suspensionstartdatetime)
);

CREATE TABLE listing (
    listinguuid UUID PRIMARY KEY,  
    selleruuid UUID NOT NULL REFERENCES client(clientuuid) ON DELETE CASCADE,
    title VARCHAR(150) NOT NULL,
    description TEXT DEFAULT NULL,
    posteddatetime TIMESTAMP NOT NULL DEFAULT NOW(),
    availabilityperiodstart TIMESTAMP DEFAULT NULL,
    availabilityperiodend TIMESTAMP DEFAULT NULL,
    minimumrentaldays INT DEFAULT 1,
    priceperminimumperiod NUMERIC(7,2) NOT NULL CHECK (pricePerMinimumPeriod >= 0),
    season VARCHAR(50) DEFAULT NULL,
    equipmenttype VARCHAR(50) NOT NULL,
    equipmentcondition VARCHAR(50) NOT NULL
);

CREATE TABLE address (
    addressuuid UUID PRIMARY KEY,
    listingaddressuuid UUID UNIQUE NOT NULL REFERENCES listing(listinguuid) ON DELETE CASCADE,
    coordinates GEOMETRY(POINT) NOT NULL,
    streetname VARCHAR(255) NOT NULL,
    streetnumber VARCHAR(10) NOT NULL CHECK (streetnumber ~ '^[0-9]+[A-Za-z]?$'),
    aptnumber VARCHAR(10) DEFAULT NULL,
    listingpostalcode VARCHAR(15) NOT NULL,
    listingcountrycode VARCHAR(10) NOT NULL,
    FOREIGN KEY (listingpostalcode, listingcountrycode) REFERENCES city (postalcode, countrycode) ON DELETE RESTRICT
);

CREATE TABLE rental (
    rentaluuid UUID PRIMARY KEY,
    clientuuid UUID NOT NULL REFERENCES client(clientuuid) ON DELETE CASCADE,
    listinguuid UUID NOT NULL REFERENCES listing(listinguuid) ON DELETE CASCADE,
    selleruuid UUID NOT NULL REFERENCES client(clientuuid) ON DELETE CASCADE,
    rentingStartDateTime TIMESTAMP NOT NULL,
    rentingEndDateTime TIMESTAMP NOT NULL,
    rating SMALLINT CHECK (rating BETWEEN 1 AND 5) DEFAULT NULL,
    review TEXT DEFAULT NULL,
    UNIQUE (clientuuid, listinguuid, selleruuid, rentingStartDateTime)
);

CREATE TABLE image (
    imageuuid UUID PRIMARY KEY,
    filename VARCHAR NOT NULL,
    content BYTEA NOT NULL,
    listinguuid UUID REFERENCES listing(listinguuid) ON DELETE CASCADE,
    clientuuid UUID NOT NULL REFERENCES client(clientuuid) ON DELETE CASCADE
);