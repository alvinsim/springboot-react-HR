DROP TABLE IF EXISTS jobs;

CREATE TABLE jobs (
	id	        INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	job_title	VARCHAR(50) NOT NULL,
	min_salary	DOUBLE NOT NULL,
	max_salary	DOUBLE NOT NULL
);

DROP TABLE IF EXISTS regions;

CREATE TABLE regions (
	id	        INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	region_name	VARCHAR(25) NOT NULL
);

DROP TABLE IF EXISTS countries;

CREATE TABLE countries (
	id            INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	country_code  VARCHAR(2) NOT NULL,
	country_name  VARCHAR(25) NOT NULL,
	region_id     INT NOT NULL,
	PRIMARY KEY (id ASC),
	FOREIGN KEY (region_id) REFERENCES regions (id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS locations;

CREATE TABLE locations (
	id	            INT PRIMARY KEY AUTO_INCREMENT,
	street_address	VARCHAR(50),
	postal_code	    VARCHAR(7),
	city	        VARCHAR(50) NOT NULL,
	state_province	VARCHAR(25),
	country_id	    INT NOT NULL,
	FOREIGN KEY (country_id) REFERENCES countries (id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS departments;

CREATE TABLE departments (
	id	            INT PRIMARY KEY AUTO_INCREMENT,
	department_name VARCHAR(20) NOT NULL,
	location_id	    INT NOT NULL,
	FOREIGN KEY (location_id) REFERENCES locations (id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS employees;

CREATE TABLE employees (
	id	            INT PRIMARY KEY AUTO_INCREMENT,
	first_name	    VARCHAR(20) NOT NULL,
	last_name       VARCHAR(20) NOT NULL,
	email           VARCHAR(100) NOT NULL,
	phone_number    VARCHAR(12),
	hire_date	    DATE NOT NULL,
	job_id	        INT NOT NULL,
	salary	        DOUBLE NOT NULL,
	manager_id	    INT,
	department_id	INT NOT NULL,
	FOREIGN KEY (manager_id) REFERENCES employees (id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (job_id) REFERENCES jobs (id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (department_id) REFERENCES departments (id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS dependents;

CREATE TABLE dependents (
	id	            INT PRIMARY KEY AUTO_INCREMENT,
	first_name	    VARCHAR(20) NOT NULL,
	last_name	    VARCHAR(20) NOT NULL,
	relationship	VARCHAR(10) NOT NULL,
	employee_id	    INT NOT NULL,
	FOREIGN KEY (employee_id) REFERENCES employees (id) ON DELETE CASCADE ON UPDATE CASCADE
);
