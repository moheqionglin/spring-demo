package com;

import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.jersey.JerseySupport;
import io.opentracing.SpanContext;
import netscape.javascript.JSObject;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.stream.Collectors;

@Path("/")
public class HelloWorld {

    @Inject
    private ServerRequest request;

    @Inject
    private ServerResponse response;


    @Inject
    @Named(JerseySupport.REQUEST_SPAN_CONTEXT)
    private SpanContext spanContext;


    @GET
    @Path("hello")
    public Response hello() {
        return Response.ok("Hello World !\r\n").build();
    }


    @POST
    @Path("hello")
    public Response hello(String content) {
        return Response.accepted("Hello: " + content + "!\r\n").build();
    }

    @POST
    @Path("content")
    public Response content(String content) {
        return Response.accepted(content+"\r\n").build();
    }

    @GET
    @Path("injection")
    public Response webServerInjection() {
        return Response.ok("request=" + request.getClass().getName()
                + "\nresponse=" + response.getClass().getName()
                + "\nspanContext=" + spanContext.getClass().getName() +"\r\n").build();
    }

    @GET
    @Path("headers")
    public Response headers(@Context HttpHeaders headers, @QueryParam("header") String header) {
        return Response.ok("headers=" + headers.getRequestHeader(header).stream().collect(Collectors.joining(",")) +"\r\n")
                .build();
    }

    @GET
    @Path("query")
    public Response query(@QueryParam("a") String a, @QueryParam("b") String b) {
        return Response.accepted("a='" + a + "';b='" + b + "'"+"\r\n").build();
    }

    @GET
    @Path("path/{num}")
    public Response path(@PathParam("num") String num) {
        return Response.accepted("num=" + num+"\r\n").build();
    }

    @GET
    @Path("requestUri")
    public String getRequestUri(@Context UriInfo uriInfo) {
        return uriInfo.getRequestUri().getPath()+"\r\n";
    }
}