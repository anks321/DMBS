CREATE TABLE IF NOT EXISTS Student
(
    roll_no INT NOT NULL AUTO_INCREMENT,
    Age INT,
    room_no INT,
    Balance INT,
    DOB DATE,
    f_name VARCHAR(255),
    l_name VARCHAR(255),
    hostel_name VARCHAR(255),
    sex VARCHAR(255),
    parent VARCHAR(255),
    phone_no VARCHAR(255),
    s_email VARCHAR(255),
    localGaurdian VARCHAR(255),
    aadhar_no VARCHAR(255),
    s_account_no VARCHAR(255),
    s_ifsc VARCHAR(255),
    PRIMARY KEY (roll_no)
);

CREATE TABLE IF NOT EXISTS Employee
(
    eid INT NOT NULL AUTO_INCREMENT, 
    salary INT,
    age INT,
    phone_no INT,
    pin INT,
    dob DATE,
    ifsc VARCHAR(255),
    account_no VARCHAR(255),
    e_aadhar_number VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    Designation VARCHAR(255),
    email VARCHAR(255),
    city VARCHAR(255),
    street VARCHAR(255),
    PRIMARY KEY (eid)
);

CREATE TABLE IF NOT EXISTS Customer
(
    cid INT NOT NULL AUTO_INCREMENT, 
    balance INT,
    pin INT,
    phone_no INT,
    c_aadhar_number VARCHAR(255),
    account_no VARCHAR(255),
    sex VARCHAR(255),
    ifsc VARCHAR(255),
    dob VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    PRIMARY KEY (cid)
);

CREATE TABLE IF NOT EXISTS Mess
(
    mess_id INT NOT NULL AUTO_INCREMENT,
    phone_no INT,
    mess_no INT,
    m_name VARCHAR(255),
    hostel_name VARCHAR(255),
    PRIMARY KEY (mess_id)
);


CREATE TABLE IF NOT EXISTS Section
(
    section_id INT NOT NULL AUTO_INCREMENT,
    hall_no INT,
    testName VARCHAR(255),
    breakfast VARCHAR(255),
    lunch VARCHAR(255),
    dinner VARCHAR(255),
    PRIMARY KEY (section_id)
);

CREATE TABLE IF NOT EXISTS Transaction 
(
    t_id INT NOT NULL AUTO_INCREMENT,
    amount INT,
    type INT,
    date DATE,
    mode_of_payment VARCHAR(255),
    PRIMARY KEY (t_id),
);

CREATE TABLE IF NOT EXISTS Forum
(
    C_id INT NOT NULL AUTO_INCREMENT,
    roll_no INT,
    resolved INT,
    expiry_date DATE,
    complaint VARCHAR(255),
    date_time VARCHAR(255),
    PRIMARY KEY (C_id)
);

CREATE TABLE IF NOT EXISTS Inventory
(
    item_id INT NOT NULL AUTO_INCREMENT,
    quantity INT,
    date DATE,
    expiry_date DATE,
    name VARCHAR(255),
    PRIMARY KEY (item_id)
);

CREATE TABLE IF NOT EXISTS Announcements
(
    A_id INT NOT NULL AUTO_INCREMENT,
    text VARCHAR(255),
    date_and_time VARCHAR(255),
    PRIMARY KEY (A_id)
);

CREATE TABLE IF NOT EXISTS Questions
(
    questionid INT NOT NULL AUTO_INCREMENT,
    StartTime VARCHAR(255),
    EndTime VARCHAR(255),
    text VARCHAR(255),
    PRIMARY KEY (questionid)
);

CREATE TABLE IF NOT EXISTS payout
(
    questionid INT NOT NULL AUTO_INCREMENT,
    OptionText VARCHAR(255),
    PRIMARY KEY (questionid)
);








