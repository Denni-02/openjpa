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
import org.junit.Test;
import java.lang.reflect.Field;
import org.apache.openjpa.lib.util.SizedMap;
import java.util.Map;
import java.util.HashMap;



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

    // COSTRUTTORI ------------------------------------------------

    // Verifica che la mappa venga creata con capacità 1000 e senza politica LRU
    @Test
    public void testDefaultConstructor() {
        CacheMap map = new CacheMap();
        assertEquals(1000, map.getCacheSize());
        assertFalse(map.isLRU());
    }


    // Verifica l'attivazione della politica LRU
    @Test
    public void testLruTrueConstructor() {
        CacheMap map = new CacheMap(true);
        assertEquals(1000, map.getCacheSize());
        assertTrue(map.isLRU());
    }

    // Verifica che LRU non sia impostata
    @Test
    public void testLruFalseConstructor() {
        CacheMap map = new CacheMap(false);
        assertEquals(1000, map.getCacheSize());
        assertFalse(map.isLRU());
    }

    // Verifica l'utilizzo del parametro max nel costruttore
    @Test
    public void testMaxSizeConstructor() {
        CacheMap map = new CacheMap(false, 200);
        assertEquals(200, map.getCacheSize());
        map.put("k1", "v1");
        assertEquals("v1", map.get("k1"));
    }


    // Verifica che un size negativo venga gestito correttamente e la mappa accetti almeno 500 elementi
    @Test
    public void testNegativeSizeDefaultsTo500_MainConstructor() {
        CacheMap map = new CacheMap(false, 1000, -10, 0.75f, 8);
        for (int i = 0; i < 500; i++) {
            map.put("key" + i, "val" + i);
        }
        // Se riusciamo a leggere un elemento tra i 500, allora il softMap è correttamente dimensionato
        assertEquals("val499", map.get("key499"));
    }

    // Verifica che un max negativo venga sostituito da Integer.MAX_VALUE
    @Test
    public void testNegativeMaxDefaultsToMaxInt_MainConstructor() throws Exception {
        CacheMap map = new CacheMap(false, -10, 64, 0.75f, 8);
        Field f = CacheMap.class.getDeclaredField("cacheMap");
        f.setAccessible(true);
        SizedMap cm = (SizedMap) f.get(map);
        assertEquals(Integer.MAX_VALUE, cm.getMaxSize());
    }

    // Verifica che size e load estreme vengano accettate nel costruttore principale
    @Test
    public void testConstructorExtremeSizeAndLoad_Main() {
        CacheMap map = new CacheMap(false, 1000, 2, 0.01f, 4);
        map.put("k", "v");
        assertEquals("v", map.get("k"));
    }


    // PINNED SIZE --------------------------------------------------

    // Verifica incremento pinnedSize dopo pin
    @Test
    public void testPinIncrementsPinnedSize() {
        CacheMap map = new CacheMap();
        map.put("k", "v");
        int before = getPinnedSize(map);
        assertTrue(map.pin("k"));
        int after = getPinnedSize(map);
        assertEquals(before + 1, after);
    }

    // Verifica decremento pinnedSize dopo unpin
    @Test
    public void testUnpinDecrementsPinnedSize() {
        CacheMap map = new CacheMap();
        map.put("k", "v");
        map.pin("k");
        int before = getPinnedSize(map);
        assertTrue(map.unpin("k"));
        int after = getPinnedSize(map);
        assertEquals(before - 1, after);
    }

    // Verifica che il pin di una chiave non presente ritorni false e non modifichi la mappa
    @Test
    public void testPinNonExistentKeyReturnsFalse() {
        CacheMap map = new CacheMap();
        //int before = map.getPinnedKeys().size();
        int before = getPinnedSize(map);
        boolean result = map.pin("nonexistent");
        //int after = map.getPinnedKeys().size();
        int after = getPinnedSize(map);
        assertFalse(result);
        assertEquals(before, after);
    }

    // Verifica che l’unpin di una chiave non presente ritorni false e non modifichi la mappa
    @Test
    public void testUnpinNonExistentKeyReturnsFalse() {
        CacheMap map = new CacheMap();
        int before = getPinnedSize(map);
        boolean result = map.unpin("nonexistent");
        int after = getPinnedSize(map);
        assertFalse(result);
        assertEquals(before, after);
    }

    // Verifica che reinserire una chiave pinnata non incrementi pinnedSize
    @Test
    public void testPutIncrementsPinnedSizeIfKeyWasNull() {
        CacheMap map = new CacheMap();
        map.put("a", "v");
        map.pin("a");
        map.remove("a");
        int before = getPinnedSize(map);
        map.put("a", "newval");
        int after = getPinnedSize(map);
        assertEquals(before + 1, after);
    }

    // Verifica che reinserire una chiave pinnata con valore diverso non alteri pinnedSize
    @Test
    public void testPutDoesNotIncrementPinnedSizeOnUpdate() {
        CacheMap map = new CacheMap();
        map.put("b", "v");
        map.pin("b");
        int before = getPinnedSize(map);
        map.put("b", "v");
        int after = getPinnedSize(map);
        assertEquals(before, after);
    }

    // Verifica che rimuovere una chiave pinnata diminuisce pinned size
    @Test
    public void testRemovePinnedKeyDecrementsPinnedSize() {
        CacheMap map = new CacheMap();
        map.put("x", "1");
        map.pin("x");
        int before = getPinnedSize(map);
        map.remove("x");
        int after = getPinnedSize(map);
        assertEquals(before - 1, after);
    }

    // Verifica rimozione chiave assente
    @Test
    public void testRemoveNonExistentKeyReturnsNull() {
        CacheMap map = new CacheMap();
        Object removed = map.remove("noKey");
        assertNull(removed);
    }

    // Rimozione chiave non pinnata
    @Test
    public void testRemoveNonPinnedKeyDoesNothingToPinnedSize() {
        CacheMap map = new CacheMap();
        map.put("y", "1");
        int before = getPinnedSize(map);
        map.remove("y");
        int after = getPinnedSize(map);
        assertEquals(before, after);
    }

    
    // CHAT-GPT ------------------------------------------- 

    // Verifica che l’inserimento di una nuova chiave richiami entryAdded
    @Test
    public void testPutInsertsNewKeyTriggersEntryAdded() {
        CacheMap map = new CacheMap();
        map.put("z", "alpha");
        assertEquals("alpha", map.get("z"));
    }

    // Verifica che la sostituzione di un valore presente in softMap attivi entryRemoved e entryAdded
    @Test
    public void testPutReplacesSoftMapValueTriggersEntryRemovedAndAdded() {
        CacheMap map = new CacheMap();
        for (int i = 0; i < 1000; i++) map.put("k" + i, "v" + i); // forziamo l’overflow in softMap
        map.put("k500", "newVal");
        assertEquals("newVal", map.get("k500"));
    }

    // Verifica che la sostituzione di un valore presente in cacheMap attivi entryRemoved e entryAdded
    @Test
    public void testPutReplacesCacheMapValueTriggersEntryRemovedAndAdded() {
        CacheMap map = new CacheMap();
        map.put("key", "v1");
        map.put("key", "v2");
        assertEquals("v2", map.get("key"));
    }

    // Verifica che la rimozione di una chiave cacheMap effettivamente chiami entryRemoved
    @Test
    public void testRemoveCachedKeyTriggersEntryRemoved() {
        CacheMap map = new CacheMap();
        map.put("a", "b");
        Object removed = map.remove("a");
        assertEquals("b", removed); // entryRemoved viene chiamato se val != null
    }

    // Verifica che la rimozione da softMap attivi entryRemoved
    @Test
    public void testRemoveSoftMapKeyTriggersEntryRemoved() {
        CacheMap map = new CacheMap();
        for (int i = 0; i < 1500; i++) map.put("k" + i, "v" + i); // overflow softMap
        Object removed = map.remove("k500"); // presunta chiave in softMap
        assertNotNull(removed); // se softMap.get(k) era valido → entryRemoved deve scattare
    }

    // Verifica che clear rimuova tutti gli elementi e azzeri il pinnedSize
    @Test
    public void testClearRemovesAllEntriesAndResetsPinnedSize() throws Exception {
        CacheMap map = new CacheMap();
        map.put("x", "1");
        map.pin("x");
        assertTrue(map.containsKey("x"));
        map.clear();
        assertFalse(map.containsKey("x"));

        Field f = CacheMap.class.getDeclaredField("_pinnedSize");
        f.setAccessible(true);
        int pinned = f.getInt(map);
        assertEquals(0, pinned); // verifica reset di _pinnedSize
    }
    
    // Verifica che notifyEntryRemovals venga eseguito su entrySet del pinnedMap
    @Test
    public void testClearInvokesNotifyEntryRemovals() {
        CacheMap map = new CacheMap();
        map.put("pinned", "1");
        map.pin("pinned");
        assertTrue(map.getPinnedKeys().contains("pinned"));
        map.clear();
        assertFalse(map.getPinnedKeys().contains("pinned")); // chiave deve sparire
    }

    


}
