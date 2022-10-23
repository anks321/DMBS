show databases;
drop database mess;
create database mess;
use mess;

CREATE TABLE IF NOT EXISTS students
(
    roll_no INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL,
    active INT NOT NULL,
    Age INT NOT NULL,
    room_no INT NOT NULL,
    Balance INT NOT NULL,
    DOB DATE NOT NULL,
    f_name VARCHAR(255) NOT NULL,
    l_name VARCHAR(255) NOT NULL,
    hostel_name VARCHAR(255) NOT NULL,
    sex VARCHAR(255) NOT NULL,
    parent VARCHAR(255) NOT NULL,
    phone_no VARCHAR(255) NOT NULL,
    s_email VARCHAR(255) NOT NULL,
    localGaurdian VARCHAR(255) NOT NULL,
    aadhar_no VARCHAR(255) NOT NULL,
    s_account_no VARCHAR(255) NOT NULL,
    s_ifsc VARCHAR(255) NOT NULL,
    Mess_Id int NOT NULL,
    section_id int NOT NULL,
    constraint pk_3 primary key(roll_no)
);

Create table IF NOT EXISTS Mess(
    mess_id int  NOT NULL, 
    Head_id int NOT NULL,
    mess_no INT NOT NULL,
    m_name VARCHAR(255) NOT NULL,
    hostel_name VARCHAR(255) NOT NULL, 
    constraint pk_2 primary key(mess_id)
);

Create table IF NOT EXISTS Section(
    mess_id int NOT NULL, 
    section_id INT NOT NULL,
    hall_no INT NOT NULL,
    breakfast VARCHAR(255) NOT NULL,
    lunch VARCHAR(255) NOT NULL,
    dinner VARCHAR(255) NOT NULL,
    constraint pk_1 primary key(mess_id,section_id)
);

Create table IF NOT EXISTS employees(
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL,
    active INT NOT NULL,
    eid int NOT NULL,
    mess_id int NOT NULL,
    section_id int NOT NULL,
    salary INT NOT NULL,
    age INT NOT NULL,
    phone_no INT NOT NULL,
    pin INT NOT NULL,
    dob DATE NOT NULL,
    ifsc VARCHAR(255) NOT NULL,
    account_no VARCHAR(255) NOT NULL,
    e_aadhar_number VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    Designation VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    constraint pk_4 primary key (eid),
    constraint fk_3 foreign key(mess_Id,section_id) references Section(mess_Id,section_id)
);

Create table IF NOT EXISTS customers(
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL,
    active INT NOT NULL,
    cid int NOT NULL,
    mess_id int NOT NULL,
    section_id int NOT NULL, 
    balance INT NOT NULL,
    pin INT NOT NULL,
    phone_no INT NOT NULL,
    c_aadhar_number VARCHAR(255) NOT NULL,
    account_no VARCHAR(255) NOT NULL,
    sex VARCHAR(255) NOT NULL,
    ifsc VARCHAR(255) NOT NULL,
    dob VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    constraint pk_5 primary key (cid),
    constraint fk_4 foreign key(mess_Id,section_id) references Section(mess_Id,section_id)
);


Alter table Section 
add constraint fk_1 foreign key (Mess_Id) references Mess(Mess_Id);
Alter table students
add constraint fk_2 foreign key(Mess_Id,Section_id) references Section(Mess_Id,Section_id);
Alter table Mess 
add constraint fk_7 foreign key (Head_id) references Employees(eid);

Create table IF NOT EXISTS forum(
    C_id int  NOT NULL,
    date_time VARCHAR(255) NOT NULL,
    roll_no INT NOT NULL,
    resolved INT NOT NULL,
    expiry_date DATE NOT NULL,
    complaint VARCHAR(255) NOT NULL,
    constraint pk_6 primary key(C_id),
    constraint fk_5 foreign key (roll_no) references students(roll_no)
);

