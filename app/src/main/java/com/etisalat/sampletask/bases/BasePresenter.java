package com.etisalat.sampletask.bases;

public abstract class BasePresenter<E extends BasePresenterListener>
        implements BaseControllerListener {

    protected E listener;

    public BasePresenter(E listener) {
        this.listener = listener;
    }
}