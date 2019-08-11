package com.github.hadasbro.javautils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings({"unused", "WeakerAccess"})
final public class Utils {

    /**
     * coalesce
     *
     * get first not null obj
     *
     * @param objects -
     * @param <T> -
     * @return <T> T
     */
    @SafeVarargs
    public static <T> T coalesce(Supplier<T>... objects) {

        return Arrays.stream(objects)
                .map(Supplier::get)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

    }

    /**
     * coalesce
     *
     * @param object -
     * @param <T> -
     * @return <T> T
     */
    @SafeVarargs
    public static <T> T coalesce(T... object) {

        return Stream
                .of(object)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

    }

    /**
     * rand
     *
     * @param a - x >= a
     * @param b - x < b
     * @return int
     */
    public static int rand(int a, int b) {
        return new SplittableRandom().nextInt(a, b);
    }

    /**
     * getRandString
     *
     * @param prefix -
     * @param length -
     * @return String
     */
    public static String getRandString(String prefix, int length){
        return prefix + getRandString(length - prefix.length());
    }

    /**
     * getRandString
     *
     * @param length -
     * @return String -
     */
    public static String getRandString(int length){

        Random random = new SecureRandom();

        return random.ints(48,122)
                .filter(i-> (i<57 || i>65) && (i <90 || i>97))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

    }

    /**
     * arrayInclude
     *
     * @param array -
     * @param element -
     * @param <T> -
     * @return boolean
     */
    public static <T> boolean arrayInclude(T[] array, T element){
        return Arrays.asList(array).contains(element);
    }

}
