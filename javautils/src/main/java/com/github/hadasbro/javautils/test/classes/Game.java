package com.github.hadasbro.javautils.test.classes;

import com.github.hadasbro.javautils.EntityTag;
import com.github.hadasbro.javautils.Printeable;

import java.util.Objects;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Game implements EntityTag, Printeable {

    public String name;
    public String gameTag;
    public String category;

    public Game(String name, String gameTag, String category) {
        this.name = name;
        this.gameTag = gameTag;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGameTag() {
        return gameTag;
    }

    public void setGameTag(String gameTag) {
        this.gameTag = gameTag;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", gameTag='" + gameTag + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(name, game.name) &&
                Objects.equals(gameTag, game.gameTag) &&
                Objects.equals(category, game.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gameTag, category);
    }
}

