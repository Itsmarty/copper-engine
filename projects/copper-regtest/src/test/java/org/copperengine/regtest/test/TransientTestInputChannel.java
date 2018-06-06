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
package org.copperengine.regtest.test;

import org.copperengine.core.ProcessingEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransientTestInputChannel implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(TransientTestInputChannel.class);
    private ProcessingEngine engine;

    public void setEngine(ProcessingEngine engine) {
        this.engine = engine;
    }

    public void run() {
        try {
            for (;;) {
                engine.run("org.copperengine.regtest.test.ExtendedSpock2GTestWF", null);
                Thread.sleep(15000);
                engine.shutdown();
                return;
            }
        } catch (Exception e) {
            logger.error("run failed", e);
        }
    }

    public void startup() {
        new Thread(this).start();
    }

    public void shutdown() {

    }
}
