package ir.shahryar.library.data;

import ir.shahryar.library.util.JSONable;

public class Response implements JSONable<Response> {
    private String message;

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Response setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toJson() {
        return gson.toJson(this);
    }
}
