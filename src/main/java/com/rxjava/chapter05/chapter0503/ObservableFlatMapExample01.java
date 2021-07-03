package com.rxjava.chapter05.chapter0503;

import com.rxjava.utils.LogType;
import com.rxjava.utils.Logger;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * FlapMap을 이용한 1:n mapping 예제
 * defer( ), fromFuture( ) 메소드도 just( ) 연산자처럼 여러 인자 값을 받을 수 있을까?
 */
public class ObservableFlatMapExample01 {
    public static void main(String[] args) {
        Observable.fromFuture(CompletableFuture.supplyAsync(() -> "Hello, "))
                .flatMap(
                        hello -> Observable
                                        .fromFuture(CompletableFuture.supplyAsync(() ->
                                                Arrays.asList("자바", "파이썬", "안드로이드")))
                                        .flatMap(langList ->
                                                        Observable.fromIterable(langList).map(lang -> hello + lang))
//                                        .defer((Callable<ObservableSource<String>>) () ->
//                                                Observable.just("자바", "파이썬", "안드로이드"))
//                                        .just("자바", "파이썬", "안드로이드").map(lang -> hello + lang)
                )
                .subscribe(data -> Logger.log(LogType.ON_NEXT, data));

    }
}
