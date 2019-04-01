** Java Utils - utility methods and classes for popular needs **

Use this class if you need to do some operations on Java objects, collections, or if you need to generate objects etc.

* Uses Models Projector(https://github.com/glaures/modelprojector)

* Apache Commonts (https://commons.apache.org/)

* jUnit (https://junit.org/)

---

## Install

Install from source from Bitbucket. This library will be also added to Maven Central soon.

---

## Overview

Library contains some methods to

1. Handle **collections** of elements
2. **Print and stringify** object of any kind.
3. Some object utils e.g. POJO < - > DTO converter.
4. Some additional methods, e.g. **Object Coalesce**, wrapper for **CompletionException**, **array element exists checker** and more


![picture](/files/jutils.png)

---

## Usage

Code has been splitted for some subclasses as below.

*Example objects (see classes below)*
		
	    Game gameA = null;
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
		
1. **CollectionUtils** - methods usefult for handling collections

    a) CollectionUtils::collGroupper - group collection of objects by object's method value / by property

    - signature
    
            <T extends EntityTag, S> Map<S, Set<T>> collGroupper<T extends EntityTag, S> Map<S, Set<T>> collGroupper(Collection<T> collection,Collector<? super T, ?, Map<S,Set<T>>> collector)

	- usage 
	
            Map<String, Set<Game>> grouppedByCategory = CollectionUtils.collGroupper(
                    gamesCollection,
                    Game::getCategory
            );
            
	- result
	
            {category 3=[Game{name='Game 4', gameTag='game4', category='category 3'}],category 2=[Game{name='Game 2', gameTag='game2', category='category 2'}],category 1=[Game{name='Game 1', gameTag='game1', category='category 1'},Game{name='Game 3', gameTag='game3', category='category 1'}]}
    
    b) CollectionUtils.collGroupperFlatter - group collection of objects by object's method value / by property and flat to object value / property

    - signature
    
            <T extends EntityTag, S, R> Map<S, Set<R>> collGroupperFlatter(gamesCollection, Game::getCategory, Game::getName);

	- usage 
	
            Map<String, Set<String>> gameNameByCategory = CollectionUtils.collGroupperFlatter(
                    gamesCollection,
                    Game::getCategory,
                    Game::getName
            );
            
	- result
	
            {category 3=[Game 4], category 2=[Game 2], category 1=[Game 1, Game 3]}
    
    c) CollectionUtils.advancedTransFilter - collection's filter and transformer

    - signature
    
            Collection<T> advancedTransFilter(Collection<S> collection, Predicate<S> filter,Function<S,T> transformer)

	- usage 
	
            Collection<Game> gameListNewCategory = CollectionUtils.advancedTransFilter(
                    gamesCollection,
                    g -> g.getCategory().equals("category 1"),
                    g -> {
                        g.setCategory("New category");
                        return g;
                    }
            );
            
	- result
	
             [Game{name='Game 1', gameTag='game1', category='New category'}, Game{name='Game 3', gameTag='game3', category='New category'}]
                     
    d) CollectionUtils::transformCollection - transform collection using multiple transformers

    - signature
    
            <T> Collection<T> transformCollection(Collection<T> collection,UnaryOperator<T> ...transformers)

	- usage 
	
            Collection<Game> transformedCollection = CollectionUtils.transformCollection(
                    gamesCollection,
                    g -> {
                        g.setCategory("New category");
                        return g;
                    },
                    g -> {
                        g.setName(g.getName().toUpperCase());
                        return g;
                    },
                    g -> {
                        g.setGameTag(g.getGameTag().toLowerCase());
                        return g;
                    }
            );
            
	- result
	
            [Game{name='GAME 1', gameTag='game1', category='New category'},Game{name='GAME 2', gameTag='game2', category='New category'},Game{name='GAME 3', gameTag='game3', category='New category'},Game{name='GAME 4', gameTag='game4', category='New category'}]
            
    e)  CollectionUtils.findAll - search all elements in collection by predicate

    - signature
    
            <T> ArrayList<T> findAll(Collection<T> collection, Predicate<T> filter)

	- usage 
	
            ArrayList<Game> gamesFromCategory1 = CollectionUtils.findAll(
                    gamesCollection, game -> game.getCategory().equals("New category")
            );
            
	- result
	
            [Game{name='GAME 1', gameTag='game1', category='New category'},Game{name='GAME 2', gameTag='game2', category='New category'},Game{name='GAME 3', gameTag='game3', category='New category'},Game{name='GAME 4', gameTag='game4', category='New category'}]
                  
    f)  CollectionUtils.getRandomElement - get element by index or random if index is out of bound

    - signature
    
            <T> T getRandomElement(List<T> list, int index)

	- usage 
	
            Game randomGame = CollectionUtils.getRandomElement(gamesCollection, 5);
            
	- result
	
            Game{name='GAME 2', gameTag='game2', category='New category'}
          
    g)  CollectionUtils::getFirstFromCollection - get first element from collection

    - signature
    
            <E> E getFirstFromCollection(Collection<E> collection)

	- usage 
	
             Game firstGame = CollectionUtils.getFirstFromCollection(gamesCollection);
            
	- result
	
            Game{name='GAME 1', gameTag='game1', category='New category'}
          
    h)  CollectionUtils::objGenerateSet + CollectionUtils::objGenerate - generate Set/List of objects by supplier

    - signature
    
            <T> Set<T> objGenerateSet(Integer n, Function<Integer, T> s)
            <T> ArrayList<T> objGenerate(Integer n, Function<Integer, T> s)

	- usage 
	
	        Set<Game> my5Games = CollectionUtils.objGenerateSet(
                    5, i -> new Game("Game " + i, "tag" + i, "category")
            );
            
            ArrayList<Game> myAnother5Games = CollectionUtils.objGenerate(
                    5, i -> new Game("Game " + i, "tag" + i, "category")
            );
                 
	- result
	
            [Game{name='Game 0', gameTag='tag0', category='category'},Game{name='Game 1', gameTag='tag1', category='category'}, Game{name='Game 2', gameTag='tag2', category='category'},Game{name='Game 3', gameTag='tag3', category='category'}, Game{name='Game 4', gameTag='tag4', category='category'}]
            [Game{name='Game 0', gameTag='tag0', category='category'},Game{name='Game 1', gameTag='tag1', category='category'},Game{name='Game 2', gameTag='tag2', category='category'},Game{name='Game 3', gameTag='tag3', category='category'},Game{name='Game 4', gameTag='tag4', category='category'}]
    
    i)  CollectionUtils::listsCombiner 

    - signature
    
            <T> List<T> listsCombiner(List<? extends T>... lists)

	- usage 
	
            List<Game> combinedList = CollectionUtils.listsCombiner(gamesCollection2, gamesCollection3, gamesCollection4);
     
	- result
	
            [Game{name='GAME 1', gameTag='game1', category='New category'},Game{name='GAME 2', gameTag='game2', category='New category'},Game{name='GAME 3', gameTag='game3', category='New category'},Game{name='GAME 4', gameTag='game4', category='New category'}]

    j)  CollectionUtils::anyOkFromCollection, CollectionUtils::allNotOkFromCollection, CollectionUtils::allOkFromCollection

    - signatures
    
            <T> boolean allOkFromCollection(Collection<T> collection, Predicate<? super T> condition)
            <T> boolean allNotOkFromCollection(Collection<T> collection, Predicate<? super T> condition)
            <T> boolean anyOkFromCollection(Collection<T> collection, Predicate<? super T> condition)

	- usage 
	
	        boolean collectionContainsGameCategory1 = CollectionUtils.anyOkFromCollection(
                  gamesCollection2,
                  game -> game.getCategory().equals("category 1")
            );
            
            boolean collectionNotContainsGameCategory1 = CollectionUtils.allNotOkFromCollection(
                    gamesCollection2,
                    game -> game.getCategory().equals("category 1")
            );
            
            boolean collectionContainsOnlyGameCategory1 = CollectionUtils.allOkFromCollection(
                    gamesCollection2,
                    game -> game.getCategory().equals("category 1")
            );
            
	- result
	
             false, true, false
             
