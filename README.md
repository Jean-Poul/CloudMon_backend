# CA3 Backend Boilerplate 
[![Build Status](https://app.travis-ci.com/Jean-Poul/CloudMon_backend.svg?branch=main)](https://app.travis-ci.com/Jean-Poul/CloudMon_backend)

## DAT3SEM GRP. 8
Alexander Pihl

Deployed on: [https://alexanderpihl.com/CA3_Boilerplate_Backend/](https://alexanderpihl.com/CA3_Boilerplate_Backend/)

## Guide for using this code
1. Make sure you have 
2. Clone the code into af folder of your choosing.
3. Start up the project in Netbeans
4. In the pom.xml file go to <remote.server>http://alexanderpihl.com/manager/text</remote.server> in line 19. Change to your own droplet.
5. Open up workbench and set up your databases both for production and for testing
6. Go the persistence.xml file in line 21 and change Ca3_boilerPlate to point at your own database. Do the same in line 35 regarding your test database.
7. Open the EMF_Creator.java file. Change line 43 and 46 to point at your CONNECTION_STR on your vitual machine. (Can be found in the docker-compose.yml file)
8. Make sure to Clean and build the project and run the SetupTestUsers.java which populates the database. 
9. Now migrate the database to your droplet using the wizard in workbench.
10. Go to https://travis-ci.com/ and connect to your github account. Give travis access to your repository on github so travis can deploy your war file for you, after each successful build. Remember to add environment varibles (user and password) for your virtual machine since travis can't deploy without this.
    
## You're now ready to use the code properly.
Run your project local or online and test the following endpoints


GET methods:

1. https://alexanderpihl.com/CA3_Boilerplate_Backend/api/jokes (JokeResource.java) will let you see what info Joke_CombinedDTO.java is holding.

2. https://alexanderpihl.com/CA3_Boilerplate_Backend/api/scrape/sequential (ScrapeResource.java) will let you see info about 4 urls that is called sequentially

3. https://alexanderpihl.com/CA3_Boilerplate_Backend/api/scrape/parallel (ScrapeResource.java) will let you see info about 4 urls that is called parallel

4. https://alexanderpihl.com/CA3_Boilerplate_Backend/api/info/all (UserResource.java) will let you see how many users there are in your database

5. https://alexanderpihl.com/CA3_Boilerplate_Backend/api/info/user (UserResource.java) will let you see if you can login with a user and token you get from the POST method. Remember to add "x-access-token" with the token in the header.

6. https://alexanderpihl.com/CA3_Boilerplate_Backend/api/info/admin (UserResource.java) same steps as endpoint 5 but here you test login for an admin

7. https://alexanderpihl.com/CA3_Boilerplate_Backend/api/quotes (Breaking_ BadResource.java) gets you a random quote from the tv seriest Breaking Bad

8. https://alexanderpihl.com/CA3_Boilerplate_Backend/api/quotes/{numberofquotes} (Breaking_ BadResource.java) gets you any number of quotes you want at the same time.

POST method

1. https://alexanderpihl.com/CA3_Boilerplate_Backend/api/login (LoginEndpoint.java) with this endpoint you can with a program like postmand create a user which will then send you a token in response. Remember to add a user and password to the body of the request (if using postman)
