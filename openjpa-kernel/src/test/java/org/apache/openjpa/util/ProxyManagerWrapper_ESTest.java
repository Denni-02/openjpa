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
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.apache.openjpa.util.ProxyManagerWrapper;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(
    mockJVMNonDeterminism = true,
    useVFS = true,
    useVNET = true,
    resetStaticState = true,
    separateClassLoader = true
)
public class ProxyManagerWrapper_ESTest extends ProxyManagerWrapper_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      ProxyManagerWrapper proxyManagerWrapper0 = new ProxyManagerWrapper();
      boolean boolean0 = proxyManagerWrapper0.testDelayCollectionLoading();
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      ProxyManagerWrapper proxyManagerWrapper0 = new ProxyManagerWrapper();
      // Undeclared exception!
      try { 
        proxyManagerWrapper0.testCopyCollection();
        fail("Expecting exception: RuntimeException");
      
      } catch(RuntimeException e) {
         //
         // org.apache.openjpa.util.java$util$Arrays$ArrayList$0$proxy
         //
         verifyException("org.apache.openjpa.util.GeneratedClasses", e);
      }
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      ProxyManagerWrapper proxyManagerWrapper0 = new ProxyManagerWrapper();
      Map<?, ?> map0 = proxyManagerWrapper0.testCopyMap();
      assertFalse(map0.isEmpty());
  }

  /*@Test(timeout = 4000)
  public void test03()  throws Throwable  {
      ProxyManagerWrapper proxyManagerWrapper0 = new ProxyManagerWrapper();
      Calendar calendar0 = proxyManagerWrapper0.testCopyCalendar();
      assertEquals("org.evosuite.runtime.mock.java.util.MockGregorian", calendar0.toString());
  }*/

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      ProxyManagerWrapper proxyManagerWrapper0 = new ProxyManagerWrapper();
      boolean boolean0 = proxyManagerWrapper0.testTrackChanges();
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      ProxyManagerWrapper proxyManagerWrapper0 = new ProxyManagerWrapper();
      proxyManagerWrapper0.testSetUnproxyable();
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      ProxyManagerWrapper proxyManagerWrapper0 = new ProxyManagerWrapper();
      boolean boolean0 = proxyManagerWrapper0.testAssertAllowedType();
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      ProxyManagerWrapper proxyManagerWrapper0 = new ProxyManagerWrapper();
      Object object0 = proxyManagerWrapper0.testCopyCustom();
      assertNull(object0);
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      ProxyManagerWrapper proxyManagerWrapper0 = new ProxyManagerWrapper();
      Object object0 = proxyManagerWrapper0.testNewCustomProxy();
      assertNull(object0);
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      ProxyManagerWrapper proxyManagerWrapper0 = new ProxyManagerWrapper();
      Date date0 = proxyManagerWrapper0.testCopyDate();
      assertEquals("Fri Feb 14 20:21:21 GMT 2014", date0.toString());
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      ProxyManagerWrapper proxyManagerWrapper0 = new ProxyManagerWrapper();
      Object object0 = proxyManagerWrapper0.testCopyArray();
      assertNotNull(object0);
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      ProxyManagerWrapper proxyManagerWrapper0 = new ProxyManagerWrapper();
      Collection<?> collection0 = proxyManagerWrapper0.testGetUnproxyable();
      assertNotNull(collection0);
  }
}
