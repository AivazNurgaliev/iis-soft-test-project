DROP TABLE IF EXISTS Employees CASCADE;

CREATE TABLE Employees (
    ID int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    DepCode varchar(20) NOT NULL,
    DepJob varchar(100) NOT NULL,
    Description varchar(255) NULL,
    CONSTRAINT Unique_DepCode_And_DepJob UNIQUE (DepCode, DepJob)
);


INSERT INTO Employees (DepCode, DepJob, Description) VALUES ('HR', 'Manager', 'Hiring manager');
INSERT INTO Employees (DepCode, DepJob, Description) VALUES ('IT', 'Developer', 'Software development and support');
INSERT INTO Employees (DepCode, DepJob, Description) VALUES ('IT', 'Analyst', 'Data analytics');
INSERT INTO Employees (DepCode, DepJob, Description) VALUES ('IT', 'Tester', null);
INSERT INTO Employees (DepCode, DepJob, Description) VALUES ('IT', 'System Administrator', 'System administration tasks');
INSERT INTO Employees (DepCode, DepJob, Description) VALUES ('MK', 'Designer', 'Design development');
INSERT INTO Employees (DepCode, DepJob, Description) VALUES ('MK', 'Copywriter', 'Content writing');
INSERT INTO Employees (DepCode, DepJob, Description) VALUES ('FN', 'Accountant', 'Auditing and accounting');
INSERT INTO Employees (DepCode, DepJob, Description) VALUES ('PR', 'Engineer', 'New product development and testing');
INSERT INTO Employees (DepCode, DepJob, Description) VALUES ('PR', 'Technician', 'Assisting the engineer');
INSERT INTO Employees (DepCode, DepJob, Description) VALUES ('AD', 'Receptionist', 'Reception');
