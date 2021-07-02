package com.rxjava.chapter05.chapter0507;

import com.rxjava.utils.LogType;
import com.rxjava.utils.Logger;
import com.rxjava.utils.TimeUtil;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

/**
 * 생성 및 통지된 데이터를 소비자 쪽에서 전달받는 시간을 일정 시간 동안 지연시키는 예제 :  delay() - 1 번째 유형
 */
public class ObservableDelayExample01 {
    public static void main(String[] args) {
        Logger.log(LogType.PRINT, "# 실행 시작 시간: " + TimeUtil.getCurrentTimeFormatted());

        Observable.just(1, 3, 4, 6)
                .doOnNext(data -> Logger.log(LogType.DO_ON_NEXT, data))
                .delay(2000L, TimeUnit.MILLISECONDS)
                .subscribe(data -> Logger.log(LogType.ON_NEXT, data));

        TimeUtil.sleep(2500L);
    }
}
