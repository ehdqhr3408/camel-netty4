package com.github.zregvart.cnp;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class MyPrc implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        final Message message = exchange.getIn();
        final String body = message.getBody(String.class);
        System.out.println("call myprc");
        System.out.println("------------------------------------------------------");
        exchange.getIn().setHeader(Exchange.HTTP_SCHEME, "https");
        exchange.getIn().setHeader(Exchange.HTTP_HOST, "petstore.swagger.io");
        exchange.getIn().setHeader(Exchange.HTTP_PORT, "443");
    }
}
