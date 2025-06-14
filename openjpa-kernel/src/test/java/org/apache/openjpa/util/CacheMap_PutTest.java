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

import org.apache.openjpa.util.CacheMap;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class CacheMap_PutTest {

    private CacheMap cache;

    @Before
    public void setUp() {
        cache = new CacheMap(); // default cacheMap, non-LRU, size 1000
    }

    // T1: key = null, value = someValue → NullPointerException
    /*@Test(expected = NullPointerException.class)
    public void testPut_NullKey() {
        cache.put(null, "someValue");
    }*/

   // T1bis: key = null, value = someValue → accettato
    @Test
    public void testPut_NullKey_ShouldBeAccepted() {
        try {
            Object result = cache.put(null, "someValue");
            assertNull("Il valore restituito dovrebbe essere null", result);
            assertTrue("La chiave null dovrebbe essere presente", cache.containsKey(null));
            assertEquals("someValue", cache.get(null));
        } catch (Exception e) {
            fail("CacheMap ha lanciato un'eccezione imprevista: " + e);
        }
    }

    // T2 e T2bis: key = newKey, value = null → comportamento da verificare
    /*@Test(expected = NullPointerException.class)
    public void testPut_NullValue_ShouldThrow() {
        cache.put("newKey", null);
    }*/

    @Test
    public void testPut_NullValue_ShouldBeAccepted() {
        try {
            Object result = cache.put("newKey", null);
            assertNull("Il valore restituito dovrebbe essere null", result);
            assertTrue("La chiave dovrebbe essere presente", cache.containsKey("newKey"));
            assertNull("Il valore associato alla chiave dovrebbe essere null", cache.get("newKey"));
        } catch (NullPointerException e) {
            fail("CacheMap non accetta valori nulli");
        }
    }


    // T3: key = newKey, value = someValue → put correttamente, ritorna null
    @Test
    public void testPut_NewKey() {
        Object result = cache.put("newKey", "someValue");
        assertNull(result); // nessun valore precedente
        assertEquals("someValue", cache.get("newKey"));
    }

    // T4: key = existingKey, value = updatedValue → sovrascrive, ritorna valore precedente
    @Test
    public void testPut_OverwriteExistingKey() {
        cache.put("existingKey", "oldValue");
        Object result = cache.put("existingKey", "newValue");
        assertEquals("oldValue", result);
        assertEquals("newValue", cache.get("existingKey"));
    }

    // T5: key = pinnedKey, value = updatedValue → put avviene, chiave resta pinnata
    @Test
    public void testPut_OnPinnedKey() {
        cache.put("pinnedKey", "initialValue");
        cache.pin("pinnedKey");

        Object result = cache.put("pinnedKey", "updatedValue");

        assertEquals("initialValue", result);
        assertEquals("updatedValue", cache.get("pinnedKey"));

        Set pinnedKeys = cache.getPinnedKeys();
        assertTrue(pinnedKeys.contains("pinnedKey"));
    }
}
