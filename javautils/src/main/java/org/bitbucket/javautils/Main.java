package org.bitbucket.javautils;
import org.bitbucket.javautils.test.classes.CustomException;
import org.bitbucket.javautils.test.classes.Game;
import org.bitbucket.javautils.test.classes.GameDto;
import org.bitbucket.javautils.test.classes.User;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings({"unused", "SameParameterValue"})
public class Main {

    public static void main(String[] args) {

        /*
            --------------- some basic tests ---------------
         */


        Game gameA = null;
        User userA = null;
        Game gameB = new Game("Game 1", "game1", "category 1");
        Game gameC = new Game("Game 2", "game2", "category 2");
        Game gameD = new Game("Game 3", "game3", "category 1");
        Game gameE = new Game("Game 4", "game4", "category 3");




        // --------------- General Utils ---------------



        /*
         * Utils::coalesce
         * get first not-null element
         */

        // coalese - single element
        System.out.println(Utils.coalesce(gameA, userA, gameB, gameC));
        // [ result ] Game{name='Game 1', gameTag='game1', category='category 1'}

        // coalese - supplier
        System.out.println(Utils.coalesce(
                () -> null,
                () -> gameB,
                () -> gameC
        ));
        //  [ result ]  Game{name='Game 1', gameTag='game1', category='category 1'}



        /*
         * Utils::rand
         * get random number from range
         */
        int rand1 = Utils.rand(0, 100);
        int rand2 = Utils.rand(115, 200);
        System.out.println(rand1);
        //  [ result ]  random number from 0 to 100
        System.out.println(rand2);
        //  [ result ]  random number from 115 to 200


        /*
         * Utils::getRandString
         * generate random token
         */
        String randString = Utils.getRandString(32);
        System.out.println(randString);
        //  [ result ]  random string 32 characters (e.g. ppS4NlSGnSTo7HuwPkvIV14HOBUPKdXh)


        /*
         * Utils::arrayInclude
         * check if element exists in array
         */
        String strings[] = {"abc", "def", "ghi"};
        System.out.println(Utils.arrayInclude(strings, "def"));
        //  [ result ] boolean (true)




        // --------------- Thread Utils ---------------



        /*
         * ThreadUtils::excToCompletableExc
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
        //  [ result ] you can handle CustomException in .handle() method




        // --------------- Object Utils ---------------



        /*
         * ObjectUtils::convertPojoToDto
         * convert POJO object to DTO
         */

        // single object conversion
        GameDto gameDtoResult = ObjectUtils.convertPojoToDto(GameDto.class, gameB);

        // collection of POJOs
        List<GameDto> gameDtoResults = ObjectUtils.convertPojoToDto(GameDto.class, new ArrayList<Game>(){{
            add(gameB);
            add(gameC);
            add(gameD);
        }});

        System.out.println(gameDtoResult);
        //  [ result ] GameDto object ( GameDto{name='Game 1'} )

        System.out.println(gameDtoResults);
        //  [ result ] GameDto objects ( [GameDto{name='Game 1'}, GameDto{name='Game 2'}, GameDto{name='Game 3'}] )




        // --------------- general Utils ---------------



        /*
         * ObjectStringifer::stringify
         * ObjectStringifer::deepStringify
         *
         * stringify object / var_dump object
         */
        System.out.println(gameB.stringify());
        // [ result ]  [name=Game 1,gameTag=game1,category=category 1]

        System.out.println(gameB.deepStringify());
        // [ result ]  org.bitbucket.javautils.test.classes.Game[name=Game 1,gameTag=game1,category=category 1][]






        // --------------- Collection Utils ---------------



        List<Game> gamesCollection = new LinkedList<>(){{
            add(gameB);
            add(gameC);
            add(gameD);
            add(gameE);
        }};

        List<Game> gamesCollection2 = new LinkedList<>(){{
            add(gameB);
        }};

        List<Game> gamesCollection3 = new LinkedList<>(){{
            add(gameC);
            add(gameD);
        }};

        List<Game> gamesCollection4 = new LinkedList<>(){{
            add(gameE);
        }};


        /*
         * CollectionUtils::collGroupper
         *
         * group collection of objects by object's method value / by property
         */
        Map<String, Set<Game>> grouppedByCategory = CollectionUtils.collGroupper(
                gamesCollection,
                Game::getCategory
        );

        System.out.println(grouppedByCategory);
        // [ result ]
        // {category 3=[Game{name='Game 4', gameTag='game4', category='category 3'}],
        // category 2=[Game{name='Game 2', gameTag='game2', category='category 2'}],
        // category 1=[Game{name='Game 1', gameTag='game1', category='category 1'},
        // Game{name='Game 3', gameTag='game3', category='category 1'}]}


        /*
         * CollectionUtils::collGroupperFlatter
         *
         * group collection of objects by object's method value / by property
         * and flat to object value / property
         */
        Map<String, Set<String>> gameNameByCategory = CollectionUtils.collGroupperFlatter(
                gamesCollection,
                Game::getCategory,
                Game::getName
        );

        System.out.println(gameNameByCategory);
        // [ result ] {category 3=[Game 4], category 2=[Game 2], category 1=[Game 1, Game 3]}




        /*
         * CollectionUtils::advancedTransFilter
         *
         * collection's filter and transformer
         */
        Collection<Game> gameListNewCategory = CollectionUtils.advancedTransFilter(
                gamesCollection,
                g -> g.getCategory().equals("category 1"),
                g -> {
                    g.setCategory("New category");
                    return g;
                }
        );

        System.out.println(gameListNewCategory);
        // [ result ]
        // [Game{name='Game 1', gameTag='game1', category='New category'},
        // Game{name='Game 3', gameTag='game3', category='New category'}]


        /*
         * CollectionUtils::transformCollection
         *
         * transform collection using multiple transformers
         */
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

        System.out.println(transformedCollection);
        // [ result ]
        // [Game{name='GAME 1', gameTag='game1', category='New category'},
        // Game{name='GAME 2', gameTag='game2', category='New category'},
        // Game{name='GAME 3', gameTag='game3', category='New category'},
        // Game{name='GAME 4', gameTag='game4', category='New category'}]


        /*
         * CollectionUtils::findAll
         *
         * search all elements in collection by predicate
         */
        ArrayList<Game> gamesFromCategory1 = CollectionUtils.findAll(
                gamesCollection, game -> game.getCategory().equals("New category")
        );

        System.out.println(gamesFromCategory1);
        // [ result ]
        // [Game{name='GAME 1', gameTag='game1', category='New category'},
        // Game{name='GAME 2', gameTag='game2', category='New category'},
        // Game{name='GAME 3', gameTag='game3', category='New category'},
        // Game{name='GAME 4', gameTag='game4', category='New category'}]


        /*
         * CollectionUtils::gamesCollection
         *
         * get element by index or random if index is out of bound
         */
        Game randomGame = CollectionUtils.getRandomElement(gamesCollection, 5);

        System.out.println(randomGame);
        // [ result ] Game{name='GAME 2', gameTag='game2', category='New category'}


        /*
         * CollectionUtils::getFirstFromCollection
         *
         * get first element from collection
         */
        Game firstGame = CollectionUtils.getFirstFromCollection(gamesCollection);

        System.out.println(firstGame);
        // [ result ] Game{name='GAME 1', gameTag='game1', category='New category'}



        /*
         * CollectionUtils::objGenerateSet
         *
         * generate Set of objects by supplier
         */
        Set<Game> my5Games = CollectionUtils.objGenerateSet(
                5, i -> new Game("Game " + i, "tag" + i, "category")
        );

        System.out.println(my5Games);
        // [ result ]
        // [Game{name='Game 0', gameTag='tag0', category='category'},
        // Game{name='Game 1', gameTag='tag1', category='category'}, Game{name='Game 2', gameTag='tag2', category='category'},
        // Game{name='Game 3', gameTag='tag3', category='category'}, Game{name='Game 4', gameTag='tag4', category='category'}]


        /*
         * CollectionUtils::objGenerate
         *
         * generate List of objects by supplier
         */
        ArrayList<Game> myAnother5Games = CollectionUtils.objGenerate(
                5, i -> new Game("Game " + i, "tag" + i, "category")
        );

        System.out.println(myAnother5Games);
        // [ result ]
        // [Game{name='Game 0', gameTag='tag0', category='category'},
        // Game{name='Game 1', gameTag='tag1', category='category'},
        // Game{name='Game 2', gameTag='tag2', category='category'},
        // Game{name='Game 3', gameTag='tag3', category='category'},
        // Game{name='Game 4', gameTag='tag4', category='category'}]



        /*
         * CollectionUtils::listsCombiner
         *
         * combine several lists
         */
        List<Game> combinedList = CollectionUtils.listsCombiner(gamesCollection2, gamesCollection3, gamesCollection4);

        System.out.println(combinedList);
        // [ result ]
        // [Game{name='GAME 1', gameTag='game1', category='New category'},
        // Game{name='GAME 2', gameTag='game2', category='New category'},
        // Game{name='GAME 3', gameTag='game3', category='New category'},
        // Game{name='GAME 4', gameTag='game4', category='New category'}]



        /*
         * CollectionUtils::anyOkFromCollection
         *
         * check if collection contains element
         */
        boolean collectionContainsGameCategory1 = CollectionUtils.anyOkFromCollection(
              gamesCollection2,
              game -> game.getCategory().equals("category 1")
        );

        System.out.println(collectionContainsGameCategory1);
        // [ result ] boolean (false)


        /*
         * CollectionUtils::allNotOkFromCollection
         *
         * check if collection doesn't contain element
         */
        boolean collectionNotContainsGameCategory1 = CollectionUtils.allNotOkFromCollection(
                gamesCollection2,
                game -> game.getCategory().equals("category 1")
        );

        System.out.println(collectionNotContainsGameCategory1);
        // [ result ] boolean (true)



        /*
         * CollectionUtils::allOkFromCollection
         *
         * check if collection contains only element
         * match to predicate
         */
        boolean collectionContainsOnlyGameCategory1 = CollectionUtils.allOkFromCollection(
                gamesCollection2,
                game -> game.getCategory().equals("category 1")
        );

        System.out.println(collectionContainsOnlyGameCategory1);
        // [ result ] boolean (false)


    }
}