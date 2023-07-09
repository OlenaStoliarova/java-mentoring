# Getting Started

### task 2

`docker run -itd -e POSTGRES_HOST_AUTH_METHOD=trust -p 5432:5432 --name m43-security-postgresql postgres`

`docker exec -it m43-security-postgresql bash`

`psql -U postgres`

`CREATE DATABASE securityapp;`

`ALTER ROLE postgres SUPERUSER;`

`CREATE SCHEMA securityapp;`
