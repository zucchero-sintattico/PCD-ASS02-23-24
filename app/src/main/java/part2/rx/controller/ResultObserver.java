package part2.rx.controller;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import part2.rx.model.SearchReport;

public class ResultObserver implements Observer<SearchReport> {

    Consumer<SearchReport> action;
    Runnable doneAction;

    public ResultObserver(Consumer<SearchReport> nextElementAction, Runnable doneAction){
        this.action = nextElementAction;
        this.doneAction = doneAction;
    }
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull SearchReport searchReport) {
        try {
            action.accept(searchReport);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {

    }

    @Override
    public void onComplete() {
        this.doneAction.run();
    }
}
