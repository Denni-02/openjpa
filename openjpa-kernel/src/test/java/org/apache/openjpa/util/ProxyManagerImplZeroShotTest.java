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

import org.apache.openjpa.util.proxy.ProxyCalendar;
import org.apache.openjpa.util.proxy.ProxyCollection;
import org.apache.openjpa.util.proxy.ProxyDate;
import org.apache.openjpa.util.proxy.ProxyMap;
import org.apache.openjpa.util.proxy.ProxyBean;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ProxyManagerImplZeroShotTest {

    private ProxyManagerImpl proxyManager;

    @Before
    public void setUp() {
        proxyManager = new ProxyManagerImpl();
    }

    @Test
    public void testGetAndSetTrackChanges() {
        proxyManager.setTrackChanges(false);
        assertFalse(proxyManager.getTrackChanges());

        proxyManager.setTrackChanges(true);
        assertTrue(proxyManager.getTrackChanges());
    }

    @Test
    public void testGetAndSetAssertAllowedType() {
        proxyManager.setAssertAllowedType(true);
        assertTrue(proxyManager.getAssertAllowedType());

        proxyManager.setAssertAllowedType(false);
        assertFalse(proxyManager.getAssertAllowedType());
    }

    @Test
    public void testGetAndSetDelayCollectionLoading() {
        proxyManager.setDelayCollectionLoading(true);
        assertTrue(proxyManager.getDelayCollectionLoading());

        proxyManager.setDelayCollectionLoading(false);
        assertFalse(proxyManager.getDelayCollectionLoading());
    }

    @Test
    public void testSetAndGetUnproxyable() {
        String classes = "java.lang.String;java.lang.Integer";
        proxyManager.setUnproxyable(classes);
        Collection unproxyables = proxyManager.getUnproxyable();
        assertTrue(unproxyables.contains("java.lang.String"));
        assertTrue(unproxyables.contains("java.lang.Integer"));
    }

    @Test
    public void testCopyArray() {
        int[] original = {1, 2, 3};
        int[] copy = (int[]) proxyManager.copyArray(original);
        assertArrayEquals(original, copy);
        assertNotSame(original, copy);
    }

    @Test(expected = UnsupportedException.class)
    public void testCopyArrayThrows() {
        Object nonArray = "not an array";
        proxyManager.copyArray(nonArray);
    }

    @Test
    public void testCopyCollection() {
        List<String> original = new ArrayList<>(Arrays.asList("a", "b"));
        Collection<?> copy = proxyManager.copyCollection(original);
        assertEquals(original, copy);
        assertNotSame(original, copy);
    }

    @Test
    public void testCopyMap() {
        Map<String, Integer> original = new HashMap<>();
        original.put("one", 1);
        Map<?, ?> copy = proxyManager.copyMap(original);
        assertEquals(original, copy);
        assertNotSame(original, copy);
    }

    @Test
    public void testCopyDate() {
        Date date = new Date();
        Date copy = proxyManager.copyDate(date);
        assertEquals(date, copy);
        assertNotSame(date, copy);
    }

    @Test
    public void testCopyCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 1);
        Calendar copy = proxyManager.copyCalendar(calendar);
        assertEquals(calendar.getTimeInMillis(), copy.getTimeInMillis());
    }

    @Test
    public void testCopyCustomWithNull() {
        assertNull(proxyManager.copyCustom(null));
    }

    @Test
    public void testNewCollectionProxy() {
        Proxy proxy = proxyManager.newCollectionProxy(List.class, String.class, null, true);
        assertNotNull(proxy);
    }

    @Test
    public void testNewMapProxy() {
        Proxy proxy = proxyManager.newMapProxy(Map.class, String.class, Integer.class, null, false);
        assertNotNull(proxy);
    }

    @Test
    public void testNewDateProxy() {
        Proxy proxy = proxyManager.newDateProxy(Date.class);
        assertNotNull(proxy);
    }

    @Test
    public void testNewCalendarProxy() {
        Proxy proxy = proxyManager.newCalendarProxy(Calendar.class, TimeZone.getDefault());
        assertNotNull(proxy);
    }

    @Test
    public void testNewCustomProxyWithNull() {
        assertNull(proxyManager.newCustomProxy(null, false));
    }

    @Test
    public void testToProxyableCollectionType() {
        assertEquals(ArrayList.class, proxyManager.toProxyableCollectionType(List.class));
    }

    @Test
    public void testToProxyableMapType() {
        assertEquals(HashMap.class, proxyManager.toProxyableMapType(Map.class));
    }

    @Test(expected = UnsupportedException.class)
    public void testToProxyableCollectionTypeFailsWithAbstract() {
        proxyManager.toProxyableCollectionType(AbstractList.class);
    }

    @Test
    public void testIsUnproxyable() {
        assertTrue(proxyManager.isUnproxyable(TimeZone.class));
        assertFalse(proxyManager.isUnproxyable(String.class));
    }

    @Test
    public void testCopyCustomCollection() {
        List<String> list = new ArrayList<>();
        list.add("test");
        Object copy = proxyManager.copyCustom(list);
        assertTrue(copy instanceof Collection);
        assertEquals(list, copy);
    }

    @Test
    public void testCopyCustomMap() {
        Map<String, String> map = new HashMap<>();
        map.put("k", "v");
        Object copy = proxyManager.copyCustom(map);
        assertTrue(copy instanceof Map);
        assertEquals(map, copy);
    }

    @Test
    public void testCopyCustomDate() {
        Date date = new Date();
        Object copy = proxyManager.copyCustom(date);
        assertTrue(copy instanceof Date);
        assertEquals(date, copy);
    }

    @Test
    public void testCopyCustomCalendar() {
        Calendar cal = Calendar.getInstance();
        Object copy = proxyManager.copyCustom(cal);
        assertTrue(copy instanceof Calendar);
        assertEquals(cal.getTimeInMillis(), ((Calendar) copy).getTimeInMillis());
    }

}
