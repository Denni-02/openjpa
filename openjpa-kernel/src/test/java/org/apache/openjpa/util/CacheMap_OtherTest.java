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

import static org.junit.Assert.*;

public class CacheMap_OtherTest {

    private CacheMap cacheMap;

    @Before
    public void setUp() {
        cacheMap = new CacheMap();
    }

   
    @Test
    public void testPutWithPinnedKeyAndNoPreviousValue() {
        String key = "pinnedKey";
        String value = "someValue";

        // pin della chiave, ma non è ancora presente alcun valore associato
        cacheMap.pin(key);

        // ora la prima put su pinnedMap dovrebbe restituire null
        Object result = cacheMap.put(key, value);

        assertNull(result);
        assertEquals(value, cacheMap.get(key)); // verifica che sia stato effettivamente inserito
    }

    
    @Test
    public void testPutWhenCacheSizeIsZero() {
        String key = "anyKey";
        String value = "anyValue";

        // disabilita la cache principale
        cacheMap.setCacheSize(0);

        Object result = cacheMap.put(key, value);

        assertNull(result);
        assertFalse(cacheMap.containsKey(key)); // verifica che non sia stato inserito
    }

    @Test
    public void testRemovePinnedKeyWithExistingMapping() {
        CacheMap cacheMap = new CacheMap();
        String key = "pinnedKey";
        String value = "someValue";

        // Pin della chiave e aggiunta valore
        cacheMap.pin(key);
        cacheMap.put(key, value); // inserimento nella pinnedMap

        // Rimozione
        Object result = cacheMap.remove(key);

        // Verifica che venga rimosso correttamente
        assertEquals(value, result);
        assertNull(cacheMap.get(key)); // deve essere stato effettivamente rimosso
    }

}
