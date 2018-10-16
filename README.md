# RestClient
REST client using Sring boot. 
Included: Csv parser, REST controllers, JPA storage, requests logging.
How to run: 
 -  create schema "loanSchema" in mysql server.
 -  run Spring Boot app.

Http requests:
  - http://127.0.0.1:8080/api/data/sync - parse CSV file and store data in DB.
  - http://127.0.0.1:8080/api/file - get all records from CSV file.
  - http://127.0.0.1:8080/api/db - get all records from DB.
  - http://127.0.0.1:8080/api/db?gender=[0,1]&year=[int]&state=[Curent,Repaid,Late] - 
                              return records stored in db with filter in any combinations.
  - http://127.0.0.1:8080/api/file?gender=[0,1]&year=[int]&state=[Curent,Repaid,Late] - 
                              return records stored in file with filter in any combinations.
