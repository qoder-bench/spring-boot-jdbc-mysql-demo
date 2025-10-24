# build the project
build:
    mvn -DskipTests clean package

# database migration with flyway
database-migrate:
    mvn org.flywaydb:flyway-maven-plugin:11.15.0:clean
    mvn org.flywaydb:flyway-maven-plugin:11.15.0:migrate

# DBUnit operation to fill dataset
dbunit-operation: database-migrate
    mvn org.dbunit:dbunit-maven-plugin:1.2.0:operation

# mysql CLI
mysql-cli:
    mysql -h 127.0.0.1 -P 13306 -u root -p test
