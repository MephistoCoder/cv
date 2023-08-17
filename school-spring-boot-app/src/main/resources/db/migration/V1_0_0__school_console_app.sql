DROP TABLE IF EXISTS courses_students, students, groups, courses CASCADE;

CREATE TABLE IF NOT EXISTS groups
(
    group_id serial NOT NULL,
    group_name character varying(5),
    CONSTRAINT groups_pkey PRIMARY KEY (group_id)
);

CREATE TABLE IF NOT EXISTS students
(
    student_id serial NOT NULL,
    group_id integer,
    first_name character varying(20) NOT NULL,
    last_name character varying(20) NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (student_id),
    CONSTRAINT group_id_fk FOREIGN KEY (group_id)
        REFERENCES public.groups (group_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE Set NULL
);

CREATE TABLE IF NOT EXISTS courses
(
    course_id serial NOT NULL,
    course_name character varying(20) NOT NULL,
    course_description character varying(50),
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
);
	
CREATE TABLE IF NOT EXISTS courses_students
(
    student_id integer NOT NULL,
    course_id integer NOT NULL,
    CONSTRAINT courses_students_pkey PRIMARY KEY (student_id, course_id),
    CONSTRAINT fk_course FOREIGN KEY (course_id)
        REFERENCES courses (course_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_student FOREIGN KEY (student_id)
        REFERENCES students (student_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
