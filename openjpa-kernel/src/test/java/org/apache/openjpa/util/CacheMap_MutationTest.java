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

import java.lang.reflect.Field;
import static org.junit.Assert.*;
import org.junit.Test;

public class CacheMap_MutationTest {

    private int getPinnedSize(CacheMap map) {
        try {
            Field f = CacheMap.class.getDeclaredField("_pinnedSize");
            f.setAccessible(true);
            return f.getInt(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test del costruttore di default: verifica che la mappa venga creata
     * con capacità 1000 e senza politica LRU.
     */
    @Test
    public void testDefaultConstructor() {
        CacheMap map = new CacheMap();
        assertEquals(1000, map.getCacheSize());
        assertFalse(map.isLRU());
    }

    /**
     * Verifica l'attivazione della politica LRU.
     */
    @Test
    public void testLruTrueConstructor() {
        CacheMap map = new CacheMap(true);
        assertEquals(1000, map.getCacheSize());
        assertTrue(map.isLRU());
    }

    /**
     * Verifica che la politica non-LRU sia impostata correttamente.
     */
    @Test
    public void testLruFalseConstructor() {
        CacheMap map = new CacheMap(false);
        assertEquals(1000, map.getCacheSize());
        assertFalse(map.isLRU());
    }

    /**
     * Verifica l'utilizzo del parametro max nel costruttore.
     */
    @Test
    public void testMaxSizeConstructor() {
        CacheMap map = new CacheMap(false, 200);
        assertEquals(200, map.getCacheSize());
        map.put("k1", "v1");
        assertEquals("v1", map.get("k1"));
    }

    /**
     * Test costruttore deprecato con parametri espliciti.
     */
    @Test
    public void testDeprecatedConstructor() {
        CacheMap map = new CacheMap(false, 128, 64, 0.8f);
        assertEquals(128, map.getCacheSize());
        map.put("key", "value");
        assertEquals("value", map.get("key"));
    }

    @Test
    public void testPinnedSizeIncrementOnPin() {
        CacheMap map = new CacheMap();
        map.put("k", "v");
        int before = getPinnedSize(map);
        boolean result = map.pin("k");
        int after = getPinnedSize(map);
        assertTrue(result);
        assertEquals(before + 1, after);
    }

    @Test
    public void testPinnedSizeDecrementOnUnpin() {
        CacheMap map = new CacheMap();
        map.put("k", "v");
        map.pin("k");
        int before = getPinnedSize(map);
        boolean result = map.unpin("k");
        int after = getPinnedSize(map);
        assertTrue(result);
        assertEquals(before - 1, after);
    }

    @Test
    public void testPinNonExistentKeyReturnsFalse() {
        CacheMap map = new CacheMap();
        int before = getPinnedSize(map);
        boolean result = map.pin("nonexistent");
        int after = getPinnedSize(map);
        assertFalse(result);
        assertEquals(before, after);
    }

    @Test
    public void testUnpinNonExistentKeyReturnsFalse() {
        CacheMap map = new CacheMap();
        int before = getPinnedSize(map);
        boolean result = map.unpin("nonexistent");
        int after = getPinnedSize(map);
        assertFalse(result);
        assertEquals(before, after);
    }

    @Test
    public void testPinnedSizeIncrementedOnNewPinnedInsert() {
        CacheMap map = new CacheMap();
        map.put("k", "v1");
        map.pin("k");
        int before = getPinnedSize(map);
        map.put("k", "v2");
        int after = getPinnedSize(map);
        assertEquals(before, after);
    }

    @Test
    public void testPinnedSizeIncrementedWhenNotPresent() {
        CacheMap map = new CacheMap();
        Object result = map.put("newkey", "val");
        assertNull(result);
        map.pin("newkey");
        int before = getPinnedSize(map);
        map.put("newkey", "updated");
        int after = getPinnedSize(map);
        assertEquals(before, after);
    }

    @Test
    public void testPinnedSizeNotIncreasedOnOverride() {
        CacheMap map = new CacheMap();
        map.put("x", "1");
        map.pin("x");
        int before = getPinnedSize(map);
        map.put("x", "1");
        int after = getPinnedSize(map);
        assertEquals(before, after);
    }
}
