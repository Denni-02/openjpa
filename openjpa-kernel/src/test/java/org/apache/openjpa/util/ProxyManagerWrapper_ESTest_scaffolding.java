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

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class ProxyManagerWrapper_ESTest_scaffolding {

  @org.junit.Rule
  public org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementRule();

  private static final java.util.Properties defaultProperties = (java.util.Properties) java.lang.System.getProperties().clone(); 

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =
    new org.evosuite.runtime.thread.ThreadStopper(
        org.evosuite.runtime.thread.KillSwitchHandler.getInstance(),
        3000
    );


  @BeforeClass
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "org.apache.openjpa.util.ProxyManagerWrapper"; 
    org.evosuite.runtime.GuiSupport.initialize(); 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfThreads = 100; 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfIterationsPerLoop = 10000; 
    org.evosuite.runtime.RuntimeSettings.mockSystemIn = true; 
    org.evosuite.runtime.RuntimeSettings.sandboxMode = org.evosuite.runtime.sandbox.Sandbox.SandboxMode.RECOMMENDED; 
    org.evosuite.runtime.sandbox.Sandbox.initializeSecurityManagerForSUT(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.init();
    setSystemProperties();
    initializeClasses();
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
  } 

  @AfterClass
  public static void clearEvoSuiteFramework(){ 
    Sandbox.resetDefaultSecurityManager(); 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
  } 

  @Before
  public void initTestCase(){ 
    threadStopper.storeCurrentThreads();
    threadStopper.startRecordingTime();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().initHandler(); 
    org.evosuite.runtime.sandbox.Sandbox.goingToExecuteSUTCode(); 
    setSystemProperties(); 
    org.evosuite.runtime.GuiSupport.setHeadless(); 
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
    org.evosuite.runtime.agent.InstrumentingAgent.activate(); 
  } 

  @After
  public void doneWithTestCase(){ 
    threadStopper.killAndJoinClientThreads();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().safeExecuteAddedHooks(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.reset(); 
    resetClasses(); 
    org.evosuite.runtime.sandbox.Sandbox.doneWithExecutingSUTCode(); 
    org.evosuite.runtime.agent.InstrumentingAgent.deactivate(); 
    org.evosuite.runtime.GuiSupport.restoreHeadlessMode(); 
  } 

  public static void setSystemProperties() {
 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
    java.lang.System.setProperty("user.dir", "/home/denni/isw2/openjpa/openjpa-kernel"); 
    java.lang.System.setProperty("java.io.tmpdir", "/tmp"); 
  }

  private static void initializeClasses() {
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(ProxyManagerWrapper_ESTest_scaffolding.class.getClassLoader() ,
      "org.apache.openjpa.lib.util.StreamResourceBundleProvider",
      "org.apache.openjpa.util.java$util$HashMap$proxy",
      "org.apache.openjpa.enhance.PersistenceCapable",
      "org.apache.openjpa.lib.util.ZipResourceBundleProvider",
      "org.apache.openjpa.lib.util.concurrent.ConcurrentReferenceHashMap$Entry",
      "org.apache.xbean.asm9.ClassVisitor",
      "org.apache.openjpa.util.GeneralException",
      "org.apache.openjpa.util.AbstractChangeTracker",
      "org.apache.openjpa.lib.util.SizedMap",
      "org.apache.xbean.asm9.MethodVisitor",
      "org.apache.openjpa.util.java$util$ArrayList$proxy",
      "org.apache.openjpa.util.proxy.ProxyMaps",
      "org.apache.openjpa.util.MapChangeTracker",
      "org.apache.openjpa.util.ChangeTracker",
      "org.apache.openjpa.util.UserException",
      "org.apache.xbean.asm9.tree.MethodInsnNode",
      "org.apache.openjpa.lib.util.StringUtil",
      "org.apache.openjpa.util.proxy.ProxyCollections$ProxyIterator",
      "org.apache.openjpa.lib.util.collections.AbstractReferenceMap$ReferenceStrength",
      "org.apache.xbean.asm9.FieldWriter",
      "org.apache.openjpa.util.CollectionChangeTrackerImpl",
      "org.apache.openjpa.lib.util.SimpleResourceBundleProvider",
      "org.apache.openjpa.util.CollectionChangeTracker",
      "org.apache.openjpa.enhance.PCRegistry",
      "org.apache.openjpa.util.ClassLoaderProxyService",
      "org.apache.xbean.asm9.ByteVector",
      "org.apache.openjpa.util.proxy.ProxyCollections",
      "org.apache.xbean.asm9.MethodTooLargeException",
      "org.apache.xbean.asm9.tree.TypeInsnNode",
      "org.apache.openjpa.lib.util.J2DoPrivHelper",
      "org.apache.openjpa.lib.util.ReferenceMap",
      "org.apache.openjpa.util.OpenJPAException",
      "org.apache.openjpa.util.ProxyManager",
      "org.apache.xbean.asm9.tree.InsnNode",
      "org.apache.openjpa.util.ImplHelper$1",
      "org.apache.openjpa.util.UnsupportedException",
      "org.apache.xbean.asm9.Type",
      "org.apache.xbean.asm9.Attribute",
      "org.apache.openjpa.lib.util.J2DoPrivHelper$5",
      "org.apache.xbean.asm9.SymbolTable$Entry",
      "org.apache.openjpa.util.Proxies",
      "org.apache.xbean.asm9.Frame",
      "org.apache.xbean.asm9.AnnotationWriter",
      "org.apache.xbean.asm9.Label",
      "org.apache.openjpa.lib.util.concurrent.ConcurrentMap",
      "org.apache.xbean.asm9.ModuleVisitor",
      "org.apache.openjpa.kernel.OpenJPAStateManager",
      "org.apache.openjpa.util.proxy.ProxyBean",
      "org.apache.openjpa.util.java$util$GregorianCalendar$proxy",
      "org.apache.openjpa.util.proxy.ProxyDate",
      "org.apache.xbean.asm9.Handler",
      "org.apache.xbean.asm9.RecordComponentWriter",
      "org.apache.xbean.asm9.tree.AbstractInsnNode",
      "org.apache.openjpa.util.proxy.ProxyCalendar",
      "org.apache.openjpa.util.asm.RedefinedAttribute",
      "org.apache.openjpa.util.proxy.ProxyMaps$ProxyEntrySetImpl$1",
      "org.apache.openjpa.enhance.StateManager",
      "org.apache.openjpa.lib.util.J2DoPrivHelper$11",
      "org.apache.openjpa.lib.util.ResourceBundleProvider",
      "org.apache.xbean.asm9.AnnotationVisitor",
      "org.apache.openjpa.lib.util.ClassUtil",
      "org.apache.xbean.asm9.CurrentFrame",
      "org.apache.openjpa.util.InternalException",
      "org.apache.openjpa.util.Proxy",
      "org.apache.xbean.asm9.tree.FieldInsnNode",
      "org.apache.openjpa.enhance.FieldConsumer",
      "org.apache.xbean.asm9.MethodWriter",
      "org.apache.openjpa.util.asm.BCClassWriter",
      "org.apache.openjpa.util.GeneratedClasses",
      "org.apache.openjpa.lib.util.concurrent.ConcurrentReferenceHashSet",
      "org.apache.xbean.asm9.tree.IntInsnNode",
      "org.apache.openjpa.lib.util.concurrent.ConcurrentReferenceHashMap",
      "org.apache.openjpa.lib.util.collections.MapBackedSet",
      "org.apache.openjpa.util.java$util$Date$proxy",
      "org.apache.openjpa.util.proxy.ProxyCollection",
      "org.apache.xbean.asm9.Edge",
      "org.apache.openjpa.util.ProxyManagerWrapper",
      "org.apache.openjpa.util.ProxyManagerImpl",
      "org.apache.openjpa.util.proxy.ProxyMaps$ProxyEntrySet",
      "org.apache.openjpa.util.proxy.ProxyMaps$ProxyEntrySetImpl",
      "org.apache.xbean.asm9.FieldVisitor",
      "org.apache.openjpa.lib.util.Localizer",
      "org.apache.xbean.asm9.RecordComponentVisitor",
      "org.apache.openjpa.util.MapChangeTrackerImpl",
      "org.apache.xbean.asm9.ClassTooLargeException",
      "org.apache.openjpa.enhance.FieldSupplier",
      "org.apache.openjpa.enhance.FieldManager",
      "org.apache.xbean.asm9.Symbol",
      "org.apache.openjpa.util.asm.ClassWriterTracker",
      "org.apache.openjpa.util.asm.AsmHelper",
      "org.apache.xbean.asm9.ClassWriter",
      "org.apache.xbean.asm9.tree.LdcInsnNode",
      "org.apache.openjpa.util.ClassLoaderProxyService$ProxiesClassLoader",
      "org.apache.openjpa.util.ImplHelper",
      "org.apache.xbean.asm9.ModuleWriter",
      "org.apache.xbean.asm9.SymbolTable",
      "org.apache.openjpa.util.proxy.ProxyMap",
      "org.apache.xbean.asm9.tree.ClassNode",
      "org.apache.openjpa.util.proxy.ProxyCollections$1",
      "org.apache.openjpa.util.ExceptionInfo"
    );
  } 

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(ProxyManagerWrapper_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "org.apache.openjpa.util.ProxyManagerWrapper",
      "org.apache.openjpa.lib.util.SimpleResourceBundleProvider",
      "org.apache.openjpa.lib.util.StreamResourceBundleProvider",
      "org.apache.openjpa.lib.util.ZipResourceBundleProvider",
      "org.apache.openjpa.lib.util.Localizer",
      "org.apache.openjpa.lib.util.J2DoPrivHelper",
      "org.apache.openjpa.lib.util.J2DoPrivHelper$5",
      "org.apache.xbean.asm9.Type",
      "org.apache.openjpa.util.ProxyManagerImpl",
      "org.apache.openjpa.util.GeneratedClasses",
      "org.apache.openjpa.lib.util.ClassUtil",
      "org.apache.openjpa.util.java$util$HashMap$proxy",
      "org.apache.openjpa.lib.util.J2DoPrivHelper$11",
      "org.apache.xbean.asm9.ClassVisitor",
      "org.apache.xbean.asm9.ClassWriter",
      "org.apache.xbean.asm9.SymbolTable",
      "org.apache.xbean.asm9.ByteVector",
      "org.apache.xbean.asm9.Symbol",
      "org.apache.xbean.asm9.SymbolTable$Entry",
      "org.apache.openjpa.util.asm.ClassWriterTracker",
      "org.apache.xbean.asm9.Attribute",
      "org.apache.openjpa.util.asm.RedefinedAttribute",
      "org.apache.openjpa.util.asm.AsmHelper",
      "org.apache.xbean.asm9.MethodVisitor",
      "org.apache.xbean.asm9.MethodWriter",
      "org.apache.xbean.asm9.Label",
      "org.apache.xbean.asm9.Frame",
      "org.apache.xbean.asm9.FieldVisitor",
      "org.apache.xbean.asm9.FieldWriter",
      "org.apache.xbean.asm9.AnnotationVisitor",
      "org.apache.xbean.asm9.AnnotationWriter",
      "org.apache.xbean.asm9.Handler",
      "org.apache.openjpa.util.ClassLoaderProxyService",
      "org.apache.openjpa.util.ClassLoaderProxyService$ProxiesClassLoader",
      "org.apache.openjpa.util.AbstractChangeTracker",
      "org.apache.openjpa.util.MapChangeTrackerImpl",
      "org.apache.openjpa.util.Proxies",
      "org.apache.openjpa.util.proxy.ProxyMaps",
      "org.apache.openjpa.util.proxy.ProxyMaps$ProxyEntrySetImpl",
      "org.apache.openjpa.util.proxy.ProxyMaps$ProxyEntrySetImpl$1",
      "org.apache.openjpa.util.java$util$ArrayList$proxy",
      "org.apache.openjpa.util.CollectionChangeTrackerImpl",
      "org.apache.openjpa.util.proxy.ProxyCollections",
      "org.apache.openjpa.util.proxy.ProxyCollections$1",
      "org.apache.xbean.asm9.Edge",
      "org.apache.openjpa.lib.util.StringUtil",
      "org.apache.openjpa.util.OpenJPAException",
      "org.apache.openjpa.util.GeneralException",
      "org.apache.openjpa.lib.util.concurrent.ConcurrentReferenceHashMap",
      "org.apache.openjpa.lib.util.collections.AbstractReferenceMap$ReferenceStrength",
      "org.apache.openjpa.util.ImplHelper$1",
      "org.apache.openjpa.util.ImplHelper",
      "org.apache.openjpa.lib.util.concurrent.ConcurrentReferenceHashSet",
      "org.apache.openjpa.lib.util.collections.MapBackedSet",
      "org.apache.openjpa.enhance.PCRegistry",
      "org.apache.openjpa.util.proxy.DelayedArrayListProxy",
      "org.apache.openjpa.util.DelayedCollectionChangeTrackerImpl",
      "org.apache.openjpa.util.java$util$GregorianCalendar$proxy",
      "org.apache.openjpa.util.java$util$Date$proxy"
    );
  }
}
