CREATE TABLE reference_Category
(
    categoryId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    categoryCode varchar(20) NOT NULL unique,
    name varchar(20) NOT NULL,
    active bit not null,
    note varchar(100) NULL,
    utimestamp datetime DEFAULT current_timestamp()
)

CREATE TABLE reference_CourseType
(
    courseTypeId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    courseTypeCode varchar(20) NOT NULL unique,
    name varchar(20) NOT NULL,
    active bit not null,
    note varchar(100) NULL,
    utimestamp datetime DEFAULT current_timestamp()
)

CREATE TABLE reference_ClassStatus
(
    ClassStatusId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ClassStatusCode varchar(20) NOT NULL unique,
    name varchar(20) NOT NULL,
    active bit not null,
    note varchar(100) NULL,
    utimestamp datetime DEFAULT current_timestamp()
)

CREATE TABLE reference_refscheduleStatus
(
    scheduleStatusId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    scheduleStatusCode varchar(20) NOT NULL unique,
    name varchar(20) NOT NULL,
    active bit not null,
    note varchar(100) NULL,
    utimestamp datetime DEFAULT current_timestamp()
)

CREATE TABLE reference_instructorType
(
    instructorTypeId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    instructorTypeCode varchar(20) NOT NULL unique,
    name varchar(20) NOT NULL,
    active bit not null,
    note varchar(100) NULL,
    utimestamp datetime DEFAULT current_timestamp()
)

CREATE TABLE reference_CourseLevel
(
    courseLevelId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    courseLevelCode varchar(20) NOT NULL,
    name varchar(20) NOT NULL,
    active bit not null,
    note varchar(100) NULL,
    utimestamp datetime DEFAULT current_timestamp()
)


CREATE TABLE training_Course
(
    courseId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uuid varchar(200) not null,
    categoryCode varchar(20) NOT NULL,
    title text DEFAULT NULL,
    courseTypeCode varchar(20) NOT NULL,
    duaration int not NULL,
    description longtext DEFAULT NULL,
    courseLevelCode varchar(255) DEFAULT NULL,
    image varchar(255) DEFAULT NULL,
    video varchar(255) DEFAULT NULL,
    statusCode varchar(20) NOT NULL,
    utimestamp datetime DEFAULT current_timestamp()

);

CREATE TABLE training_Class
(
    classId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    courseId int not null,
    startDate date NOT NULL,
    endDate date NOT NULL,
    progress_date date DEFAULT NULL,
    price DECIMAL(13,2) DEFAULT null,
    time time DEFAULT NULL,
    zoomUrl text DEFAULT NULL,
    classStatusCode varchar(20) NOT NULL,
    utimestamp datetime DEFAULT current_timestamp()
);

CREATE TABLE training_ClassInstructor
(
    ClassInstructorId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    classId int not null,
    userId int not null,
    instructorTypeCode varchar(20) NOT NULL,
    utimestamp datetime DEFAULT current_timestamp()
    );


CREATE TABLE training_ClassSchedule
(
    ClassScheduleId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    classId int not null,
    classDate  date not NULL,
    scheduleStatusCode varchar(20) NOT NULL,
    actualStartTime time DEFAULT NULL,
    actualDuration int DEFAULT NULL,
    note text DEFAULT NULL,
    utimestamp datetime DEFAULT current_timestamp()
    );

