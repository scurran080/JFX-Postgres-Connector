# JFX-Postgres-Connector
A postgres database GUI written with JavaFX.

This project was made as a pet project and in order to have a substitute to PGAdmin. It currently only supports Postgres and as a result requires the Postgres [JDBC](https://pages.github.com/](https://jdbc.postgresql.org/download/)). 
There are a few more things I would like to add to this, but I am not currently in a rush to add those changes.

#### Postgres
Learn to install an use postgres [here](https://www.digitalocean.com/community/tutorials/how-to-install-postgresql-on-ubuntu-20-04-quickstart).
The formatting for queries is like normal postgres. In one of the screenshots however you will notice
quotes around the table name. This is due to the fact that the table name is mixed case.
If you want to query normally make sure table names and column names are lowercase.
```console
sudo -u postgres psql
```
```console
git clone https://github.com/scurran080/JFX-Postgres-Connector.git
```

![alt text](https://github.com/scurran080/JFX-Postgres-Connector/blob/master/images/dbconnector.png?raw=true)
![alt text](https://github.com/scurran080/JFX-Postgres-Connector/blob/master/images/dashboard-query.png?raw=true)
