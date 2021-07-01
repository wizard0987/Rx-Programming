package com.rxjava.chapter05.chapter0505.quiz;

import com.rxjava.common.SampleData;
import com.rxjava.utils.LogType;
import com.rxjava.utils.Logger;
import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

/**
 * zip을 이용하여 각 지점별 월별 매출(SampleData.salesOfBranchA, SampleData.salesOfBranchB, SampleData.salesOfBranchC)을
 * 월별로 합산하여 통합 월별 매출을 출력하세요.
 * (지점별 월별 매출 List(salesOfBranchA, salesOfBranchB, salesOfBranchC)는 index가 빠른 요소부터 1월입니다.)
 *
 * Retry!
 */
public class QuizAnswerForChapter050501 {
    public static void main(String[] args) {
        /*
         * (원본 답안)
         */
//        List<Observable<Integer>> salesList = Arrays.asList(
//                Observable.fromIterable(SampleData.salesOfBranchA),
//                Observable.fromIterable(SampleData.salesOfBranchB),
//                Observable.fromIterable(SampleData.salesOfBranchC)
//        );
//
//        Observable.zip(
//                salesList,
//                sales -> (int)sales[0] + (int)sales[1] + (int)sales[2]
//        )
//        .subscribe(data -> Logger.log(LogType.ON_NEXT, data));

        Observable<Integer> branchA = Observable.fromIterable(SampleData.salesOfBranchA);
        Observable<Integer> branchB = Observable.fromIterable(SampleData.salesOfBranchB);
        Observable<Integer> branchC = Observable.fromIterable(SampleData.salesOfBranchC);

        Observable.zip(
                branchA, branchB, branchC,
                (sales1, sales2, sales3) -> sales1 + sales2 + sales3
        )
        .subscribe(data -> Logger.log(LogType.ON_NEXT, data));

    }
}
