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

import org.apache.openjpa.util.proxy.ProxyCalendar;
import org.apache.openjpa.util.proxy.ProxyDate;
import org.apache.openjpa.util.proxy.ProxyMap;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.*;

public class ProxyManagerImpl_CoTTest {

    private ProxyManagerImpl proxyManager;

    @Before
    public void setUp() {
        proxyManager = new ProxyManagerImpl();
    }

    @Test
    public void testGenerateAndLoadProxyDate() {
        ProxyDate proxyDate = (ProxyDate) proxyManager.newDateProxy(Date.class);
        assertNotNull(proxyDate);
        assertTrue(proxyDate instanceof Date);
    }

    @Test
    public void testGenerateAndLoadProxyCalendar() {
        ProxyCalendar proxyCalendar = (ProxyCalendar) proxyManager.newCalendarProxy(GregorianCalendar.class, Calendar.getInstance().getTimeZone());
        assertNotNull(proxyCalendar);
        assertTrue(proxyCalendar instanceof Calendar);
    }

    @Test
    public void testGenerateAndLoadProxyMapAndAddMethods() {
        ProxyMap proxyMap = (ProxyMap) proxyManager.newMapProxy(TreeMap.class, String.class, String.class, Comparator.naturalOrder(), false);
        assertNotNull(proxyMap);
        assertTrue(proxyMap instanceof SortedMap);
    }

    @Test
    public void testProxyOverrideMethodWithHashMap() {
        ProxyMap proxyMap = (ProxyMap) proxyManager.newMapProxy(HashMap.class, String.class, String.class, null, false);
        assertNotNull(proxyMap);
        Map map = (Map) proxyMap;
        map.put("key", "value");
        assertEquals("value", map.get("key"));
        map.remove("key");
        assertNull(map.get("key"));
        map.clear();
        assertTrue(map.isEmpty());
    }

    @Test
    public void testFindComparatorConstructorExists() {
        try {
            Method method = ProxyManagerImpl.class.getDeclaredMethod("findComparatorConstructor", Class.class);
            method.setAccessible(true);
            Object result = method.invoke(proxyManager, TreeSet.class);
            assertNotNull(result);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    public void testFindComparatorConstructorMissing() {
        try {
            Method method = ProxyManagerImpl.class.getDeclaredMethod("findComparatorConstructor", Class.class);
            method.setAccessible(true);
            Object result = method.invoke(proxyManager, ArrayList.class);
            assertNull(result);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateProxyMapBytecodeExecution() {
        try {
            Method m = ProxyManagerImpl.class.getDeclaredMethod("generateProxyMapBytecode", Class.class, boolean.class, String.class);
            m.setAccessible(true);
            byte[] result = (byte[]) m.invoke(proxyManager, TreeMap.class, true, "dummy.proxy.TreeMap$proxy");
            assertNotNull(result);
        } catch (Exception e) {
            fail("generateProxyMapBytecode failed: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateProxyDateBytecodeExecution() {
        try {
            Method m = ProxyManagerImpl.class.getDeclaredMethod("generateProxyDateBytecode", Class.class, boolean.class, String.class);
            m.setAccessible(true);
            byte[] result = (byte[]) m.invoke(proxyManager, Date.class, true, "dummy.proxy.Date$proxy");
            assertNotNull(result);
        } catch (Exception e) {
            fail("generateProxyDateBytecode failed: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateProxyCalendarBytecodeExecution() {
        try {
            Method m = ProxyManagerImpl.class.getDeclaredMethod("generateProxyCalendarBytecode", Class.class, boolean.class, String.class);
            m.setAccessible(true);
            byte[] result = (byte[]) m.invoke(proxyManager, GregorianCalendar.class, true, "dummy.proxy.GregorianCalendar$proxy");
            assertNotNull(result);
        } catch (Exception e) {
            fail("generateProxyCalendarBytecode failed: " + e.getMessage());
        }
    }

    @Test
    public void testCopyBeanPropertiesExecution() {
        try {
            Method m = ProxyManagerImpl.class.getDeclaredMethod("copyBeanProperties",
                org.apache.xbean.asm9.MethodVisitor.class, Class.class, int.class);
            m.setAccessible(true);
            m.invoke(proxyManager, null, DummyBean.class, 1);
            fail("Expected NullPointerException");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof NullPointerException);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testInstantiateProxyWithNullConstructor() {
        try {
            Method m = ProxyManagerImpl.class.getDeclaredMethod("instantiateProxy", Class.class, Constructor.class, Object[].class);
            m.setAccessible(true);
            Object result = m.invoke(proxyManager, DummyProxy.class, null, null);
            assertNotNull(result);
        } catch (Exception e) {
            fail("instantiateProxy failed: " + e.getMessage());
        }
    }

    @Test
    public void testFindCopyConstructorFound() {
        try {
            Method m = ProxyManagerImpl.class.getDeclaredMethod("findCopyConstructor", Class.class);
            m.setAccessible(true);
            Constructor<?> result = (Constructor<?>) m.invoke(proxyManager, DummyBean.class);
            assertNotNull(result);
        } catch (Exception e) {
            fail("findCopyConstructor failed: " + e.getMessage());
        }
    }

    @Test
    public void testToConcreteTypeReturnsExpected() {
        try {
            Method m = ProxyManagerImpl.class.getDeclaredMethod("toConcreteType", Class.class, Map.class);
            m.setAccessible(true);
            Map<Class<?>, Class<?>> dummy = new HashMap<>();
            dummy.put(List.class, ArrayList.class);
            Class<?> result = (Class<?>) m.invoke(null, List.class, dummy);
            assertEquals(ArrayList.class, result);
        } catch (Exception e) {
            fail("toConcreteType failed: " + e.getMessage());
        }
    }

    @Test
    public void testGetFactoryProxyBean() {
        DummyBean bean = new DummyBean("abc");
        Object result = proxyManager.copyCustom(bean);
        assertNotNull(result);
    }

    @Test
    public void testDelegateConstructors() {
        try {
            Method m = ProxyManagerImpl.class.getDeclaredMethod("delegateConstructors",
                org.apache.openjpa.util.asm.ClassWriterTracker.class, Class.class, String.class);
            m.setAccessible(true);
            m.invoke(proxyManager, null, DummyBean.class, "Dummy");
            fail("Expected NullPointerException");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof NullPointerException);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testAddProxyCollectionMethods() {
        try {
            Method m = ProxyManagerImpl.class.getDeclaredMethod("addProxyCollectionMethods",
                org.apache.openjpa.util.asm.ClassWriterTracker.class, String.class, Class.class);
            m.setAccessible(true);
            m.invoke(proxyManager, null, "Dummy", TreeSet.class);
            fail("Expected NullPointerException");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof NullPointerException);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}
