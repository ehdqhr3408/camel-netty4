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

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.springframework.stereotype.Component;

@Component
public class ProxyRoute extends RouteBuilder {

    Processor myprocessor = new MyPrc();
    @Override
    public void configure() throws Exception {
        final RouteDefinition from;
        if (Files.exists(keystorePath())) {
            System.out.println("ssl000000000000-------------------------1");
            from = from("netty-http:proxy://0.0.0.0:8443?ssl=true&keyStoreFile=C:/Program Files/Java/jdk-11.0.10/bin/server.jks&passphrase=111111&trustStoreFile=C:/Program Files/Java/jdk-11.0.10/bin/server.jks");
            System.out.println("ssl000000000000-------------------------2");
        } else {
            from = from("netty-http:proxy://0.0.0.0:8080");
        }

        from
                //.process(myprocessor)
            .process(ProxyRoute::uppercase)
            .toD("netty-http:"
                + "${headers." + Exchange.HTTP_SCHEME + "}://"
                + "${headers." + Exchange.HTTP_HOST + "}:"
                + "${headers." + Exchange.HTTP_PORT + "}"
                + "${headers." + Exchange.HTTP_PATH + "}")
            .process(ProxyRoute::uppercase);

    }
    Path keystorePath() {
        return Path.of("C:/Program Files/Java/jdk-11.0.10/bin", "server.jks");
    }

    public static void uppercase(final Exchange exchange) {
        final Message message = exchange.getIn();
        final String body = message.getBody(String.class);
        System.out.println("call uppercase");
        System.out.println("------------------------------------------------------");
        exchange.getIn().setHeader(Exchange.HTTP_SCHEME, "https");
        exchange.getIn().setHeader(Exchange.HTTP_HOST, "petstore.swagger.io");
        exchange.getIn().setHeader(Exchange.HTTP_PORT, "443");
        //(String)exchange.getIn().setHeader(Exchange.HTTP_QUERY, "order = 123, detail = short");
        // exchange.getIn().getHeader(Exchange.HTTP_QUERY);
        // exchange.getIn().getHeader(Exchange.HTTP_BASE_URI);
        //exchange.getIn().getHeader(Exchange.HTTP_METHOD);

//        message.getHeader(Exchange.HTTP_SCHEME);
//        message.getHeader(Exchange.HTTP_HOST);
//        message.getHeader(Exchange.HTTP_PORT);
//        message.getHeader(Exchange.HTTP_PATH);

    }

}

