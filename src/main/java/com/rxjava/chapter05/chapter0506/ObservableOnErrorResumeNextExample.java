package com.rxjava.chapter05.chapter0506;

import com.rxjava.utils.LogType;
import com.rxjava.utils.Logger;
import com.rxjava.utils.TimeUtil;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

/**
 * onErrorResumeNext() 메소드는,
 * onErrorReturn()과 다르게 에러 발생 시 내가 원하는 Observable로 대체하는 방법이다.
 */
public class ObservableOnErrorResumeNextExample {
    public static void main(String[] args) {
        Observable.just(5L)
                .flatMap(num ->
                        Observable.interval(200L, TimeUnit.MILLISECONDS)
                        .take(5)
                        .map(i -> {
                            long result;
                            try{
                                result = num / i;
                            }catch(ArithmeticException ex){
                                 /*
                                 데이터 흐름을 콘솔에 로그 출력을 위해 try-catch 문을 활용하고자 한다.
                                 실제로는 map() 메소드 안에 try-catch 문 사용하지 않는다.
                                 */
                                Logger.log(LogType.PRINT, "error: " + ex.getMessage());
                                throw ex;
                            }
                            return result;
                        })
                        .retry(3)
                        .onErrorResumeNext(throwable -> {
                            Logger.log(LogType.PRINT, "# 운영자에게 이메일 발송: " + throwable.getMessage());
                            return Observable.interval(200L,TimeUnit.MILLISECONDS)
                                    .take(5).skip(1).map(i -> num / i);
                        })
                ).subscribe(data -> Logger.log(LogType.ON_NEXT, data));

        TimeUtil.sleep(2000L);
    }
}
