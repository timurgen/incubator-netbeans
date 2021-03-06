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

package org.netbeans.modules.java.hints.perf;

import org.junit.Test;
import org.netbeans.modules.java.hints.test.api.HintTest;

/**
 *
 * @author lahvac
 */
public class SizeEqualsZeroTest {

    @Test
    public void testSimple1() throws Exception {
        HintTest.create()
                .input("test/Test.java",
                       "package test;\n" +
                       "import java.util.List;" +
                       "public class Test {\n" +
                       "     private void test(List l) {\n" +
                       "         boolean b = l.size() == 0;\n" +
                       "     }\n" +
                       "}\n")
                .run(SizeEqualsZero.class)
                .findWarning("3:21-3:34:verifier:.size() == 0")
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n" +
                       "import java.util.List;" +
                       "public class Test {\n" +
                       "     private void test(List l) {\n" +
                       "         boolean b = l.isEmpty();\n" +
                       "     }\n" +
                        "}\n");
    }

    @Test
    public void testSimple2() throws Exception {
        HintTest.create()
                .input("package test;\n" +
                       "import java.util.List;" +
                       "public class Test {\n" +
                       "     private void test(List l) {\n" +
                       "         boolean b = l.size() != 0;\n" +
                       "     }\n" +
                       "}\n")
                .preference(SizeEqualsZero.CHECK_NOT_EQUALS, true)
                .run(SizeEqualsZero.class)
                .findWarning("3:21-3:34:verifier:.size() != 0")
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n" +
                        "import java.util.List;" +
                        "public class Test {\n" +
                        "     private void test(List l) {\n" +
                        "         boolean b = !l.isEmpty();\n" +
                        "     }\n" +
                        "}\n");
    }
    
    @Test
    public void testCollection() throws Exception {
        HintTest.create()
                .input("test/Test.java",
                       "package test;\n" +
                       "import java.util.ArrayList;" +
                       "public class Test extends ArrayList {\n" +
                       "     private void test() {\n" +
                       "         boolean b = size() == 0;\n" +
                       "     }\n" +
                       "}\n")
                .run(SizeEqualsZero.class)
                .findWarning("3:21-3:32:verifier:.size() == 0")
                .applyFix()
                .assertCompilable()
                .assertOutput("package test;\n" +
                       "import java.util.ArrayList;" +
                       "public class Test extends ArrayList {\n" +
                       "     private void test() {\n" +
                       "         boolean b = isEmpty();\n" +
                       "     }\n" +
                        "}\n");
    }
    
    @Test
    public void testDoNotChangeIsEmptyItself() throws Exception {
        HintTest.create()
                .input("test/Test.java",
                       "package test;\n" +
                       "import java.util.ArrayList;" +
                       "public class Test extends ArrayList {\n" +
                       "     public boolean isEmpty() {\n" +
                       "         return this.size() == 0;\n" +
                       "     }\n" +
                       "}\n")
                .run(SizeEqualsZero.class)
                .assertWarnings();
    }

    @Test
    public void testSimpleConfig() throws Exception {
        HintTest.create()
                .input("package test;\n" +
                       "import java.util.List;" +
                       "public class Test {\n" +
                       "     private void test(List l) {\n" +
                       "         boolean b = l.size() != 0;\n" +
                       "     }\n" +
                       "}\n")
                .preference(SizeEqualsZero.CHECK_NOT_EQUALS, false)
                .run(SizeEqualsZero.class)
                .assertWarnings();
    }

}