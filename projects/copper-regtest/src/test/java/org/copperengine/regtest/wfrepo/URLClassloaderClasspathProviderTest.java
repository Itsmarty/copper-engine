/*
 * Copyright 2002-2015 SCOOP Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.copperengine.regtest.wfrepo;

import org.copperengine.core.wfrepo.FileBasedWorkflowRepository;
import org.copperengine.core.wfrepo.URLClassloaderClasspathProvider;
import org.junit.Assume;
import org.junit.Test;

public class URLClassloaderClasspathProviderTest {

    protected static double getJavaVersion() {
        try {
            return Double.parseDouble(System.getProperty("java.specification.version"));
        } catch (Exception e) {
            return 1.0;
        }
    }

    @Test
    public void testGetOptions() {
        Assume.assumeTrue(getJavaVersion() < 9);
        FileBasedWorkflowRepository repo = new FileBasedWorkflowRepository();
        try {
            repo.addSourceDir("src/workflow/java");
            repo.setTargetDir("build/compiled_workflow");
            repo.addCompilerOptionsProvider(new URLClassloaderClasspathProvider());
            repo.start();
        } finally {
            try {
                repo.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
