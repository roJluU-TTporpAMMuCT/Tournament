# Tournament
Restful application for creating and participating programming tasks on java

The task consists of title, condition, empty class with empty method(s), first smaller visible test class and second larger invisible test class.<br>
Solution of task is just a class(usualy you can just start filling provided empty class with empty method/methods).

If there a compile error or test fail while creating\participating the task - you'll get a rest feedback with all interesting information.

The applicaton consists of 3 components:
 - In-memory compiler;
 - Performing requests and responses(Controllers/Services);
 - Storing and accessing tasks, solutions and other data(Repos/Models);

Repos realized from JPA repo standart methods and some spring data features.<br>
Models bades on Hibernate.<br>
In-memory compiler assembled from edited standard java classes.<br>
There is presented some features of lombok and provided security coverage from Spring Security.
