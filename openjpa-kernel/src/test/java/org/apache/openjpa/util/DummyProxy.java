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

import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.util.ChangeTracker;
import org.apache.openjpa.util.Proxy;


public class DummyProxy implements Proxy {

    @Override
    public void setOwner(OpenJPAStateManager sm, int field) {}

    @Override
    public OpenJPAStateManager getOwner() {
        return null;
    }

    @Override
    public int getOwnerField() {
        return 0;
    }

    @Override
    public Object copy(Object orig) {
        return null;
    }

    @Override
    public ChangeTracker getChangeTracker() {
        return null;
    }
}
