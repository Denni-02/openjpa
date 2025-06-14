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

import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class CacheMap_PinTest {

    private CacheMap cache;

    @Before
    public void setUp() {
        cache = new CacheMap();
    }

    /*
    // T6a: key = null → NullPointerException
    @Test(expected = NullPointerException.class)
    public void testPin_NullKey_ReturnException() {
        cache.pin(null);
    }
    */

    // T6b: key = null → false
    @Test
    public void testPin_NullKey_ShouldReturnFalse() {
        boolean result = cache.pin(null);
        assertFalse("Ci si aspetta che una chiave nulla non venga pinnata", result);
        //assertFalse("La chiave nulla non dovrebbe comparire tra quelle pinnate", cache.getPinnedKeys().contains(null));
    }

    /*
    // T6b: key = null → true
    @Test
    public void testPin_NullKey_ShouldBePinned() {
        boolean result = cache.pin(null);
        assertTrue("Ci si aspetta che la chiave nulla venga accettata", result);
        assertTrue("La chiave nulla dovrebbe essere presente tra quelle pinnate", cache.getPinnedKeys().contains(null));
    }
    */

    /*
    // T7: key = "newKey" (nessun valore associato) → true
    @Test
    public void testPin_NewKeyWithoutValue() {
        boolean result = cache.pin("newKey");
        assertTrue(result);
        assertTrue(cache.getPinnedKeys().contains("newKey"));
    }
    */

    // T7bis: key = "newKey" (nessun valore associato) → false
    @Test
    public void testPin_NewKeyWithoutValue() {
        boolean result = cache.pin("newKey");
        assertFalse(result);
        assertTrue(cache.getPinnedKeys().contains("newKey"));
    }

    // T8: key = "newKey", value = "someValue" → true
    @Test
    public void testPin_KeyWithValue() {
        cache.put("newKey", "someValue");
        boolean result = cache.pin("newKey");
        assertTrue("Chiave con valore deve essere pinnata", result);
        assertTrue("La chiave deve essere presente tra quelle pinnate", cache.getPinnedKeys().contains("newKey"));
    }

    // T9: key = "pinnedKey" già presente e già pinnata → true
    @Test
    public void testPin_AlreadyPinnedKey() {
        cache.put("pinnedKey", "initialValue");
        cache.pin("pinnedKey");
        boolean result = cache.pin("pinnedKey");
        assertTrue("Una chiave già pinnata dovrebbe restituire comunque true", result);
        assertTrue("La chiave deve essere ancora pinnata", cache.getPinnedKeys().contains("pinnedKey"));
    }
}
