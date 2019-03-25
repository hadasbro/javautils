package org.bitbucket.javautils;


import java.util.concurrent.CompletionException;


@SuppressWarnings({"unused", "WeakerAccess"})
final public class ThreadUtils {

    /**
     * CompletionException
     *
     * @param tex -
     * @return CompletionException
     */
    public static CompletionException excToCompletableExc(Throwable tex) {
        return  tex instanceof CompletionException
                ? (CompletionException)tex
                : new CompletionException(tex);
    }


}

