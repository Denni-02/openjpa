/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.openjpa.util;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class ProxyManagerImpl_CopyCollectionTest {

    /*
    // T10: orig == null → NullPointerException
    @Test
    public void test_copyNull_throwsException() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        try {
            manager.copyCollection(null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // OK
        }
    }
    */

    // T10bis: origin == null → restituisce null
    @Test
    public void test_copyNull_returnsNull() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        Collection result = manager.copyCollection(null);
        assertNull("Expected null to be returned when input is null", result);
    }

    // T11: orig vuota → collezione vuota copiata
    @Test
    public void test_copyEmptyCollection() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        Collection<Object> empty = new ArrayList<>();
        Collection<Object> copy = manager.copyCollection(empty);
        assertNotNull(copy);
        assertEquals(0, copy.size());
    }

    // T12: collezione con un solo elemento immutabile
    @Test
    public void test_copySingleImmutableElement() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        Collection<String> original = new ArrayList<>(List.of("foo"));
        Collection<String> copy = manager.copyCollection(original);
        assertEquals(1, copy.size());
        assertTrue(copy.contains("foo"));
    }

    /*
    // T13: elemento mutabile deve essere copiato con riferimento diverso (isolamento)
    @Test
    public void test_copySingleMutableElement_independence() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        StringBuilder sb = new StringBuilder("abc");
        Collection<StringBuilder> orig = List.of(sb);
        Collection<?> copy = manager.copyCollection(orig);

        StringBuilder copied = (StringBuilder) copy.iterator().next();
        assertEquals(sb.toString(), copied.toString()); // contenuto uguale
        assertNotSame(sb, copied); // ma riferimenti diversi
    }
    */

    // T13 bis: collezione con un solo elemento mutabile
    @Test
    public void test_copySingleMutableElement() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        StringBuilder sb = new StringBuilder("abc");
        Collection<StringBuilder> original = new ArrayList<>(List.of(sb));
        Collection<StringBuilder> copy = manager.copyCollection(original);

        // modifica l'elemento nella copia
        copy.iterator().next().append("def");

        // anche l'originale sarà cambiato (shallow copy)
        assertEquals("abcdef", sb.toString());
    }

    /*
    // T14: modifica alla copia non deve riflettersi sull'originale (deep copy)
    @Test
    public void test_copyMultipleMutableElements() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        StringBuilder sb1 = new StringBuilder("one");
        StringBuilder sb2 = new StringBuilder("two");
        Collection<StringBuilder> orig = new ArrayList<>(Arrays.asList(sb1, sb2));
        Collection<?> copy = manager.copyCollection(orig);

        Iterator<?> it = copy.iterator();
        StringBuilder c1 = (StringBuilder) it.next();
        StringBuilder c2 = (StringBuilder) it.next();

        c1.append("-changed");

        assertEquals("one", sb1.toString());             // originale invariato
        assertEquals("one-changed", c1.toString());     // copia modificata
        assertNotSame(sb1, c1);                          // riferimenti diversi
    }
    */

    // T14bis: verifica se oggetti mutabili sono gli stessi riferimenti
     @Test
    public void test_copyMultipleMutableElements_shallowCopy() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        StringBuilder sb1 = new StringBuilder("one");
        StringBuilder sb2 = new StringBuilder("two");
        Collection<StringBuilder> original = new ArrayList<>(List.of(sb1, sb2));
        Collection<StringBuilder> copy = manager.copyCollection(original);

        Iterator<StringBuilder> it = copy.iterator();
        StringBuilder first = it.next();
        first.append("-changed");

        assertEquals("one-changed", sb1.toString());
    }

    // T15: collezione con elementi null → devono essere presenti anche nella copia
    @Test
    public void test_copyWithNullElements() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        Collection<String> orig = new ArrayList<>();
        orig.add(null);
        orig.add("abc");

        Collection<?> copy = manager.copyCollection(orig);

        assertEquals(2, copy.size());
        assertTrue(copy.contains(null));
        assertTrue(copy.contains("abc"));
    }



}