2. **ObjectUtils** - operations on objects.
    a) ObjectUtils::convertPojoToDto - convert POJO - > DTO

    - signatures
    
            <T extends DtoObject, S extends EntityTag> T convertPojoToDto(Class<T> targetClass, S post)
            <T extends DtoObject, S extends EntityTag> List<T> convertPojoToDto(Class<T> targetClass, Collection<S> post)
	
	- usage 
	
            // single object conversion
            GameDto gameDtoResult = ObjectUtils.convertPojoToDto(GameDto.class, gameB);
    
            // collection of POJOs
            List<GameDto> gameDtoResults = ObjectUtils.convertPojoToDto(GameDto.class, new ArrayList<Game>(){{
                add(gameB);
                add(gameC);
                add(gameD);
            }});
                
	- result
	
             GameDto object ( GameDto{name='Game 1'} )
             GameDto objects ( [GameDto{name='Game 1'}, GameDto{name='Game 2'}, GameDto{name='Game 3'}] )
             
    b) ObjectUtils::convert - convert DTO -> POJO

    - signature
    
            <T, S> T convert(Class<T> targetClass, S post)
	
	- usage 
	
            Game game = ObjectUtils.convert(Game.class, gameDtoResult);
                
	- result
	
             Game object ( Game{name='Game 1'} )
              
    `see more `( https://github.com/glaures/modelprojector )
           
3. **ThreadUtils**  - multithread programming utils.
    a) ThreadUtils::excToCompletableExc - wrap CompletionException exceptions in CompletableFuture 

    - signature
    
            CompletionException excToCompletableExc(Throwable tex)
	
	- usage 
    	
            /*
             * wrap custom exception in completable future and throw
             * to be able to handle this exception later (unwrap and handle)
             */
            CompletableFuture.allOf(
                    CompletableFuture.supplyAsync(() -> {
                        throw ThreadUtils.excToCompletableExc(new CustomException("Example exception"));
                    })
            )
    
            .handle((res, ex) -> {
    
                if (ex != null && ex.getCause() instanceof CustomException) {
                    return (CustomException) ex.getCause();
                }
                return null;
    
            });
            
	- result
	
            you can handle CustomException in .handle() method

