package com.leonvsg.pgexapp.rbs;

public interface Callback<T> {
    public void execute(T model);
}
