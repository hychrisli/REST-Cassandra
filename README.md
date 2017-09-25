# REST-Cassandra #

RESTful Web Service on Cassandra. CMPE 282 Assignment. 

### Goal ###

* Support http methods GET/POST/PUT/DELETE through Java based REST API

### Functionality ###
* GET /.../rest/employee/
* GET /.../rest/project
* GET /.../rest/employee/m
* GET /.../rest/project/n
* POST /.../rest/employee
* POST /.../rest/project
* PUT /.../rest/employee/m
* PUT /.../rest/project/n
* DELETE /.../rest/employee/m
* DELETE /.../rest/project/n

### Cassandra Setup ###

CREATE KEYSPACE mydb WITH replication={'class': 'SimpleStrategy', 'replication_factor': '1'};

CREATE TABLE Employee ( id int, firstname text, lastname text, PRIMARY KEY(id));
INSERT INTO Employee (id, firstname, lastname) VALUES (1, 'John', 'Smith');
INSERT INTO Employee (id, firstname, lastname) VALUES (2, 'Laura', 'Williams');

CREATE TABLE Project (id int, name text, budget float, PRIMARY KEY(id));
INSERT INTO Project (id, name, budget) VALUES (1, 'Apache Cassandra', 32300.00);
INSERT INTO Project (id, name, budget) VALUES (2, 'Apache Spark', 42390.00);
