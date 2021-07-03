package com.rxjava.chapter0509.quiz;

import com.rxjava.utils.LogType;
import com.rxjava.utils.Logger;
import io.reactivex.Observable;

/**
 * 10부터 1까지 역순으로 뺄셈한 최종 결과 값을 출력하세요.
 * 예) 10 - 9 - 8 - 7 - 6 - 5 - 4 - 3 - 2 - 1 = -35
 */
public class QuizAnswerForChapter050901 {
    public static void main(String[] args) {

        Observable.range(0, 10)
                .map(integer -> 10 - integer)
                .reduce((integer1, integer2) -> integer1 - integer2)
                .subscribe(data -> Logger.log(LogType.ON_NEXT, data));

        /*
         * (원본 답안)
         */
//        final int seed = 10;
//        Observable.range(1, 9)
//                .reduce(seed, (x, y) -> {
//                    int b = seed - y;
//                    Logger.log(LogType.PRINT, x + ", " + b);
//                    return x - b;
//                })
//                .subscribe(data -> Logger.log(LogType.ON_NEXT, data));
    }
}
