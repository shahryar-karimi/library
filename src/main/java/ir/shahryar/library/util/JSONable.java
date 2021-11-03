package ir.shahryar.library.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface JSONable {
    Gson gson = new GsonBuilder().create();

    String toJson();
}
