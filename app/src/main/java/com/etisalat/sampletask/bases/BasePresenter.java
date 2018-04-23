package com.etisalat.sampletask.bases;

public abstract class BasePresenter<T extends BaseController,
        E extends BasePresenterListener>
        implements BaseControllerListener {

    protected E listener;
    protected T controller;

    public BasePresenter(E listener) {
        this.listener = listener;
    }
}