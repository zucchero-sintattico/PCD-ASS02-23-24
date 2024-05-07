package part2.rx.controller;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import part2.rx.model.ErrorReport;

public class ErrorObserver implements Observer<ErrorReport> {

    Consumer<ErrorReport> errorReportAction;

    public ErrorObserver(Consumer<ErrorReport> errorReportAction) {
        this.errorReportAction = errorReportAction;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull ErrorReport errorReport) {
        try {
            this.errorReportAction.accept(errorReport);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
