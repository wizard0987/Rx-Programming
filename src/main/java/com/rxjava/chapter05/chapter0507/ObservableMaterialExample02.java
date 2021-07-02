package com.rxjava.chapter05.chapter0507;

import com.rxjava.utils.LogType;
import com.rxjava.utils.Logger;
import com.rxjava.utils.TimeUtil;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.Arrays;

/**
 * Material()/Dematerial() 연산자의 실제 활용 예제 :
 * 특정 Observable에서 에러가 발생할 경우 해당 에러에 대해서 구체적으로 처리할 수 있다.
 */
public class ObservableMaterialExample02 {
    public static void main(String[] args) {
        /*
         * concatEager( ) 메소드 :
         * concat( ) 메소드는 파라미터로 전달받은 Observable을 '순차적으로 실행'하여 데이터를 통지하는 반면,
         * concatEager( )는 파라미터로 전달받은 Observable을 '동시에 실행'하고 파라미터 순서에 따라 데이터를 통지한다.
         *
         * 즉, concatEager( )와 concat( )의 차이점은, 파라미터로 전달받은 Observable을 동시/순차적으로 실행하냐이다.
         */
        Observable.concatEager(
                Observable.just(
                        // Schedulers.io() :
                        // subscribeOn( ) 메소드를 통해서 각각의 Observable이 별도의 스레드에서 독립적으로 실행된다.
                        getDBUser().subscribeOn(Schedulers.io()),
                        getAPIUser().subscribeOn(Schedulers.io())
                                .materialize()
                                .map(notification -> {
                                    if (notification.isOnError()) {
                                        // 관리자에게 에러 발생을 알림
                                        Logger.log(LogType.PRINT, "# API user 에러 발생!");
                                    }
                                    return notification;
                                })
                                // filter() 함수를 이용해 에러가 발생한 데이터만 제외하고 구독자에게 데이터 방출한다.
                                .filter(notification -> !notification.isOnError())
                                .dematerialize(notification -> notification)
                )
        ).subscribe(
                data -> Logger.log(LogType.ON_NEXT, data),
                error -> Logger.log(LogType.ON_ERROR, error),
                () -> Logger.log(LogType.ON_COMPLETE)
        );
        TimeUtil.sleep(1000L);
    }

    private static Observable<String> getDBUser() {
        return Observable
                .fromIterable(Arrays.asList("DB user1", "DB user2", "DB user3", "DB user4", "DB user5"));
    }

    private static Observable<String> getAPIUser() {
        return Observable
                .just("API user1", "API user2", "Not User", "API user4", "API user5")
                .map(user -> {
                    if(user.equals("Not User"))
                        throw new RuntimeException();
                    return user;
                });
    }
}
