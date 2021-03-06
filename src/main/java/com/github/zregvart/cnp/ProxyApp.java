/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.zregvart.cnp;

import io.netty.handler.logging.LoggingHandler;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.main.Main;

public class ProxyApp {
    public static void main(final String[] args) throws Exception {

        final Main main = new Main();
        launch(main);
    }

    static void launch(final Main main) throws Exception {
        try {
            main.bind("logging-handler", new LoggingHandler());
            main.configure().addRoutesBuilder(new ProxyRoute());
            main.run();
        } catch (final Exception e) {
            throw new ExceptionInInitializerError(e);
        } finally {
            main.stop();
        }
    }
}
