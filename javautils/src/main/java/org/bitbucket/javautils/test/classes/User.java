package org.bitbucket.javautils.test.classes;

import org.bitbucket.javautils.EntityTag;
import org.bitbucket.javautils.Printeable;

@SuppressWarnings({"unused", "WeakerAccess"})
public class User implements EntityTag, Printeable {

    public String name;
    public String secondName;

    User(String name) {
        this.name = name;
    }

    User(String name, String secondName) {
        this.name = name;
        this.secondName = secondName;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", secondName='" + secondName + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
