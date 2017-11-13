#!/bin/bash

cqlsh -e "CREATE KEYSPACE mydb WITH replication={'class': 'SimpleStrategy', 'replication_factor': '1'};"
echo "\n\n\n success \n\n\n"
cqlsh -e "CREATE TABLE mydb.Employee ( id int, firstname text, lastname text, PRIMARY KEY(id));" 	
cqlsh -e "CREATE TABLE mydb.Project (id int, name text, budget float, PRIMARY KEY(id));"
