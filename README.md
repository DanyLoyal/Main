
# Hobby
Hold B Gruppe 5
Pelle, Danyal, Nicolai, Carsten

### Description of business idea

The business idea is to create a platform that not only provides information about people and their hobbies but also offers a matchmaking service. Users can input their hobbies and interests, and the platform can connect them with like-minded individuals or groups in their local area. This would add a social networking element to the service and encourage people to explore new hobbies together, fostering real-world connections.

![DomainModelPic.png](src%2Fmain%2Fdocumentation%2FDomainModelPic.png)
### Domaine Model Description
 
Originally we thought of having only 2 entities: Hobby and User. Later we decided to add Userinfo to manage information about the user. We then added the phonenumber entity to manage phonenumbers for the user, in case the user has multiple phone numbers, work phone, home phone etc.
The address entity was made to store the address for the user, with the connection to the zip entity. The zip entity would manage all of the danish zipcodes, citynames, regions and municipalities.
Like we did with the userinfo managing info for the user, we also decided to include a hobbyInfo entity, which would do the same for the Hobby entity, this time managing types of hobbies using ENUM.

### EER Model Description

As described in the "Domaine Model Description", we decided on adding a lot of tables after our original two.
The user and hobby entities would be the parent entities of the rest.
Userinfos Id would be a foreign key of Users primary key, so it wouldnt be possible for an entity of userinfo to exist without the parent.
Phonenumber and adress would have their own generated primary keys, but would contain a foreign key for the user entity. The idea behind it was that a user could have multiple phonenumbers and adresses, which otherwise wouldnt be possible.
As formerly mentioned zipcode would manage the zipcodes and cities of Denmark, with the zipcode being the primary key. The address entity would then contain a foreign key for zip.

To maintain a manytomany relationship between users and hobbies, we created another table in the DB called user_hobby. This would contain the primary keys of users and its hobbies.

Hobbyinfo would contain its own generated primary key and it would contain enums for the different types of hobbies. Each hobby would then be able to have multiple types like "outdoor" and "competition".

To manage the manytomany relationship between hobby and hobbyinfo, we would include a table called hobby_hobby_info containing the primary keys of hobby and hobby_info.


![EERModelPic.png](src%2Fmain%2Fdocumentation%2FEERModelPic.png)


### Project Management

We have primarly used Github for most of our sharing of code and Project management.
We use Github Projects to manage our assignments and what we are missing. We met every day at 10:00 to discuss our progress and help eachother with the problems we encountered.
Since we only had 4 days from start to finish, we spend a lot of the time on fixing problems with the code and less time on proper preparation of our product and time schedule.
Discord was our main form of communication.

### Technical Requirements

- JPA
- JPQL
- Maven
- JDK 17^
- JUnit 5
- Docker
- PostgresSQL
- pgAdmin
- Lombok


### Project Set up

1. Clone the project from Github: https://github.com/DanyLoyal/Main
2. Create Database named "hobby", ("hobby_test" for test DB)
3. Run main once to create the tables
4. Run the "Hobby_and_hobby_info_populator.txt" and "Zipcode_Populator.txt" populator files in a query
