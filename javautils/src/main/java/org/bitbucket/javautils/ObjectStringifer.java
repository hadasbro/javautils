package org.bitbucket.javautils;

import org.apache.commons.lang3.builder.ToStringStyle;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

@SuppressWarnings({"unused", "WeakerAccess"})
final public class ObjectStringifer {

    /**
     * Object Printer Style
     */
    final public static class PrintToStringStyle extends ToStringStyle {
        
        public PrintToStringStyle() {
            super();
            this.setUseClassName(false);
            this.setUseIdentityHashCode(false);
            this.setUseFieldNames(true);
        }

    }

    /**
     * Deep Obj printer (via Reflection API)
     */
    final public static class DeepObjectPrinter {

        private ArrayList<Object> elements = new ArrayList<>();

        /**
         * Object to string
         * via REFLECTION API
         *
         * @param obj -
         * @return String
         */
        public String toString(Object obj)
        {
            if (obj == null)
                return "null";

            if (elements.contains(obj))
                return "...";

            elements.add(obj);

            Class cl = obj.getClass();

            if (cl == String.class)
                return (String) obj;

            if (cl.isArray())
            {
                StringBuilder r = new StringBuilder(cl.getComponentType() + "[]{");

                for (int i = 0; i < Array.getLength(obj); i++)
                {
                    if (i > 0) r.append(",");

                    Object val = Array.get(obj, i);

                    if (cl.getComponentType().isPrimitive())
                        r.append(val);
                    else
                        r.append(toString(val));

                }

                return r + "}";

            }

            StringBuilder r = Optional
                    .ofNullable(cl.getName())
                    .map(StringBuilder::new)
                    .orElse(null);

            do {
                r = (r == null ? new StringBuilder("null") : r).append("[");
                Field[] fields = cl.getDeclaredFields();
                AccessibleObject.setAccessible(fields, true);

                for (Field f : fields)
                {
                    if (!Modifier.isStatic(f.getModifiers()))
                    {
                        if (!r.toString().endsWith("[")) r.append(",");
                        r.append(f.getName()).append("=");
                        try
                        {
                            Class t = f.getType();
                            Object val = f.get(obj);

                            if (t.isPrimitive())
                                r.append(val);
                            else
                                r.append(toString(val));

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }

                r.append("]");
                cl = cl.getSuperclass();

            } while (cl != null);

            return r.toString();
        }
    }

    /**
     * stringify
     *
     * @param col -
     * @return String
     */
    public static String stringify(Collection<Object> col) {

        StringBuilder strBuff = new StringBuilder().append(Collection.class).append( "[ ");

        col
                .parallelStream()
                .filter(Objects::nonNull)
                .map(ObjectStringifer::stringify)
                .forEach(strBuff::append);

        strBuff.append(" ]");

        return strBuff.toString();

    }

    /**
     * stringify
     *
     * @param obj -
     * @return String
     */
    public static String stringify(Object[] obj) {

        StringBuilder strBuff = new StringBuilder().append("Array [ ");

        Arrays
                .stream(obj)
                .filter(Objects::nonNull)
                .map(ObjectStringifer::stringify)
                .forEach(strBuff::append);

        strBuff.append(" ]");

        return strBuff.toString();
    }

    /**
     * stringify
     *
     * recursive function
     *
     * @param obj -
     * @return String
     */
    public static String stringify(Object obj) {

        if (obj instanceof Printeable) {
            return ((Printeable)obj).stringify();
        } else if(obj instanceof Number){
            return obj.toString();
        }

        return String.valueOf(obj);

    }

    /**
     * print
     *
     * Object printer
     *
     * @param obj -
     */
    public static void print(Object obj) {
        System.out.println(stringify(obj));
    }

    /**
     * deepPrint
     *
     * @param obj -
     */
    public static void deepPrint(Printeable obj) {
        System.out.println(obj.deepStringify());
    }

}
