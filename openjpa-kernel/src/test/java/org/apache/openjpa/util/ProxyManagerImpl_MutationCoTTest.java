
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

import org.apache.openjpa.util.ProxyManagerImpl;
import org.apache.openjpa.util.Proxy;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.openjpa.util.CustomDate;
import org.apache.openjpa.util.CustomCalendar;
import org.apache.openjpa.util.MyMap;
import org.apache.openjpa.util.MyList;

import java.util.*;

public class ProxyManagerImpl_MutationCoTTest {

    private ProxyManagerImpl proxyManager;

    @Before
    public void setup() {
        proxyManager = new ProxyManagerImpl();
    }

    @Test
    public void testNewMapProxyConcreteType() {
        Proxy p = proxyManager.newMapProxy(HashMap.class, String.class, String.class, null, false);
        assertNotNull(p);
    }

    @Test
    public void testNewMapProxyAbstractTypeFallback() {
        Proxy p = proxyManager.newMapProxy(SortedMap.class, String.class, String.class, null, false);
        assertNotNull(p);
        assertTrue(p instanceof SortedMap);
    }

    @Test
    public void testNewCollectionProxyConcrete() {
        Proxy p = proxyManager.newCollectionProxy(ArrayList.class, String.class, null, false);
        assertNotNull(p);
    }

    @Test
    public void testNewCollectionProxyInterfaceFallback() {
        Proxy p = proxyManager.newCollectionProxy(List.class, String.class, null, false);
        assertNotNull(p);
        assertTrue(p instanceof Collection);
    }

    @Test
    public void testCopyCollectionProxyable() {
        List<String> original = new ArrayList<>(Arrays.asList("a", "b"));
        Collection result = proxyManager.copyCollection(original);
        assertNotSame(original, result);
        assertTrue(result.contains("a"));
    }

    @Test
    public void testCopyMapProxyable() {
        Map<String, Integer> map = new TreeMap<>();
        map.put("x", 1);
        Map result = proxyManager.copyMap(map);
        assertNotSame(map, result);
        assertEquals(map, result);
    }

    @Test
    public void testCustomMapTypeCaching() {
        MyMap map = new MyMap();
        map.put("key", "val");
        Map result = proxyManager.copyMap(map);
        assertEquals("val", result.get("key"));
    }

    @Test
    public void testCustomDateCopy() {
        CustomDate d = new CustomDate();
        Object result = proxyManager.copyDate(d);
        assertTrue(result instanceof java.util.Date);
    }

    @Test
    public void testCustomCalendarCopy() {
        CustomCalendar c = new CustomCalendar();
        Object result = proxyManager.copyCalendar(c);
        assertTrue(result instanceof Calendar);
    }

    @Test
    public void testCopyCustomWithCollection() {
        List<String> list = new ArrayList<>(List.of("a", "b"));
        Object res = proxyManager.copyCustom(list);
        assertTrue(res instanceof List);
    }

    @Test
    public void testCopyCustomWithMap() {
        Map<String, String> m = new HashMap<>();
        m.put("one", "1");
        Object res = proxyManager.copyCustom(m);
        assertTrue(res instanceof Map);
    }

    @Test
    public void testCopyCustomWithDate() {
        Date d = new Date();
        Object res = proxyManager.copyCustom(d);
        assertTrue(res instanceof Date);
    }

    @Test
    public void testCopyCustomWithCalendar() {
        Calendar c = Calendar.getInstance();
        Object res = proxyManager.copyCustom(c);
        assertTrue(res instanceof Calendar);
    }

    @Test
    public void testCopyCustomNull() {
        assertNull(proxyManager.copyCustom(null));
    }

    @Test
    public void testNewCustomProxyList() {
        List<String> list = new LinkedList<>(List.of("x"));
        Proxy p = proxyManager.newCustomProxy(list, false);
        assertTrue(p instanceof Collection);
    }

    @Test
    public void testNewCustomProxyMap() {
        Map<String, String> map = new LinkedHashMap<>();
        Proxy p = proxyManager.newCustomProxy(map, false);
        assertTrue(p instanceof Map);
    }

    @Test
    public void testNewCustomProxyDate() {
        Date d = new Date();
        Proxy p = proxyManager.newCustomProxy(d, false);
        assertTrue(p instanceof Date);
    }

    @Test
    public void testNewCustomProxyCalendar() {
        Calendar c = new GregorianCalendar();
        Proxy p = proxyManager.newCustomProxy(c, false);
        assertTrue(p instanceof Calendar);
    }

    @Test
    public void testNewCustomProxyNull() {
        assertNull(proxyManager.newCustomProxy(null, false));
    }

    @Test
    public void testNewCustomProxyUnproxyable() {
        proxyManager.setUnproxyable("java.lang.String");
        assertNull(proxyManager.newCustomProxy("test", false));
    }

    @Test
    public void testCopyCustomWithProxy() {
        List<String> list = new ArrayList<>(List.of("a"));
        Proxy proxy = proxyManager.newCustomProxy(list, false);
        Object copy = proxyManager.copyCustom(proxy);
        assertTrue(copy instanceof Collection);
    }

    @Test
    public void testNewCustomProxyTwiceSameType() {
        List<String> list = new ArrayList<>(List.of("z"));
        Proxy p1 = proxyManager.newCustomProxy(list, false);
        Proxy p2 = proxyManager.newCustomProxy(list, false);
        assertEquals(p1.getClass(), p2.getClass());
    }

    @Test
    public void testProxyTypeConsistency() {
        List<String> list = new LinkedList<>();
        Proxy proxy = proxyManager.newCustomProxy(list, false);
        assertTrue(proxy.getClass().getName().contains("proxy"));
    }
}

