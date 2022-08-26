DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS filemetadata;

CREATE TABLE customers
(
    customer_id        INT AUTO_INCREMENT PRIMARY KEY,
    customer_name      VARCHAR(250) ,
    booking_date       DATE         ,
    opportunity_id     VARCHAR(250) ,
    booking_type       VARCHAR(250) ,
    total              VARCHAR(250) ,
    account_executive  VARCHAR(250) ,
    sales_organization VARCHAR(250) ,
    team               VARCHAR(250) ,
    product            VARCHAR(250) ,
    renewable          VARCHAR(250)
);

CREATE TABLE filemetadata
(
    filemetadata_id INT AUTO_INCREMENT PRIMARY KEY,
    filename        VARCHAR(250) NOT NULL,
    creation_date   DATE         NOT NULL,
    size_file       VARCHAR(250) NOT NULL,
    upload_date     DATE         NOT NULL
);