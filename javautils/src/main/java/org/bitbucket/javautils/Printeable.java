package org.bitbucket.javautils;

/**
 * interface Printeable
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public interface Printeable {

    /**
     * print
     *
     * Print object
     *
     * @return String
     */
    default String stringify() {
        return deepStringify();
    }

    /**
     * deepPrint
     *
     * Deep print object
     *
     * @return String
     */
    default String deepStringify() {
        return (new ObjectStringifer.DeepObjectPrinter()).toString(this);
    }


}
