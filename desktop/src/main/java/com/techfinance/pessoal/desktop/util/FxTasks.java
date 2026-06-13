package com.techfinance.pessoal.desktop.util;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.concurrent.Task;

public final class FxTasks {

    private FxTasks() {}

    public static <T> void run(
            Callable<T> callable,
            Consumer<T> onSuccess,
            Consumer<Throwable> onError) {

        Task<T> task = new Task<>() {
            @Override
            protected T call() throws Exception {
                return callable.call();
            }
        };

        task.setOnSucceeded(event ->
            Platform.runLater(() -> onSuccess.accept(task.getValue()))
        );

        task.setOnFailed(event ->
            Platform.runLater(() -> onError.accept(task.getException()))
        );

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public static void run(
            Runnable runnable,
            Runnable onSuccess,
            Consumer<Throwable> onError) {

        run(
            () -> {
                runnable.run();
                return null;
            },
            ignored -> onSuccess.run(),
            onError
        );
    }
}
