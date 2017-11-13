CREATE KEYSPACE IF NOT EXISTS mydb WITH replication={'class': 'SimpleStrategy', 'replication_factor': '1'};
CREATE TABLE IF NOT EXISTS mydb.Employee ( id int, firstname text, lastname text, PRIMARY KEY(id));
