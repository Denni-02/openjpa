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

import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

public class ProxyManagerImpl_NewCustomProxyTest {

    private final ProxyManagerImpl proxyManager = new ProxyManagerImpl();

    @Test // T23
    public void testMockCollectionProxyReturnsSameInstance() {
        Collection<?> mockedCollection = org.mockito.Mockito.mock(Collection.class,
            org.mockito.Mockito.withSettings().extraInterfaces(Proxy.class));

        Proxy result = proxyManager.newCustomProxy(mockedCollection, true);
        assertSame(mockedCollection, result);
    }

    @Test // T24
    public void testArrayListReturnsProxyWithFoo() {
        Collection<String> original = new ArrayList<>(List.of("foo"));
        Proxy result = proxyManager.newCustomProxy(original, true);
        assertTrue(result instanceof Proxy);
        assertTrue(((Collection<?>) result).contains("foo"));
    }

    @Test // T25
    public void testHashMapReturnsProxyWithEntry() {
        Map<String, Integer> original = new HashMap<>(Map.of("a", 1));
        Proxy result = proxyManager.newCustomProxy(original, true);
        assertTrue(result instanceof Proxy);
        assertEquals(1, ((Map<?, ?>) result).get("a"));
    }

    @Test // T26
    public void testDateReturnsProxyWithSameTimestamp() {
        Date original = new Date();
        Proxy result = proxyManager.newCustomProxy(original, true);
        assertTrue(result instanceof Proxy);
        assertEquals(original.getTime(), ((Date) result).getTime());
    }

    @Test // T27
    public void testCalendarReturnsProxyWithSameTime() {
        Calendar original = Calendar.getInstance();
        Proxy result = proxyManager.newCustomProxy(original, true);
        assertTrue(result instanceof Proxy);
        assertEquals(original.getTimeInMillis(), ((Calendar) result).getTimeInMillis());
    }

    @Test // T28
    public void testArrayListReturnsProxyAutoOffFalse() {
        Collection<String> original = new ArrayList<>(List.of("foo"));
        Proxy result = proxyManager.newCustomProxy(original, false);
        assertTrue(result instanceof Proxy);
        assertTrue(((Collection<?>) result).contains("foo"));
    }

   
}
