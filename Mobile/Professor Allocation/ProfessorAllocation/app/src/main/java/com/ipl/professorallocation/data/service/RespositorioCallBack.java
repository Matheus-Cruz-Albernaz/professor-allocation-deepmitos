package com.ipl.professorallocation.data.service;

public interface RespositorioCallBack<T> {
    void onResponse(T response);

    void onFailure(Throwable t);
}
