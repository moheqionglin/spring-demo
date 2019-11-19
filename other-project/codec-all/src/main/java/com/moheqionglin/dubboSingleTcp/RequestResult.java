package com.moheqionglin.dubboSingleTcp;

class RequestResult{
    public RequestResult(Object o) {
        this.o = o;
    }
    Object o;

    @Override
    public String toString() {
        return "RequestResult{" +
                "o=" + o +
                '}';
    }
}