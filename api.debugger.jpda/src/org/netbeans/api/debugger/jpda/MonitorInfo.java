/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.api.debugger.jpda;

import com.sun.jdi.AbsentInformationException;
import java.beans.PropertyChangeListener;
import java.util.List;
import org.netbeans.spi.debugger.jpda.EditorContext.Operation;


/**
 * Represents one owned monitor.
 *
 * <pre style="background-color: rgb(255, 255, 102);">
 * Since JDI interfaces evolve from one version to another, it's strongly recommended
 * not to implement this interface in client code. New methods can be added to
 * this interface at any time to keep up with the JDI functionality.</pre>
 *
 * @author Martin Entlicher
 * @since 2.16
 */
public interface MonitorInfo {
    
    /**
     * Returns the acquired monitor object
     *
     * @return monitor object
     */
    ObjectVariable getMonitor();

    /**
     * Returns the frame at which this monitor was acquired by the owning thread.
     * 
     * @return  The frame, or <CODE>null</CODE> when the implementation cannot
     * determine the stack frame (e.g., for monitors acquired by JNI).
     */
    CallStackFrame getFrame();
    
    /**
     * Get the owning thread.
     * 
     * @return the thread.
     */
    JPDAThread getThread();

}
 
