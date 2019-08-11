Frequently Asked Questions
----

If you have any questions, please feel free to contact me.

#### What does this library is usefult to?

Use this class if you need to do some operations on 
Java objects, collections, or if you need to generate
 objects etc.


#### Any examples?
Use as singleton:

```java
/Game gameA = null;
 User userA = null;
 Game gameB = new Game("Game 1", "game1", "category 1");
 Game gameC = new Game("Game 2", "game2", "category 2");
 Game gameD = new Game("Game 3", "game3", "category 1");
 Game gameE = new Game("Game 4", "game4", "category 3");
 
 
 List<Game> gamesCollection = new LinkedList<>(){{
     add(gameB); add(gameC); add(gameD); add(gameE);
 }};
 
 List<Game> gamesCollection2 = new LinkedList<>(){{
     add(gameB);
 }};
 
 List<Game> gamesCollection3 = new LinkedList<>(){{
     add(gameC); add(gameD);
 }};
 
 List<Game> gamesCollection4 = new LinkedList<>(){{
     add(gameE);
 }};
```
    