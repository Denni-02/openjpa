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
import java.util.*;
import java.util.Comparator;
import java.util.Calendar;
import java.util.Date;
import org.apache.openjpa.util.ProxyManagerImpl;
import org.apache.openjpa.util.Proxy;

import static org.mockito.Mockito.*;

public class ProxyManagerImpl_OtherTest {

    private final ProxyManagerImpl proxyManager = new ProxyManagerImpl();

    @Test
    public void testSetUnproxyableWithNull() { // T15
        proxyManager.setUnproxyable(null); 
        // non ci sono eccezioni
    }

    @Test
    public void testCopyArrayWithNull() { // T16
        Object result = proxyManager.copyArray(null);
        assertNull(result); // deve tornare null
    }

    @Test
    public void testCopyCollectionWithRealCollection() { //T17
        Collection<String> original = new ArrayList<>();
        original.add("A");

        Collection<String> result = proxyManager.copyCollection(original);

        assertNotNull(result);
        assertEquals(original.size(), result.size());
        assertTrue(result.contains("A"));
    }

    @Test
    public void testCopyMapWithRealMap() { // T18
        Map<String, String> original = new HashMap<>();
        original.put("key", "value");

        Map<String, String> result = proxyManager.copyMap(original);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("value", result.get("key"));
    }


    @Test // T19
    public void testNewMapProxyKeyTypeAndElementTypeNotNull() {
        Proxy proxy = proxyManager.newMapProxy(HashMap.class, String.class, Integer.class, null, false);
        assertNotNull(proxy);
    }

    @Test 
    public void testCopyDateWithRealDate() { // T20
        Date original = new Date();
        Date result = proxyManager.copyDate(original);

        assertNotNull(result);
        assertEquals(original.getTime(), result.getTime());
    }


    @Test
    public void testCopyCalendarWithRealCalendar() { // T21
        Calendar original = Calendar.getInstance();
        original.setTimeInMillis(System.currentTimeMillis());

        Calendar result = proxyManager.copyCalendar(original);

        assertNotNull(result);
        assertEquals(original.getTimeInMillis(), result.getTimeInMillis());
    }


    public static class CustomClass {}

    @Test // T22
    public void testCopyCustomWithRealCustomClass() {
        CustomClass original = new CustomClass();

        Object result = proxyManager.copyCustom(original);

        assertTrue(result == null || result instanceof CustomClass);
    }


}