Create table IF NOT EXISTS Announcements(
    A_id int NOT NULL,
    mess_id int NOT NULL,
    section_id int NOT NULL,
    Date_time datetime NOT NULL,
    text VARCHAR(255) NOT NULL,
    date_and_time VARCHAR(255) NOT NULL,
    constraint pk_7 primary key(A_id),
    constraint fk_6 foreign key (mess_id,section_id) references Section(mess_id,section_id)
);

Create table Inventory(
    item_id int NOT NULL,
    mess_id int NOT NULL,
    section_id int NOT NULL,
    quantity INT NOT NULL,
    cost INT NOT NULL,
    expiry_date DATE NOT NULL,
    name VARCHAR(255) NOT NULL,
    constraint pk_8 primary key(item_id),
    constraint fk_8 foreign key(mess_id,section_id) references Section(mess_Id,section_id)
);

Create table IF NOT EXISTS Transactions(
    t_id int NOT NULL,
    roll_no int NOT NULL,
    C_id int NOT NULL,
    amount INT NOT NULL,
    type INT NOT NULL,
    date DATE NOT NULL,
    mode_of_payment VARCHAR(255) NOT NULL,
    constraint pk_9 primary key (t_id),
    constraint fk_9 foreign key(roll_no) references students(roll_no),
    constraint fk_10 foreign key(C_id) references customers(cid)
);

Create table IF NOT EXISTS Questions(
    questionid INT NOT NULL,
    mess_id int NOT NULL,
    section_id int NOT NULL,
    StartTime VARCHAR(255) NOT NULL,
    EndTime VARCHAR(255) NOT NULL,
    text VARCHAR(255) NOT NULL,
    constraint pk_11 primary key (questionid),
    constraint fk_11 foreign key(mess_id,section_id) references Section(mess_Id,section_id)
);

Create table IF NOT EXISTS Options(
    optionid int NOT NULL,
    Q_id int NOT NULL,
    OptionText VARCHAR(255) NOT NULL,
    constraint pk_12 primary key(Q_id,optionid),
    constraint fk_12 foreign key(Q_id) references Questions(questionid)
);

Create table IF NOT EXISTS Opt_Student(
    roll_no int NOT NULL,
    Q_id int NOT NULL,
    Opt_id int NOT NULL,
    constraint pk_13 primary key (roll_no,Q_id,Opt_id),
    constraint fk_13 foreign key (roll_no) references students(roll_no),
    constraint fk_14 foreign key (Q_id,Opt_id) references Options(Q_id,optionid)
);

Create table IF NOT EXISTS supervisor(
    E_id int NOT NULL,
    Manager_id int NOT NULL, 
    constraint pk_14 primary key(E_id,Manager_id),
    constraint fk_15 foreign key (E_id) references employees(eid),
    constraint fk_16 foreign key (Manager_id) references employees(eid)
);

Create table  IF NOT EXISTS student_phn(
    Rollno int NOT NULL,
    phn varchar(20) NOT NULL,
    constraint pk_18 primary key(Rollno , phn),
    constraint fk_23 foreign key (Rollno) references students(roll_no)
);

Create table  IF NOT EXISTS emplo_phn(
    eid int NOT NULL,
    phn varchar(20) NOT NULL,
    constraint pk_19 primary key(eid , phn),
    constraint fk_22 foreign key (eid) references employees(eid)
);

Create table  IF NOT EXISTS custo_phn(
    Cu_id int NOT NULL,
    phn varchar(20) NOT NULL,
    constraint pk_20 primary key(Cu_id , phn),
    constraint fk_21 foreign key (Cu_id) references customers(cid)
);

Create table IF NOT EXISTS mess_phn(
    Mess_id int NOT NULL,
    phn varchar(20) NOT NULL,
    constraint pk_21 primary key(Mess_id , phn),
    constraint fk_20 foreign key (Mess_id) references Mess(Mess_id)
);






