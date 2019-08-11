package com.github.hadasbro.javautils;

import com.github.hadasbro.javautils.test.classes.Game;
import com.github.hadasbro.javautils.test.classes.User;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings({"unused", "WeakerAccess"})
public class CollectionUtilsTest {

    Game gameA = null;

    User userA = null;

    Game gameB = new Game("Game 1", "game1", "category 1");

    Game gameC = new Game("Game 2", "game2", "category 2");

    Game gameD = new Game("Game 3", "game3", "category 1");

    Game gameE = new Game("Game 4", "game4", "category 3");

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


    @Test
    public void collGroupper() {

        Map<String, Set<Game>> grouppedByCategory = CollectionUtils.collGroupper(
                gamesCollection,
                Game::getCategory
        );


        assertEquals(grouppedByCategory.toString(), "{category 3=[Game{name='Game 4', gameTag='game4', category='category 3'}], " +
                "category 2=[Game{name='Game 2', gameTag='game2', category='category 2'}], category 1=[Game{name='Game 1', " +
                "gameTag='game1', category='category 1'}, Game{name='Game 3', gameTag='game3', category='category 1'}]}");

    }

    @Test
    public void collGroupperFlatter( ) {

        Map<String, Set<String>> gameNameByCategory = CollectionUtils.collGroupperFlatter(
                gamesCollection,
                Game::getCategory,
                Game::getName
        );

        assertEquals(gameNameByCategory.toString(), "{category 3=[Game 4], category 2=[Game 2], category 1=[Game 1, Game 3]}");

    }

    @Test
    public void advancedTransFilter() {

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

    }

    @Test
    public void transformCollection() {

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

        assertEquals(
                transformedCollection.toString(),
                "[Game{name='GAME 1', gameTag='game1', category='New category'}, Game{name='GAME 2', gameTag='game2', " +
                        "category='New category'}, Game{name='GAME 3', gameTag='game3', category='New category'}, " +
                        "Game{name='GAME 4', gameTag='game4', category='New category'}]"
        );

    }

    @Test
    public void findAll() {

        ArrayList<Game> gamesFromCategory1 = CollectionUtils.findAll(
                gamesCollection, game -> game.getCategory().equals("New category") || game.getCategory().equals("category 1")
        );

        assertTrue(gamesFromCategory1.size() > 0);

    }

    @Test
    public void anyOkFromCollection() {

        boolean collectionContainsGameCategory1 = CollectionUtils.anyOkFromCollection(
                gamesCollection2,
                game -> game.getCategory().equals("category 1")
        );

        assertTrue(collectionContainsGameCategory1);

    }

    @Test
    public void allOkFromCollection() {

        boolean collectionContainsOnlyGameCategory1 = CollectionUtils.allOkFromCollection(
                gamesCollection2,
                game -> game.getCategory().equals("category 1")
        );

        assertTrue(collectionContainsOnlyGameCategory1);

    }

    @Test
    public void allNotOkFromCollection() {

        boolean collectionNotContainsGameCategory1 = CollectionUtils.allNotOkFromCollection(
                gamesCollection2,
                game -> game.getCategory().equals("category 1")
        );

        assertFalse(collectionNotContainsGameCategory1);

    }

    public void listsCombiner() {

        List<Game> combinedList = CollectionUtils.listsCombiner(gamesCollection2, gamesCollection3, gamesCollection4);

        System.out.println(combinedList);

    }

    @Test
    public void objGenerate() {

        ArrayList<Game> myAnother5Games = CollectionUtils.objGenerate(
                5, i -> new Game("Game " + i, "tag" + i, "category")
        );

        assertTrue(CollectionUtils.findAll(myAnother5Games, g -> g.getName().startsWith("Game")).size() == 5);

    }

    @Test
    public void objGenerateSet() {

        Set<Game> my5Games = CollectionUtils.objGenerateSet(
                5, i -> new Game("Game " + i, "tag" + i, "category")
        );

        assertTrue(my5Games.size() > 0);

    }

    @Test
    public void getRandomElement(){

        Game randomGame = CollectionUtils.getRandomElement(gamesCollection, 5);

        assertTrue(randomGame instanceof Game);

    }

    @Test
    public void getFirstFromCollection(){

        Game firstGame = CollectionUtils.getFirstFromCollection(gamesCollection);

        assertTrue(firstGame instanceof Game);

    }
}

