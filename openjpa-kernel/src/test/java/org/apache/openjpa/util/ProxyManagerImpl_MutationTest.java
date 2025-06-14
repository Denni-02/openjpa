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

import org.apache.openjpa.util.proxy.ProxyMap;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ProxyManagerImpl_MutationTest {

    private ProxyManagerImpl proxyManager;

    @Before
    public void setUp() {
        proxyManager = new ProxyManagerImpl();
    }

    // T29
    @Test
    public void testNewMapProxyWithAssertTypeTrue() {
        proxyManager.setAssertAllowedType(true);
        Proxy proxy = proxyManager.newMapProxy(HashMap.class, String.class, Integer.class, null, false);
        assertNotNull(proxy);
        assertTrue(proxy instanceof ProxyMap);
        ProxyMap proxyMap = (ProxyMap) proxy;
        assertEquals(String.class, proxyMap.getKeyType());
        assertEquals(Integer.class, proxyMap.getValueType());
    }

    // T30
    @Test
    public void testNewMapProxyWithAssertTypeFalse() {
        proxyManager.setAssertAllowedType(false);
        Proxy proxy = proxyManager.newMapProxy(HashMap.class, String.class, Integer.class, null, false);
        assertNotNull(proxy);
        assertTrue(proxy instanceof ProxyMap);
        ProxyMap proxyMap = (ProxyMap) proxy;
        assertNull(proxyMap.getKeyType());
        assertNull(proxyMap.getValueType());
    }

    // T31
    @Test
    public void testNewCustomProxy_CallsGetFactoryProxyBean() {
        DummyBean bean = new DummyBean();
        bean.setValue("hello");
        Proxy proxy = proxyManager.newCustomProxy(bean, false);
        assertNotNull(proxy);
        assertEquals("hello", ((DummyBean) proxy).getValue());
    }

    // T32
    @Test
    public void testNewCustomProxy_NonProxyableReturnsNull() {
        NonProxyable bean = new NonProxyable();
        Proxy proxy = proxyManager.newCustomProxy(bean, false);
        assertNull(proxy);
    }


    // T33
    @Test
    public void testNewMapProxy_MapInterfaceReturnsHashMap() {
        Proxy proxy = proxyManager.newMapProxy(Map.class, null, null, null, false);
        assertTrue(proxy instanceof HashMap);
    }

    // T34
    @Test
    public void testNewMapProxy_SortedMapInterfaceReturnsTreeMap() {
        Proxy proxy = proxyManager.newMapProxy(SortedMap.class, null, null, null, false);
        assertTrue(proxy instanceof TreeMap);
    }

    // T35
    @Test
    public void testNewCollectionProxy_QueueReturnsLinkedList() {
        Proxy proxy = proxyManager.newCollectionProxy(Queue.class, null, null, false);
        assertTrue(proxy instanceof LinkedList);
    }

    // T36
    @Test
    public void testInstantiateProxy_UsesConstructor() {
        BeanWithCopyConstructor bean = new BeanWithCopyConstructor("ciao");
        Proxy proxy = proxyManager.newCustomProxy(bean, false);
        assertNotNull(proxy);
        assertEquals("ciao", ((BeanWithCopyConstructor) proxy).getValue());
    }

    // T37
    @Test
    public void testInstantiateProxy_UsesAccessController() {
        Proxy proxy = proxyManager.newCollectionProxy(ArrayList.class, Integer.class, null, false);
        assertNotNull(proxy);
    }


    // T38
    @Test(expected = RuntimeException.class)
    public void testInstantiateProxy_ThrowsGeneralException() {
        proxyManager.newCustomProxy(new ExceptionBean(), false);
    }

    // Proxyable bean with proper getter/setter
    public static class DummyBean {
        private String value;
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
    }

    // Bean with copy constructor and getter/setter
    public static class BeanWithCopyConstructor {
        private String value;
        public BeanWithCopyConstructor() {}
        public BeanWithCopyConstructor(String v) { this.value = v; }
        public BeanWithCopyConstructor(BeanWithCopyConstructor other) {
            this.value = other.value;
        }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
    }

    // Abstract class to trigger instantiation error
    public static abstract class AbstractDummyBean {
        public AbstractDummyBean() {}
    }

    // Class with exploding constructor
    public static class ExceptionBean {
        public ExceptionBean() { throw new RuntimeException("exception"); }
    }

    // Non proxyable class
    public static final class NonProxyable {
        // Final + no public constructor → proxying fallisce
    }
}
