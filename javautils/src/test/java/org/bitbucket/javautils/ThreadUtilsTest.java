package org.bitbucket.javautils;

import org.bitbucket.javautils.test.classes.CustomException;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertTrue;

@SuppressWarnings({"unused", "WeakerAccess"})
public class ThreadUtilsTest {

    @Test
    public void excToCompletableExcTest() {

        CompletableFuture.allOf(
                CompletableFuture.supplyAsync(() -> {
                    throw ThreadUtils.excToCompletableExc(new CustomException("Example exception"));
                })
        )

        .handle((res, ex) -> {
            assertTrue(ex instanceof CustomException);
            return true;
        });
    }

}

