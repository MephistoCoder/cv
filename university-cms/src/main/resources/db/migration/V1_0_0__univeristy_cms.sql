DROP TABLE IF EXISTS courses_students, students_groups, teachers_groups, teachers_courses,
						lessons_times, lessons_groups, lessons, audiences, users,
						students, persons, groups, courses, teachers, admins CASCADE;
DROP SEQUENCE IF EXISTS hibernate_sequences;
						
CREATE SEQUENCE IF NOT EXISTS hibernate_sequences
	START 1
	INCREMENT 1;
						
CREATE TABLE IF NOT EXISTS users 
(
	id serial NOT NULL,
	username varchar NOT NULL UNIQUE,
	password varchar NOT NULL,
	role integer NOT NULL,
	CONSTRAINT users_pkey PRIMARY KEY(id),
	UNIQUE(username)
);

CREATE TABLE IF NOT EXISTS persons (
	id serial,
	first_name character varying(20) NOT NULL,
	last_name character varying(20) NOT NULL,
	user_id integer NOT NULL,
	CONSTRAINT persons_pkey PRIMARY KEY(id),
	CONSTRAINT fk_user FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS audiences 
(
	id serial,
	name character varying(10) NOT NULL,
	CONSTRAINT audiences_pkey PRIMARY KEY (id),
	UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS groups
(
    id serial,
    name character varying(7) NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (id),
    CONSTRAINT name_regex_check CHECK (name ~ $$[A-Z]{2}-\d{2}/?\d?$$),
    UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS students
(
    CONSTRAINT students_pkey PRIMARY KEY (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
) INHERITS (persons);

CREATE TABLE IF NOT EXISTS teachers
(
    CONSTRAINT teachers_pkey PRIMARY KEY (id)
) INHERITS (persons);

CREATE TABLE IF NOT EXISTS admins
(
    CONSTRAINT admins_pkey PRIMARY KEY (id)
) INHERITS (persons);

CREATE TABLE IF NOT EXISTS courses
(
    id serial,
    name character varying(20) NOT NULL,
    description character varying(50),
    CONSTRAINT courses_pkey PRIMARY KEY (id),
    UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS lessons_times
(
    id serial,
    start_time time NOT NULL,
    end_time time NOT NULL,
    CONSTRAINT lessons_times_pkey PRIMARY KEY (id),
    CHECK(start_time < end_time)
);

CREATE TABLE IF NOT EXISTS courses_students
(
    course_id integer,
    student_id integer,
    CONSTRAINT courses_students_pkey PRIMARY KEY (student_id, course_id),
    CONSTRAINT fk_course FOREIGN KEY (course_id)
        REFERENCES courses (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_student FOREIGN KEY (student_id)
        REFERENCES students (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS students_groups (
	student_id integer,
	group_id integer,
	CONSTRAINT students_groups_pkey PRIMARY KEY (student_id, group_id),
	CONSTRAINT fk_student FOREIGN KEY (student_id)
		REFERENCES students (id) MATCH SIMPLE
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT fk_group FOREIGN KEY (group_id)
		REFERENCES groups (id) MATCH SIMPLE
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS teachers_groups (
	teacher_id integer,
	group_id integer,
	CONSTRAINT teachers_groups_pkey PRIMARY KEY (teacher_id, group_id),
	CONSTRAINT fk_teacher FOREIGN KEY (teacher_id)
		REFERENCES teachers (id) MATCH SIMPLE
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT fk_group FOREIGN KEY (group_id)
		REFERENCES groups (id) MATCH SIMPLE
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS teachers_courses (
	teacher_id integer,
	course_id integer,
	CONSTRAINT teachers_courses_pkey PRIMARY KEY (teacher_id, course_id),
	CONSTRAINT fk_teacher FOREIGN KEY (teacher_id)
		REFERENCES teachers (id) MATCH SIMPLE
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT fk_course FOREIGN KEY (course_id)
		REFERENCES courses (id) MATCH SIMPLE
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS lessons 
(
	id serial,
	date_ date NOT NULL,
	lesson_time_id integer,
	teacher_id integer,
	course_id integer,
	audience_id integer,
	CONSTRAINT lessons_pkey PRIMARY KEY (id),
	CONSTRAINT fk_lesson_time_id FOREIGN KEY (lesson_time_id)
		REFERENCES lessons_times (id) MATCH SIMPLE
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT fk_teacher FOREIGN KEY (teacher_id)
		REFERENCES teachers (id) MATCH SIMPLE
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT fk_course FOREIGN KEY (course_id)
		REFERENCES courses (id) MATCH SIMPLE
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT fk_audience FOREIGN KEY (audience_id)
		REFERENCES audiences (id) MATCH SIMPLE
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS lessons_groups 
(
	lesson_id integer,
	group_id integer,
	CONSTRAINT lessons_groups_pkey PRIMARY KEY (lesson_id, group_id),
	CONSTRAINT fk_lesson FOREIGN KEY (lesson_id)
		REFERENCES lessons (id) MATCH SIMPLE
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT fk_group FOREIGN KEY (group_id)
		REFERENCES groups (id) MATCH SIMPLE
		ON UPDATE CASCADE
		ON DELETE CASCADE 
);

-- create audiences
insert into audiences(id, name) values(1, 'ET-123');
insert into audiences(id, name) values(2, 'ET-133');
insert into audiences(id, name) values(3, 'ET-103');
insert into audiences(id, name) values(4, 'G-113');
insert into audiences(id, name) values(5, 'MAIN-322');
-- create courses
insert into courses(name, description) values('JavaSE', 'course of java standart edition');
insert into courses(name, description) values('History', 'course of history of Ukraine in XIX-XX centuries');
insert into courses(name, description) values('Math', 'course of high math');
insert into courses(name, description) values('Ukrainian', 'course of ukrainian language');
insert into courses(name, description) values('DataBases', 'course of basics of databases, based on Oracle SQL');
-- create groups
insert into groups(id, name) values(1, 'TE-22');
insert into groups(id, name) values(2, 'TE-22/1');
insert into groups(id, name) values(3, 'TE-22/2');
insert into groups(id, name) values(4, 'GT-21');
insert into groups(id, name) values(5, 'GT-21/1');
insert into groups(id, name) values(6, 'GT-21/2');
-- create times of lessons
insert into lessons_times(id, start_time, end_time) values(1, '8:20', '9:40');
insert into lessons_times(id, start_time, end_time) values(2, '9:50', '11:10');
insert into lessons_times(id, start_time, end_time) values(3, '11:20', '12:40');
insert into lessons_times(id, start_time, end_time) values(4, '13:20', '14:40');
insert into lessons_times(id, start_time, end_time) values(5, '14:50', '16:10');
insert into lessons_times(id, start_time, end_time) values(6, '16:20', '17:40');
insert into lessons_times(id, start_time, end_time) values(7, '17:50', '19:10');
insert into lessons_times(id, start_time, end_time) values(8, '19:20', '20:40');

-- create users
insert into users(id, username, password, role) values(1, 'admin1', '$2a$10$DaK1EhGyIQhrMYKrkYrOF.lCXmR/RNtQdvLa0k3fKIE0bVmbF3kVm', 0);
insert into users(id, username, password, role) values(2, 'teacher1', '$2a$10$DaK1EhGyIQhrMYKrkYrOF.lCXmR/RNtQdvLa0k3fKIE0bVmbF3kVm', 1);
insert into users(id, username, password, role) values(3, 'teacher2', '$2a$10$DaK1EhGyIQhrMYKrkYrOF.lCXmR/RNtQdvLa0k3fKIE0bVmbF3kVm', 1);
insert into users(id, username, password, role) values(4, 'teacher3', '$2a$10$DaK1EhGyIQhrMYKrkYrOF.lCXmR/RNtQdvLa0k3fKIE0bVmbF3kVm', 1);
insert into users(id, username, password, role) values(5, 'teacher4', '$2a$10$DaK1EhGyIQhrMYKrkYrOF.lCXmR/RNtQdvLa0k3fKIE0bVmbF3kVm', 1);
insert into users(id, username, password, role) values(6, 'teacher5', '$2a$10$DaK1EhGyIQhrMYKrkYrOF.lCXmR/RNtQdvLa0k3fKIE0bVmbF3kVm', 1);
insert into users(id, username, password, role) values(7, 'student1', '$2a$10$DaK1EhGyIQhrMYKrkYrOF.lCXmR/RNtQdvLa0k3fKIE0bVmbF3kVm', 2);
insert into users(id, username, password, role) values(8, 'student2', '$2a$10$DaK1EhGyIQhrMYKrkYrOF.lCXmR/RNtQdvLa0k3fKIE0bVmbF3kVm', 2);
insert into users(id, username, password, role) values(9, 'student3', '$2a$10$DaK1EhGyIQhrMYKrkYrOF.lCXmR/RNtQdvLa0k3fKIE0bVmbF3kVm', 2);
insert into users(id, username, password, role) values(10, 'student4', '$2a$10$DaK1EhGyIQhrMYKrkYrOF.lCXmR/RNtQdvLa0k3fKIE0bVmbF3kVm', 2);
insert into users(id, username, password, role) values(11, 'student5', '$2a$10$DaK1EhGyIQhrMYKrkYrOF.lCXmR/RNtQdvLa0k3fKIE0bVmbF3kVm', 2);
insert into users(id, username, password, role) values(12, 'student6', '$2a$10$DaK1EhGyIQhrMYKrkYrOF.lCXmR/RNtQdvLa0k3fKIE0bVmbF3kVm', 2);
insert into users(id, username, password, role) values(13, 'student7', '$2a$10$DaK1EhGyIQhrMYKrkYrOF.lCXmR/RNtQdvLa0k3fKIE0bVmbF3kVm', 2);
insert into users(id, username, password, role) values(14, 'student8', '$2a$10$DaK1EhGyIQhrMYKrkYrOF.lCXmR/RNtQdvLa0k3fKIE0bVmbF3kVm', 2);
insert into users(id, username, password, role) values(15, 'student9', '$2a$10$DaK1EhGyIQhrMYKrkYrOF.lCXmR/RNtQdvLa0k3fKIE0bVmbF3kVm', 2);
insert into users(id, username, password, role) values(16, 'student10', '$2a$10$DaK1EhGyIQhrMYKrkYrOF.lCXmR/RNtQdvLa0k3fKIE0bVmbF3kVm', 2);

-- create students
insert into students(id, first_name, last_name, user_id) values(1, 'Ben', 'Sandler',  7);
insert into students(id, first_name, last_name, user_id) values(2, 'Tom', 'Clark', 8);
insert into students(id, first_name, last_name, user_id) values(3, 'Steve', 'Scott', 9);
insert into students(id, first_name, last_name, user_id) values(4, 'John', 'Cruz', 10);
insert into students(id, first_name, last_name, user_id) values(5, 'Jason', 'Dubua', 11);
insert into students(id, first_name, last_name, user_id) values(6, 'Susan', 'Anderson', 12);
insert into students(id, first_name, last_name, user_id) values(7, 'Emily', 'Rock', 13);
insert into students(id, first_name, last_name, user_id) values(8, 'Kate', 'Doe', 14);
insert into students(id, first_name, last_name, user_id) values(9, 'Adam', 'Hardi', 15);
insert into students(id, first_name, last_name, user_id) values(10, 'Denis', 'Boe', 16);
-- create teachers
insert into teachers(id, first_name, last_name, user_id) values(1, 'Chloe', 'Harrison', 2);
insert into teachers(id, first_name, last_name, user_id) values(2, 'Harry', 'Potter', 3);
insert into teachers(id, first_name, last_name, user_id) values(3, 'Susan', 'Abigale', 4);
insert into teachers(id, first_name, last_name, user_id) values(4, 'Margo', 'Robbi', 5);
insert into teachers(id, first_name, last_name, user_id) values(5, 'Leo', 'Velaskes', 6);

-- create admins
insert into admins(id, first_name, last_name, user_id) values(1, 'Leo', 'Velaskes', 1);

-- groups of students
-- add students to main group #1 
insert into students_groups(student_id, group_id) values(1, 1);
insert into students_groups(student_id, group_id) values(2, 1);
insert into students_groups(student_id, group_id) values(3, 1);
insert into students_groups(student_id, group_id) values(4, 1);
insert into students_groups(student_id, group_id) values(5, 1);

-- add students to #1 subgroup of main group #1 
insert into students_groups(student_id, group_id) values(1, 2);
insert into students_groups(student_id, group_id) values(2, 2);
insert into students_groups(student_id, group_id) values(3, 2);

-- add students to #2 subgroup of main group #1
insert into students_groups(student_id, group_id) values(4, 3);
insert into students_groups(student_id, group_id) values(5, 3);

-- add students to main group #2 
insert into students_groups(student_id, group_id) values(6, 4);
insert into students_groups(student_id, group_id) values(7, 4);
insert into students_groups(student_id, group_id) values(8, 4);
insert into students_groups(student_id, group_id) values(9, 4);
insert into students_groups(student_id, group_id) values(10, 4);

-- add students to #1 subgroup of main group #2 
insert into students_groups(student_id, group_id) values(6, 5);
insert into students_groups(student_id, group_id) values(7, 5);

-- add students to #2 subgroup of main group #2
insert into students_groups(student_id, group_id) values(8, 6);
insert into students_groups(student_id, group_id) values(9, 6);
insert into students_groups(student_id, group_id) values(10, 6);

-- 1st teacher
insert into teachers_groups(teacher_id, group_id) values(1, 1);
insert into teachers_groups(teacher_id, group_id) values(1, 2);
insert into teachers_groups(teacher_id, group_id) values(1, 3);
insert into teachers_groups(teacher_id, group_id) values(1, 4);
insert into teachers_groups(teacher_id, group_id) values(1, 5);
insert into teachers_groups(teacher_id, group_id) values(1, 6);

-- 2nd teacher
insert into teachers_groups(teacher_id, group_id) values(2, 1);
insert into teachers_groups(teacher_id, group_id) values(2, 2);
insert into teachers_groups(teacher_id, group_id) values(2, 3);
insert into teachers_groups(teacher_id, group_id) values(2, 5);

-- 3rd teacher
insert into teachers_groups(teacher_id, group_id) values(3, 3);
insert into teachers_groups(teacher_id, group_id) values(3, 4);
insert into teachers_groups(teacher_id, group_id) values(3, 5);
insert into teachers_groups(teacher_id, group_id) values(3, 6);

-- 4th teacher
insert into teachers_groups(teacher_id, group_id) values(4, 1);
insert into teachers_groups(teacher_id, group_id) values(4, 2);
insert into teachers_groups(teacher_id, group_id) values(4, 3);
insert into teachers_groups(teacher_id, group_id) values(4, 4);

-- 5th teacher
insert into teachers_groups(teacher_id, group_id) values(5, 2);
insert into teachers_groups(teacher_id, group_id) values(5, 6);

-- courses of students
-- #1 student
insert into courses_students(student_id, course_id) values(1, 1);
insert into courses_students(student_id, course_id) values(1, 2);
insert into courses_students(student_id, course_id) values(1, 5);

-- #2 student
insert into courses_students(student_id, course_id) values(2, 1);
insert into courses_students(student_id, course_id) values(2, 2);
insert into courses_students(student_id, course_id) values(2, 5);

-- #3 student
insert into courses_students(student_id, course_id) values(3, 1);
insert into courses_students(student_id, course_id) values(3, 2);
insert into courses_students(student_id, course_id) values(3, 5);

-- #4 student
insert into courses_students(student_id, course_id) values(4, 1);
insert into courses_students(student_id, course_id) values(4, 2);
insert into courses_students(student_id, course_id) values(4, 3);

-- 5 student
insert into courses_students(student_id, course_id) values(5, 1);
insert into courses_students(student_id, course_id) values(5, 2);
insert into courses_students(student_id, course_id) values(5, 3);

-- 6 student
insert into courses_students(student_id, course_id) values(6, 1);
insert into courses_students(student_id, course_id) values(6, 3);
insert into courses_students(student_id, course_id) values(6, 4);

-- 7 student
insert into courses_students(student_id, course_id) values(7, 1);
insert into courses_students(student_id, course_id) values(7, 3);
insert into courses_students(student_id, course_id) values(7, 4);

-- 8 student
insert into courses_students(student_id, course_id) values(8, 1);
insert into courses_students(student_id, course_id) values(8, 3);
insert into courses_students(student_id, course_id) values(8, 5);

-- 9 student
insert into courses_students(student_id, course_id) values(9, 1);
insert into courses_students(student_id, course_id) values(9, 3);
insert into courses_students(student_id, course_id) values(9, 5);

-- 10 student
insert into courses_students(student_id, course_id) values(10, 1);
insert into courses_students(student_id, course_id) values(10, 3);
insert into courses_students(student_id, course_id) values(10, 5);

-- teachers of courses
insert into teachers_courses(teacher_id, course_id) values(1, 1);
insert into teachers_courses(teacher_id, course_id) values(2, 2);
insert into teachers_courses(teacher_id, course_id) values(2, 4);
insert into teachers_courses(teacher_id, course_id) values(3, 3);
insert into teachers_courses(teacher_id, course_id) values(4, 4);
insert into teachers_courses(teacher_id, course_id) values(4, 2);
insert into teachers_courses(teacher_id, course_id) values(5, 5);
