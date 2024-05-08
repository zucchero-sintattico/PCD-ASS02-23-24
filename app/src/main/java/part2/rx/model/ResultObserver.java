package part2.rx.model;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

public class ResultObserver implements Observer<SearchReport> {

    private final Consumer<SearchReport> action;
    private final Runnable onCompletedAction;

    public ResultObserver(Consumer<SearchReport> nextElementAction, Runnable onCompletedAction){
        this.action = nextElementAction;
        this.onCompletedAction = onCompletedAction;
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
        this.onCompletedAction.run();
    }
}
