package com.df4j.xcoria.test.bits;

import com.df4j.xcoria.bits.BitStatus;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

public class BitStatusTest {

    private String[] keys = {
            "KEY01",
            "KEY02",
            "KEY03",
            "KEY04",
            "KEY05",
            "KEY06",
            "KEY07",
            "KEY08",
            "KEY09",
            "KEY10",
            "KEY11",
            "KEY12",
            "KEY13",
            "KEY14",
            "KEY15",
            "KEY16"
    };

    private Integer[] indexes = {0, 1, 5, 15, 12};

    private BitStatus status;

    @BeforeTest
    public void beforeClass() {
        status = new BitStatus(keys);
        for (int i : indexes) {
            status.setStatus(keys[i], true);
        }
    }

    @Test
    public void testSetStatus() {
        BitStatus newStatus = new BitStatus(keys);
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            newStatus.setStatus(key, true);
            assertTrue(newStatus.getStatus(key));
            newStatus.setStatus(key, false);
            assertFalse(newStatus.getStatus(key));
        }
    }

    @Test
    public void testGetStatus() {
        List<Integer> list = Arrays.asList(indexes);
        for (int i = 0; i < keys.length; i++) {
            if (list.contains(i)) {
                assertTrue(status.getStatus(keys[i]));
            } else {
                assertFalse(status.getStatus(keys[i]));
            }
        }
    }

    @Test
    public void testStream() {
        List<Integer> list = Arrays.asList(indexes);
        status.stream().forEach(x -> {
            int i = x.getIndex();
            assertEquals(keys[i], x.getKey());
            if (list.contains(i)) {
                assertTrue(x.isFlag());
            } else {
                assertFalse(x.isFlag());
            }
        });
    }
}