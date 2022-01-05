# CloudMon - Backend [![Build Status](https://app.travis-ci.com/Jean-Poul/CloudMon_backend.svg?branch=main)](https://app.travis-ci.com/Jean-Poul/CloudMon_backend)

### Udarbejdet af:
**Alexander Pihl & Jean-Poul Wilhelm Luplau Leth-Møller**

*CloudMon er udviklet over en periode fra den 1.11.2021 til den 05.01.2022, som del af den afsluttende hovedopgave på Datamatikeruddannelsen på Copenhagen Business Academy Lyngby, og tager udgangspunkt i en problemstilling, der ønskes løst, i virksomheden GovCloud.*

--- 

## Guide for using this code
1. Make sure you have 
2. Clone the code into af folder of your choosing.
3. Start up the project in Netbeans
4. In the pom.xml file go to <remote.server>http://www.jplm.dk/manager/text</remote.server> in line 19. Change to your own droplet.
5. Open up workbench and set up your databases both for production and for testing
6. Go the persistence.xml file in line 21 and change cloudmon to point at your own database. Do the same in line 35 regarding your test database.
7. Open the EMF_Creator.java file. Change line 43 and 46 to point at your CONNECTION_STR on your vitual machine. (Can be found in the docker-compose.yml file)
8. Make sure to Clean and build the project and run the SetupTestUsers.java which populates the database. 
9. Now migrate the database to your droplet using the wizard in workbench.
10. Go to https://travis-ci.com/ and connect to your github account. Give travis access to your repository on github so travis can deploy your war file for you, after each successful build. Remember to add environment varibles (user and password) for your virtual machine since travis can't deploy without this.

## Repos
[CloudMon - Frontend](https://github.com/Jean-Poul/CloudMon_frontend)

[CloudMon - Backend](https://github.com/Jean-Poul/CloudMon_backend)

---

## Deployed frontend & backend:
[https://cloudmon.surge.sh/](https://cloudmon.surge.sh/)

[https://www.jplm.dk/cloudmon/](https://www.jplm.dk/cloudmon/)

---

## Setup Filer:
[SetupTestUsers](https://gist.github.com/Jean-Poul/5809e72e97be979e29daaed60ef662d1)

[SetupTestData](https://gist.github.com/Jean-Poul/c7badf890de76f6a0738e3b358a47c5f)

---

## Postman documentation:
[Postman documentation](https://documenter.getpostman.com/view/12822718/UVR5s9RN)

---

## Frontend configuration
The frontend is a single page application (SPA) written in React.

---

## Backend configuration:
**The Database**
- MySQL Database using Java Persistence API (JPA) (With some JPQL) to achieve ORM.
  
**RESTFUL Web service**
- JAX-RS to handle REST operations

**Testing**
Consisting of unit and integration tests using:
- Grizzly webserver
- Hamcrest
- JUnit
- REST-Assured

**Security**
- BCrypt plus hash/salt configurations.

**CI/CD pipeline**
- Travis configuration with github hooks - Everytime you push, travis builds and deploys
