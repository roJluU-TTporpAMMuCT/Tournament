# Tournament
Restful application for creating and participating programming tasks on java

The task consists of title, condition, empty class with empty method(s), first smaller visible test class and second larger invisible test class.<br>
Solution of task is just a class(usually you can just start filling provided empty class with empty method/methods).

If there a compile error or test fail while creating\participating the task - you'll get a rest feedback with all interesting information.

The application consists of 3 components:
 - In-memory compiler;
 - Performing requests and responses(Controllers/Services);
 - Storing and accessing tasks, solutions and other data(Repos/Models);

Repos realized from JPA repo standard methods and some spring data features.<br>
Models based on Hibernate and database realized by PostgreSQL.<br>
In-memory compiler assembled from edited standard java classes.<br>
There is presented some features of lombok and provided security coverage from Spring Security.<br>

Database ERD:
![image](https://user-images.githubusercontent.com/102484646/171260322-86e2e4da-beba-44fa-9327-350ea071ed94.png)
<br>
API of application:<br>
&nbsp; Login part:<br>
&nbsp;&nbsp;  POST:/singUp(attr: username, rawPassword) - for registration in application<br>
&nbsp;&nbsp;  POST:/login(attr: username, password) - for login in application<br>
&nbsp; Quest creation part:<br>
&nbsp;&nbsp;  POST:/create(attr: quest model, solve model) - attempt to create a quest<br>
&nbsp; Solution creation part:<br>
&nbsp;&nbsp;  GET:/quest/{questID} - just get a quest conditions<br>
&nbsp;&nbsp;  POST:/quest/{questID}(attr: solve model) - attempt to solve a quest
<br>
Brief overview:
![image](https://user-images.githubusercontent.com/102484646/171264485-00f9359d-08ac-48ea-bd50-0209be9e8ab7.png)<br>
To get access to container you have to login.
After login you have some ways to use application:
 Quest creation
 Quest solving

To create quest you have to come up with a name of quest, classname for empty class, conditions of quest and create testcases classes: one smaller and visible, there participator can look directly what output are expected from him and one much bigger and invisible, there tests all envisioned situations to don't accept incorrect solution. So, necessarily you must create a first solution to your own quest to prove that it's possible to solve.

To participate in someones quest - just create solution and attempt to pass testcases, if don't pass you'll get a feedback what was wrong, if pass your solution will save to database.
<br><br>
Brief application architecture:
![image](https://user-images.githubusercontent.com/102484646/171443348-9a1860e3-fb56-4b33-98db-5fb09d3be632.png)
