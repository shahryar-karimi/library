package ir.shahryar.library.util;

import java.util.ArrayList;

public class MyList<E> extends ArrayList<E> implements JSONable<MyList<E>>{
    @Override
    public String toJson() {
        return gson.toJson(this);
    }
}
