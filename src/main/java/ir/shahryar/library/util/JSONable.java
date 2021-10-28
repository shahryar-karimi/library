package ir.shahryar.library.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface JSONable<T> {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    String toJson();
}
