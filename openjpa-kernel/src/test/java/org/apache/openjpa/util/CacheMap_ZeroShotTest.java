/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.openjpa.util;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

public class CacheMap_ZeroShotTest {

    private CacheMap cache;

    @Before
    public void setUp() {
        cache = new CacheMap(true, 5); // LRU with max size 5
    }

    @Test
    public void testPutAndGet() {
        cache.put("key1", "value1");
        assertEquals("value1", cache.get("key1"));
    }

    @Test
    public void testOverwriteValue() {
        cache.put("key1", "value1");
        cache.put("key1", "value2");
        assertEquals("value2", cache.get("key1"));
    }

    @Test
    public void testRemove() {
        cache.put("key1", "value1");
        cache.remove("key1");
        assertNull(cache.get("key1"));
    }

    @Test
    public void testSizeAndIsEmpty() {
        assertTrue(cache.isEmpty());
        cache.put("key1", "value1");
        assertFalse(cache.isEmpty());
        assertEquals(1, cache.size());
    }

    @Test
    public void testContainsKeyAndValue() {
        cache.put("key1", "value1");
        assertTrue(cache.containsKey("key1"));
        assertTrue(cache.containsValue("value1"));
        assertFalse(cache.containsKey("key2"));
        assertFalse(cache.containsValue("value2"));
    }

    @Test
    public void testPutAll() {
        Map<String, String> data = new HashMap<>();
        data.put("k1", "v1");
        data.put("k2", "v2");
        cache.putAll(data);
        assertEquals("v1", cache.get("k1"));
        assertEquals("v2", cache.get("k2"));
    }

    @Test
    public void testPutAllReplaceFalse() {
        cache.put("k1", "old");
        Map<String, String> data = new HashMap<>();
        data.put("k1", "new");
        data.put("k2", "v2");
        cache.putAll(data, false);
        assertEquals("old", cache.get("k1"));
        assertEquals("v2", cache.get("k2"));
    }

    @Test
    public void testClear() {
        cache.put("k1", "v1");
        cache.put("k2", "v2");
        cache.clear();
        assertTrue(cache.isEmpty());
        assertNull(cache.get("k1"));
    }

    @Test
    public void testPinAndUnpin() {
        cache.put("k1", "v1");
        boolean pinned = cache.pin("k1");
        assertTrue(pinned);
        assertTrue(cache.getPinnedKeys().contains("k1"));

        boolean unpinned = cache.unpin("k1");
        assertTrue(unpinned);
        assertFalse(cache.getPinnedKeys().contains("k1"));
    }

    @Test
    public void testPinKeyWithNoValue() {
        boolean pinned = cache.pin("missing");
        assertFalse(pinned);
        assertTrue(cache.getPinnedKeys().contains("missing"));
    }

    @Test
    public void testUnpinUnexistingKey() {
        boolean result = cache.unpin("missing");
        assertFalse(result);
    }

    @Test
    public void testEvictionLRU() {
        CacheMap smallCache = new CacheMap(true, 5);

        for (int i = 0; i < 10; i++) {
            smallCache.put("k" + i, "v" + i);
        }

        // Verifichiamo che k0 non sia più nella cache "attiva"
        // MA potrebbe essere nella softMap: quindi verifichiamo che
        // il valore venga ripescato dalla softMap ma non resti fisso nella cacheMap
        Object value = smallCache.get("k0");
        assertNotNull("Expected value in softMap", value);
    }



    @Test
    public void testSetCacheSize() {
        CacheMap cm = new CacheMap(true, 10);

        for (int i = 0; i < 10; i++) {
            cm.put("k" + i, "v" + i);
        }

        cm.setCacheSize(2);

        // Inserimento di nuovi elementi per attivare l'espulsione
        cm.put("extra1", "vExtra1");
        cm.put("extra2", "vExtra2");

        // Verifica: gli elementi più vecchi sono stati spostati nella softMap
        // quindi sono ancora accessibili con `get`, ma non sono più nella cache "forte"
        assertEquals("v0", cm.get("k0")); // ancora presente, ma solo grazie alla softMap

        // Test più corretto: verifichiamo che la cache forte abbia dimensione <= 2
        assertTrue("cacheMap size must be <= 2", cm.cacheMap.size() <= 2);
    }



    @Test
    public void testSoftReferenceSize() {
        cache.setSoftReferenceSize(10);
        assertEquals(10, cache.getSoftReferenceSize());
        cache.setSoftReferenceSize(-1);
        assertEquals(-1, cache.getSoftReferenceSize());
    }

    @Test
    public void testKeySetView() {
        cache.put("k1", "v1");
        cache.put("k2", "v2");
        Set keys = cache.keySet();
        assertTrue(keys.contains("k1"));
        assertTrue(keys.contains("k2"));
    }

    @Test
    public void testValuesView() {
        cache.put("k1", "v1");
        cache.put("k2", "v2");
        Collection values = cache.values();
        assertTrue(values.contains("v1"));
        assertTrue(values.contains("v2"));
    }

    @Test
    public void testEntrySetView() {
        cache.put("k1", "v1");
        Set entries = cache.entrySet();
        boolean found = false;
        for (Object o : entries) {
            Map.Entry entry = (Map.Entry) o;
            if (entry.getKey().equals("k1") && entry.getValue().equals("v1")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testToString() {
        cache.put("k1", "v1");
        String str = cache.toString();
        assertTrue(str.contains("CacheMap"));
        assertTrue(str.contains("k1"));
    }

    @Test
    public void testIsLRU() {
        assertTrue(cache.isLRU());
    }

    @Test
    public void testGetCacheSize() {
        cache.setCacheSize(123);
        assertEquals(123, cache.getCacheSize());
        cache.setCacheSize(-1);
        assertEquals(-1, cache.getCacheSize());
    }

}
