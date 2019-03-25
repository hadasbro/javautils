package org.bitbucket.javautils;

import javax.persistence.Table;

@SuppressWarnings("unused")
public interface EntityTag{

    int STATUS_PRODUCTION = 1;
    int STATUS_STAGING = 2;
    int STATUS_HIDDEN = 3;

    static String getTable(Class<?> clazz){
        Table table = clazz.getAnnotation(Table.class);
        if(table == null) {
            return null;
        }
        return table.name();
    }
}
