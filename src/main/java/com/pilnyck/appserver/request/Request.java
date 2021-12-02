package com.pilnyck.appserver.request;

import java.util.Map;

public class Request {
    String uri;
    HTTPMethods method;
    Map<String, String> headers;
}
enum HTTPMethods{
    POST, GET
}
