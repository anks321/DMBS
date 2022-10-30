show databases;
drop database mess;
create database mess;
use mess;


select * from customer;
select * from student;
insert into Mess values (2,NULL,200,"lanka","sariya");
insert into section values(2,2,1,"breakfast","lunch","dinner");
insert into employees values("adfa","adfh","sadf","asdfas",2,123,2,2,1,1,1,123,'1999-11-11',"asdf","asdf","234","firstn","adfa","cook","saf@asdf","asfd","asdf");
update Mess set Head_id = 123 where mess_id=2;

CREATE TABLE IF NOT EXISTS student
(
    roll_no INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    role VARCHAR(255),
    token VARCHAR(255),
    active INT,
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
    Mess_Id int,
    section_id int,
    constraint pk_3 primary key(roll_no)
);

Create table IF NOT EXISTS Mess(
    mess_id int  NOT NULL, 
    Head_id int,
    mess_no INT,
    m_name VARCHAR(255),
    hostel_name VARCHAR(255), 
    constraint pk_2 primary key(mess_id)
);

Create table IF NOT EXISTS Section(
    mess_id int NOT NULL, 
    section_id INT NOT NULL,
    hall_no INT,
    breakfast VARCHAR(255),
    lunch VARCHAR(255),
    dinner VARCHAR(255),
    constraint pk_1 primary key(mess_id,section_id)
);

Create table IF NOT EXISTS employees(
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    role VARCHAR(255),
    token VARCHAR(255),
    active INT,
    eid int NOT NULL,
    mess_id int,
    section_id int,
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
    constraint pk_4 primary key (eid),
    constraint fk_3 foreign key(mess_Id,section_id) references Section(mess_Id,section_id)
);


Create table IF NOT EXISTS customer(
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    role VARCHAR(255),
    token VARCHAR(255),
    active INT,
    cid int NOT NULL,
    mess_id int,
    section_id int, 
    balance INT,
    pin INT,
    phone_no VARCHAR(255),
    c_aadhar_number VARCHAR(255),
    account_no VARCHAR(255),
    sex VARCHAR(255),
    ifsc VARCHAR(255),
    dob VARCHAR(255),
    email VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    city VARCHAR(255),
    street VARCHAR(255),
    constraint pk_5 primary key (cid),
    constraint fk_4 foreign key(mess_Id,section_id) references Section(mess_Id,section_id)
);




Alter table Section 
add constraint fk_1 foreign key (Mess_Id) references Mess(Mess_Id);
Alter table student
add constraint fk_2 foreign key(Mess_Id,Section_id) references Section(Mess_Id,Section_id);
Alter table Mess 
add constraint fk_7 foreign key (Head_id) references Employees(eid);


use mess;
drop table forum;


Create table IF NOT EXISTS forum(
    C_id int  NOT NULL auto_increment,
    date_time VARCHAR(255),
    roll_no INT,
    resolved INT,
   
    complaint VARCHAR(255),
    constraint pk_6 primary key(C_id),
    constraint fk_5 foreign key (roll_no) references student(roll_no)
);

Create table IF NOT EXISTS Announcements(
    A_id int NOT NULL,
    mess_id int,
    section_id int,
    Date_time datetime,
    announce_text VARCHAR(255),
    date_and_time VARCHAR(255),
    constraint pk_7 primary key(A_id),
    constraint fk_6 foreign key (mess_id,section_id) references Section(mess_id,section_id)
);
insert into Announcements values(1,2,2,12/12/2002,"announcementssss","12232010");
insert into Announcements values(2,2,2,12/12/2002,"announce 2","12232"); 
Insert into questions values (1,2,2,"adfa","afaag","How are you?");
Insert into options  values(1,1,"Good");
Insert into options  values(2,1,"Not Good");


Create table Inventory(
    item_id int NOT NULL,
    mess_id int,
    section_id int,
    quantity INT,
    cost INT,
    expiry_date DATE,
    name VARCHAR(255),
    constraint pk_8 primary key(item_id),
    constraint fk_8 foreign key(mess_id,section_id) references Section(mess_Id,section_id)
);

Create table IF NOT EXISTS Transactions(
    t_id int NOT NULL,
    roll_no int,
    C_id int,
    amount INT,
    type INT,
    date DATE,
    mode_of_payment VARCHAR(255),
    constraint pk_9 primary key (t_id),
    constraint fk_9 foreign key(roll_no) references student(roll_no),
    constraint fk_10 foreign key(C_id) references customer(cid)
);

Create table IF NOT EXISTS Questions(
    questionid INT NOT NULL,
    mess_id int,
    section_id int,
    StartTime VARCHAR(255),
    EndTime VARCHAR(255),
    text VARCHAR(255),
    constraint pk_11 primary key (questionid),
    constraint fk_11 foreign key(mess_id,section_id) references Section(mess_Id,section_id)
);

Create table IF NOT EXISTS Options(
    optionid int,
    Q_id int NOT NULL,
    OptionText VARCHAR(255),
    constraint pk_12 primary key(Q_id,optionid),
    constraint fk_12 foreign key(Q_id) references Questions(questionid)
);

Create table IF NOT EXISTS Opt_Student(
    roll_no int NOT NULL,
    Q_id int NOT NULL,
    Opt_id int NOT NULL,
    constraint pk_13 primary key (roll_no,Q_id,Opt_id),
    constraint fk_13 foreign key (roll_no) references student(roll_no),
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
    constraint fk_23 foreign key (Rollno) references student(roll_no)
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
    constraint fk_21 foreign key (Cu_id) references customer(cid)
);

Create table IF NOT EXISTS mess_phn(
    Mess_id int NOT NULL,
    phn varchar(20) NOT NULL,
    constraint pk_21 primary key(Mess_id , phn),
    constraint fk_20 foreign key (Mess_id) references Mess(Mess_id)
);






