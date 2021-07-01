package com.rxjava.chapter05.chapter0504.quiz;

import com.rxjava.common.Car;
import com.rxjava.common.CarMaker;
import com.rxjava.common.SampleData;
import com.rxjava.utils.LogType;
import com.rxjava.utils.Logger;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.Map;

/**
 * toMap 을 이용하여 SampleData.carList 의 car 객체들을 carName을 key로, carMaker를 value로 가지는 Map을 출력하세요.
 */
public class QuizAnswerForChapter050401 {
    public static void main(String[] args) {
        /*
        * (원본 답안)
        */
        Single<Map<String, CarMaker>> single =
                Observable.fromIterable(SampleData.carList)
                        .toMap(
                                car -> car.getCarName(),
                                car -> car.getCarMaker()
                        );
        single.subscribe(map -> Logger.log(LogType.ON_NEXT, map));

        Observable.fromIterable(SampleData.carList)
                // 첫 번째 param은 'key' 값 설정, 두 번째 param은 'value' 값 설정
                .toMap(Car::getCarName, Car::getCarMaker)
                .subscribe(data -> Logger.log(LogType.ON_NEXT, data));
    }
}
