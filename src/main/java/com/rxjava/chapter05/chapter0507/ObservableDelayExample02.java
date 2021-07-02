package com.rxjava.chapter05.chapter0507;

import com.rxjava.utils.LogType;
import com.rxjava.utils.Logger;
import com.rxjava.utils.TimeUtil;
import io.reactivex.Observable;

/**
 * 통지되는 데이터 각각에 지연 시간(delay)을 적용하는 예제 : delay() - 2 번째 유형
 */
public class ObservableDelayExample02 {
    public static void main(String[] args) {
        Observable.just(1, 3, 5, 7)
                .delay(item -> {
                    Logger.log("# item : " + item);
                    TimeUtil.sleep(1000L);
                /*
                 * Observable.empty()로 통지(emit)되는 데이터 자체가 없는 (비어있는) Observable을 반환을 한다.
                 * 그럼에도 불구하고 해당 코드를 실행하면 데이터(1, 3, 5, 7)가 소비자 쪽에 최종적으로 전달이
                 * 될 수 있는 것을 알 수 있다.
                 *
                 * 즉, delay() 함수 안에서 반환하는 Observable의 역할은 이 Observble이 데이터를 통지하는 시점까지
                 * 원본 데이터소스의 데이터 통지를 '지연시키는 것만이 목적'인 것이다.
                 *
                 * 그렇기 때문에 원본 데이터가 아닌 다른 데이터를 통지하더라도 소비자 쪽에 최종적으로 전달이 되는 데이터는
                 * delay 함수를 호출하기전의 원본 데이터소스에서 통지된 데이터가 된다.
                 *
                 * 결론적으로 delay 함수에서 return되는 Observable에서 통지되는 데이터들은 그냥 버려진다고 생각하면 된다.
                 */
                    // delay( ) 함수 내에서 생성된 Observable의 데이터는 출력이 안된다는 것을 확인할 수 있다.
                    //return Observable.just(10, 8, 6, 4);
                    return Observable.empty();
                })
                .subscribe(data -> Logger.log(LogType.ON_NEXT, data));
    }
}
