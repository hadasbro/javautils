package com.github.hadasbro.javautils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

@SuppressWarnings({"unused", "WeakerAccess"})
final public class CollectionUtils {

    /**
     * collGroupper
     *
     * Group objects collection by collector
     *
     * usage:
     * Game { String category = "abc"}
     * List<Games> games = ...
     * Map<String, Set<Game> gamesPerCategory = cGroupper(games, Collectors.groupingBy(Game::getCategory))
     *
     * @param collection -
     * @param collector -
     * @param <T> -
     * @param <S> -
     * @return <T extends EntityTag, S> Map Map<S, Set<T>>
     */
    public static <T extends EntityTag, S> Map<S, Set<T>> collGroupper(
            Collection<T> collection,
            Collector<? super T, ?, Map<S,Set<T>>> collector
    ) {

        return collection
                .parallelStream()
                .collect(collector);

    }

    /**
     * collGroupper
     *
     * Group objects collection by object method result
     *
     * usage:
     * Game { Integer rating = 7;}
     * List<Game> games = ...
     * Map<Integer, Set<Game> gamesPerRating = cGroupper(games, Game::getRating))
     *
     *
     * @param collection -
     * @param objMethod -
     * @param <T> -
     * @param <S> -
     * @return <T extends EntityTag, S> Map<S, Set<T>>
     */
    public static <T extends EntityTag, S> Map<S, Set<T>> collGroupper(
            Collection<T> collection,
            Function<T,S> objMethod
    ) {

        return collection
                .parallelStream()
                .collect(
                        groupingBy(
                                objMethod,
                                Collectors.toCollection(HashSet::new)
                        )
                );

    }

    /**
     * collGroupperFlatter
     *
     * Group objects and flat to one final value
     *
     * usage:
     * Game { Integer rating = 7; String name = "game super"; }
     * List<Game> games = ...
     * Map<Integer, Set<String> gameNamesPerRating = cGroupper(games, Game::getRating, Game::getName))
     *
     * @param collection -
     * @param objMethodKey -
     * @param objMethodValue -
     * @param <T> -
     * @param <S> -
     * @param <R> -
     * @return <T extends EntityTag, S, R> Map<S, Set<R>>
     */
    public static <T extends EntityTag, S, R> Map<S, Set<R>> collGroupperFlatter(
            Collection<T> collection,
            Function<T,S> objMethodKey,
            Function<T,R> objMethodValue
    ) {

        return collection
                .parallelStream()
                .collect(
                        groupingBy(
                                objMethodKey,
                                Collectors.mapping(objMethodValue, Collectors.toCollection(HashSet::new))
                        )
                );

    }

    /**
     * advancedTransFilter
     *
     * @param collection -
     * @param filter -
     * @param transformer -
     * @param <S> -
     * @param <T> -
     * @return <S,T> Collection<T>
     */
    public static <S,T> Collection<T> advancedTransFilter(
            Collection<S> collection,
            Predicate<S> filter,
            Function<S,T> transformer
    ) {

        return
                collection
                .parallelStream()
                .filter(filter)
                .map(transformer)
                .collect(Collectors.toCollection(ArrayList::new));

    }

    /**
     * Transform collection
     *
     * @param collection -
     * @param transformers -
     * @param <T> -
     * @return <T> Collection<T>
     */
    @SafeVarargs
    public static <T> Collection<T> transformCollection(
            Collection<T> collection,
            UnaryOperator<T> ...transformers
    ) {


       Stream<T> str = collection
                .parallelStream();

        for (UnaryOperator<T> transformer : transformers) {
            str = str.map(transformer);
        }

        return str.collect(Collectors.toCollection(ArrayList::new));

    }

    /**
     * Filter collection
     *
     * @param collection -
     * @param filter -
     * @param <T> -
     * @return  <T> ArrayList<T>
     */
    public static <T> ArrayList<T> findAll(Collection<T> collection, Predicate<T> filter) {

        return
                collection
                        .parallelStream()
                        .filter(filter)
                        .collect(Collectors.toCollection(ArrayList::new));

    }

    /**
     * anyOkFromCollection
     *
     * @param collection -
     * @param condition -
     * @param <T> -
     * @return boolean
     */
    public static <T> boolean anyOkFromCollection(Collection<T> collection, Predicate<? super T> condition) {

        return collection
                .stream()
                .anyMatch(condition);

    }

    /**
     * allOkFromCollection
     *
     * @param collection -
     * @param condition -
     * @param <T> -
     * @return boolean
     */
    public static <T> boolean allOkFromCollection(Collection<T> collection, Predicate<? super T> condition) {

        return collection
                .stream()
                .allMatch(condition);

    }

    /**
     * allNotOkFromCollection
     *
     * @param collection -
     * @param condition -
     * @param <T> -
     * @return boolean
     */
    public static <T> boolean allNotOkFromCollection(Collection<T> collection, Predicate<? super T> condition) {

        return collection
                .stream()
                .noneMatch(condition);

    }

    /**
     * listsCombiner
     *
     * @param lists -
     * @param <T> -
     * @return <T> List<T>
     */
    @SafeVarargs
    static <T> List<T> listsCombiner(List<? extends T>... lists) {

        List<T> result = new ArrayList<>();

        for (List<? extends T> list : lists) {
            result.addAll(list);
        }

        return result;
    }

    /**
     * objGenerator
     *
     * Generate some fake data
     *
     * usage:
     * List<Employee> t = objGenerate(10,Employee::new);
     * List<Employee> t = objGenerate(10, e -> new Employee(1,2,3));
     *
     * @param n -
     * @param s -
     * @param <T> -
     * @return <T> List<T>
     */
    public static <T> ArrayList<T> objGenerate(Integer n, Function<Integer, T> s) {

        ArrayList<T> trg = new ArrayList<>();

        for(int i = 0; i < n; i++){
            trg.add(s.apply(i));
        }

        return trg;

    }

    /**
     * objGenerateSet
     * @param n -
     * @param s -
     * @param <T> -
     * @return Set<T>
     */
    public static <T> Set<T> objGenerateSet(Integer n, Function<Integer, T> s) {
        return new HashSet<>(objGenerate(n, s));
    }

    /**
     * getRandomElement
     *
     * @param list -
     * @param index -
     * @param <T> -
     * @return T
     */
    public static <T> T getRandomElement(List<T> list, int index){

        try {
            return list.get(index);
        } catch (IndexOutOfBoundsException ex) {
            int rnd = Utils.rand(0, list.size());
            return list.get(rnd);
        }

    }

    /**
     * toSingleton
     * @param <T> -
     * @return T
     */
    public static <T> Collector<T, ?, T> toSingleton() {

        try {

            return Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> {
                        if (list.size() != 1) {
                            return null;
                        }
                        return list.get(0);
                    }
            );

        } catch (Exception e) {
            return null;
        }

    }

    /**
     * getFirstFromCollection
     *
     * @param collection -
     * @param <E> -
     * @return E
     */
    public static <E> E getFirstFromCollection(Collection<E> collection){
        return collection
                .stream()
                .limit(1)
                .collect(CollectionUtils.toSingleton());
    }
}

