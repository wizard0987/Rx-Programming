package com.rxjava.chapter05.chapter0504;

import com.rxjava.common.Car;
import com.rxjava.common.CarMaker;
import com.rxjava.common.SampleData;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observables.GroupedObservable;

import java.util.concurrent.CompletableFuture;

/**
 * 제조사 별로 그룹으로 묶은 후, 제조사 별 차량 가격의 합계를 구하는 예제
 */
public class ObservableGroupByExample04 {
    public static void main(String[] args) {
        Observable<GroupedObservable<CarMaker, Car>> observable = Observable.fromIterable(SampleData.carList)
                .groupBy(Car::getCarMaker);

        /*
        * 제조사별 차량 판매가 총액 출력 (원본 답안)
        */
        observable.flatMapSingle(groupedCars ->
                Single.fromFuture(CompletableFuture.supplyAsync(() -> groupedCars.getKey()))
                    .zipWith(
                            groupedCars.flatMap(car ->
                                    Observable.fromFuture(CompletableFuture.supplyAsync(() -> car.getCarPrice()))
                            )
                            .reduce((price1, price2) -> price1 + price2)
                            .toSingle(),
                            (carMaker, sum) -> carMaker + " : " + sum
                    )
        )
        .subscribe(System.out::println);

        /*
         * 제조사별 차량 목록 리스트 출력 (1)
         */
        observable.flatMapSingle(carGroup ->
                Single.fromFuture(CompletableFuture.supplyAsync(() -> carGroup.getKey()))
                        .zipWith(
                                carGroup.flatMap(car ->
                                        Observable.fromFuture(CompletableFuture.supplyAsync(() -> car.getCarName()))
                                )
                                .reduce((carName1, carName2) -> carName1 + ", " + carName2)
                                .toSingle(),
                                (carMaker, carList) -> carMaker + " : " + carList
                        )
        )
                .subscribe(System.out::println);

        /*
        * 제조사별 차량 목록 리스트 출력 (2)
        */
        observable.flatMapSingle(carGroup ->
                Single.fromFuture(CompletableFuture.supplyAsync(() -> carGroup.getKey()))
                    .zipWith(
                            carGroup.flatMap(car ->
                                    Observable.fromFuture(CompletableFuture.supplyAsync(() -> car.getCarName()))
                            )
                            .toList(),
                            (carMaker, carList) -> carMaker + " : " + carList
                    )
        )
        .subscribe(System.out::println);
    }
}
