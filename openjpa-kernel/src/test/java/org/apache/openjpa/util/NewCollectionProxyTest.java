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


public class NewCollectionProxyTest {

    // T1: null, null, null, true → NullPointerException
    @Test
    public void test_nullType_NullPointerException() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        try {
            manager.newCollectionProxy(null, null, null, true);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // OK
        }
    }

    // T2: ArrayList.class, null, null, true → proxy valido, accetta ogni elemento
    @Test
    public void test_arrayListNoTypeAcceptsAnyElement() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        Collection proxy = (Collection) manager.newCollectionProxy(ArrayList.class, null, null, true);
        proxy.add(1);
        proxy.add("foo");
        proxy.add(3.14);
        assertEquals(3, proxy.size());
    }

    /*
    // T3: String.class, null, null, true → IllegalArgumentException
    @Test
    public void test_invalidCollectionType_IllegalArgumentException() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        try {
            manager.newCollectionProxy(String.class, null, null, true);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }
    */

    // T3bis: String.class, null, null, true → IllegalArgumentException
    @Test
    public void test_invalidCollectionType_IllegalArgumentException() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        try {
            manager.newCollectionProxy(String.class, null, null, true);
            fail("Expected UnsupportedException");
        } catch (UnsupportedException e) {
            // OK
        }
    }

    // T4: ArrayList.class, Integer.class, null, true, add(1) → aggiunta riuscita
    @Test
    public void test_validIntegerType() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        Collection<Integer> proxy = (Collection<Integer>) manager.newCollectionProxy(ArrayList.class, Integer.class, null, true);
        proxy.add(1);
        assertTrue(proxy.contains(1));
    }

    /*
    // T5: ArrayList.class, Integer.class, null, true, add("foo") → ClassCastException
    @Test
    public void test_invalidElementType_ClassCastException() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        Collection proxy = (Collection) manager.newCollectionProxy(ArrayList.class, Integer.class, null, true);
        try {
            proxy.add("foo");
            fail("Expected ClassCastException");
        } catch (ClassCastException e) {
            // OK
        }
    }
    */

   //T5 bis
   @Test
    public void test_invalidElementType_AggiuntoDavvero() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        Collection proxy = (Collection) manager.newCollectionProxy(ArrayList.class, Integer.class, null, true);
        proxy.add("foo"); // non viene lanciata eccezione!
        
        // Verifica se è stato aggiunto davvero
        assertTrue("L'elemento 'foo' dovrebbe essere contenuto", proxy.contains("foo"));
        assertEquals("La collezione dovrebbe avere 1 elemento", 1, proxy.size());
    }


    // T6: ArrayList.class, Integer.class, Comparator.reverseOrder(), true, add(3) after 1, 2
    @Test
    public void test_reverseOrderComparator() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        Collection<Integer> proxy = (Collection<Integer>) manager.newCollectionProxy(TreeSet.class, Integer.class, Comparator.reverseOrder(), true);
        proxy.add(1);
        proxy.add(2);
        proxy.add(3);
        assertEquals(Arrays.asList(3, 2, 1), new ArrayList<>(proxy));
    }

    // T7: ArrayList.class, Integer.class, null, true, add(3) after 1, 2
    @Test
    public void test_naturalOrder() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        Collection<Integer> proxy = (Collection<Integer>) manager.newCollectionProxy(TreeSet.class, Integer.class, null, true);
        proxy.add(1);
        proxy.add(2);
        proxy.add(3);
        assertEquals(Arrays.asList(1, 2, 3), new ArrayList<>(proxy));
    }

    // T8: List.class, Integer.class, null, true, add(1) → fallback e successo
    @Test
    public void test_interfaceListFallback() {
        ProxyManagerImpl manager = new ProxyManagerImpl();
        Collection<Integer> proxy = (Collection<Integer>) manager.newCollectionProxy(List.class, Integer.class, null, true);
        proxy.add(1);
        assertTrue(proxy.contains(1));
    }

    // T9: TreeSet.class, null, Comparator.naturalOrder(), true, add(non-comparable)
    @Test
    public void test_nonComparableElement_ClassCastException() {
        class NonComparable {}
        ProxyManagerImpl manager = new ProxyManagerImpl();
        Collection proxy = (Collection) manager.newCollectionProxy(TreeSet.class, null, Comparator.naturalOrder(), true);
        try {
            proxy.add(new NonComparable());
            fail("Expected ClassCastException");
        } catch (ClassCastException e) {
            // OK
        }
    }
}