4. **General Utils** - other useful methods.

    Utils::coalesce
        
        Utils.coalesce(gameA, userA, gameB, gameC)
        
        Utils.coalesce(
                () -> null,
                () -> gameB,
                () -> gameC
        );
    
        // Game{name='Game 1', gameTag='game1', category='category 1'}
        // Game{name='Game 1', gameTag='game1', category='category 1'}
        // first not null element

    Utils::rand
    
        int rand1 = Utils.rand(0, 100); // random number from 0 to 100
        int rand2 = Utils.rand(115, 200); // random number from 115 to 200
        
    Utils::getRandString
        
        String randString = Utils.getRandString(32);
        // random string 32 characters (e.g. ppS4NlSGnSTo7HuwPkvIV14HOBUPKdXh)
        
    Utils::arrayInclude
    
        String strings[] = {"abc", "def", "ghi"};
        Utils.arrayInclude(strings, "def"); // true
        Utils.arrayInclude(strings, "defghi"); // false
        
5. **Object stringifer** - var_dump / object printer.

    Interface
    
        interface Printeable {
            default String stringify() {
                return ToStringBuilder.reflectionToString(this, new ObjectStringifer.PrintToStringStyle());
            }
        
            default String deepStringify() {
                return (new ObjectStringifer.DeepObjectPrinter()).toString(this);
            }
        }
        
    **Usage:** Implement this interface if you want your object/class to be stringifeable.
    
    Example: 
    
        public class Game implements Printeable {} 
        
        Game game = new Game("Game 1", "game1", "category 1");
        
        String gameStr1 = gameB.stringify();
        // [name=Game 1,gameTag=game1,category=category 1]

        String gameStr2 =  game.deepStringify());
        // org.bitbucket.javautils.test.classes.Game[name=Game 1,gameTag=game1,category=category 1][]

        // object printers
        game.print();
        game.deepPrint();
        
6. Classes used in examples above
    
    see source code, package test.classes
    
    
## See more

`https://javaee.github.io/javaee-spec/javadocs/javax/persistence/package-frame.html`
`https://commons.apache.org/`
