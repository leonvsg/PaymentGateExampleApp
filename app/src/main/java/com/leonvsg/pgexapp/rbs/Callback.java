package com.leonvsg.pgexapp.rbs;

public interface Callback<T> {
    void execute(T model);
}
