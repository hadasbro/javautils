package com.github.hadasbro.javautils;

import com.github.hadasbro.javautils.test.classes.Game;
import com.github.hadasbro.javautils.test.classes.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings({"unused", "WeakerAccess"})
public class UtilsTest {

    Game gameA = null;

    User userA = null;

    Game gameB = new Game("Game 1", "game1", "category 1");

    Game gameC = new Game("Game 2", "game2", "category 2");

    Game gameD = new Game("Game 3", "game3", "category 1");

    Game gameE = new Game("Game 4", "game4", "category 3");

    @Test
    public void coalesceTest() {

        assertEquals(Utils.coalesce(gameA, userA, gameB, gameC).toString(), "Game{name='Game 1', gameTag='game1', " +
                "category='category 1'}");

        assertEquals(Utils.coalesce(
                () -> null,
                () -> gameB,
                () -> gameC
        ).toString(), "Game{name='Game 1', gameTag='game1', category='category 1'}");

    }

    @Test
    public void randTest(){
        int rand2 = Utils.rand(115, 200);
        assertTrue(rand2 >= 115);
        assertTrue(rand2 <= 200);
    }


    @Test
    public void arrayIncludeTest(){
        String strings[] = {"abc", "def", "ghi"};
        assertFalse(Utils.arrayInclude(strings, "defghi"));
        assertTrue(Utils.arrayInclude(strings, "def"));
    }

    @Test
    public void stringifyTest(){

        String gameStr1 = gameB.stringify();

        assertEquals(gameStr1,"com.github.hadasbro." +
                "javautils.test.classes.Game[name=Game 1,gameTag=game1,category=category 1][]");

        String gameStr2 =  gameD.deepStringify();

        assertEquals(gameStr2,"com.github.hadasbro.javautils.test.classes.Game" +
                "[name=Game 3,gameTag=game3,category=category 1][]");

    }

}
