package com.github.hadasbro.javautils;

import com.github.hadasbro.javautils.test.classes.Game;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.*;

public class GeneralTest {

    private Game gameB = new Game("Game 1", "game1", "category 1");

    private Game gameC = new Game("Game 2", "game2", "category 2");

    private Game gameD = new Game("Game 3", "game3", "category 1");

    private Game gameE = new Game("Game 4", "game4", "category 3");

    @Test
    public void generalTest() {

        assertEquals(Utils.coalesce(null, null, gameB, gameC).toString(),
                "Game{name='Game 1', gameTag='game1', category='category 1'}");

        assertEquals(Utils.coalesce(
                () -> null,
                () -> gameB,
                () -> gameC
        ).toString(), "Game{name='Game 1', gameTag='game1', category='category 1'}");

        /*
         * Utils::rand
         * get random number from range
         */
        int rand1 = Utils.rand(0, 100);
        int rand2 = Utils.rand(115, 200);

        assertTrue(rand1>=0 && rand1<=100);

        assertTrue(rand2 >= 115);
        assertTrue(rand2<= 200);

        /*
         * Utils::getRandString
         * generate random token
         */
        String randString = Utils.getRandString(32);
        assertEquals(32, randString.length());

        /*
         * Utils::arrayInclude
         * check if element exists in array
         */
        String strings[] = {"abc", "def", "ghi"};

        assertTrue(Utils.arrayInclude(strings, "def"));

        assertEquals(gameB.stringify(), "com.github.hadasbro.javautils.test.classes.Game" +
                "[name=Game 1,gameTag=game1,category=category 1][]");

        assertEquals(gameB.deepStringify(), "com.github.hadasbro.javautils.test.classes.Game" +
                "[name=Game 1,gameTag=game1,category=category 1][]");

    }

    @Test
    public void collectionManipulationTest(){

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

        assertEquals(grouppedByCategory.toString(), "{category 3=[Game{name='Game 4', gameTag='game4', " +
                "category='category 3'}], category 2=[Game{name='Game 2', gameTag='game2', category='category 2'}], " +
                "category 1=[Game{name='Game 1', gameTag='game1', category='category 1'}, " +
                "Game{name='Game 3', gameTag='game3', category='category 1'}]}");


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

        assertEquals(gameNameByCategory.toString(), "{category 3=[Game 4], category 2=[Game 2], " +
                "category 1=[Game 1, Game 3]}");


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

        assertEquals(gameListNewCategory.toString(), "[Game{name='Game 1', gameTag='game1', category='New category'}, " +
                "Game{name='Game 3', gameTag='game3', category='New category'}]");

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

        assertEquals(transformedCollection.toString(), "[Game{name='GAME 1', gameTag='game1', category='New category'}, " +
                "Game{name='GAME 2', gameTag='game2', category='New category'}, Game{name='GAME 3', gameTag='game3', " +
                "category='New category'}, Game{name='GAME 4', gameTag='game4', category='New category'}]");


        /*
         * CollectionUtils::findAll
         *
         * search all elements in collection by predicate
         */
        ArrayList<Game> gamesFromCategory1 = CollectionUtils.findAll(
                gamesCollection, game -> game.getCategory().equals("New category")
        );

        assertEquals(gamesFromCategory1.toString(), "[Game{name='GAME 1', gameTag='game1', category='New category'}, " +
                "Game{name='GAME 2', gameTag='game2', category='New category'}, Game{name='GAME 3', gameTag='game3', " +
                "category='New category'}, Game{name='GAME 4', gameTag='game4', category='New category'}]");




        /*
         * CollectionUtils::gamesCollection
         *
         * get element by index or random if index is out of bound
         */
        Game randomGame = CollectionUtils.getRandomElement(gamesCollection, 5);

        assertNotNull(randomGame);


        /*
         * CollectionUtils::getFirstFromCollection
         *
         * get first element from collection
         */
        Game firstGame = CollectionUtils.getFirstFromCollection(gamesCollection);

        assertNotNull(firstGame);

        /*
         * CollectionUtils::objGenerateSet
         *
         * generate Set of objects by supplier
         */
        Set<Game> my5Games = CollectionUtils.objGenerateSet(
                5, i -> new Game("Game " + i, "tag" + i, "category")
        );

        assertEquals(my5Games.toString(), "[Game{name='Game 0', gameTag='tag0', category='category'}, " +
                "Game{name='Game 1', gameTag='tag1', category='category'}, Game{name='Game 2', gameTag='tag2', " +
                "category='category'}, Game{name='Game 3', gameTag='tag3', category='category'}, " +
                "Game{name='Game 4', gameTag='tag4', category='category'}]");

        /*
         * CollectionUtils::objGenerate
         *
         * generate List of objects by supplier
         */
        ArrayList<Game> myAnother5Games = CollectionUtils.objGenerate(
                5, i -> new Game("Game " + i, "tag" + i, "category")
        );

        assertEquals(myAnother5Games.toString(), "[Game{name='Game 0', gameTag='tag0', category='category'}, " +
                "Game{name='Game 1', gameTag='tag1', category='category'}, Game{name='Game 2', gameTag='tag2', " +
                "category='category'}, Game{name='Game 3', gameTag='tag3', category='category'}, Game{name='Game 4', " +
                "gameTag='tag4', category='category'}]");


        /*
         * CollectionUtils::listsCombiner
         *
         * combine several lists
         */
        List<Game> combinedList = CollectionUtils.listsCombiner(gamesCollection2, gamesCollection3, gamesCollection4);

        assertEquals(combinedList.toString(), "[Game{name='GAME 1', gameTag='game1', category='New category'}, " +
                "Game{name='GAME 2', gameTag='game2', category='New category'}, Game{name='GAME 3', gameTag='game3', " +
                "category='New category'}, Game{name='GAME 4', gameTag='game4', category='New category'}]");


        /*
         * CollectionUtils::anyOkFromCollection
         *
         * check if collection contains element
         */
        boolean collectionContainsGameCategory1 = CollectionUtils.anyOkFromCollection(
                gamesCollection2,
                game -> game.getCategory().equals("category 1")
        );

        assertFalse(collectionContainsGameCategory1);

        /*
         * CollectionUtils::allNotOkFromCollection
         *
         * check if collection doesn't contain element
         */
        boolean collectionNotContainsGameCategory1 = CollectionUtils.allNotOkFromCollection(
                gamesCollection2,
                game -> game.getCategory().equals("category 1")
        );

        assertTrue(collectionNotContainsGameCategory1);


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

        assertFalse(collectionContainsOnlyGameCategory1);

    }
}
