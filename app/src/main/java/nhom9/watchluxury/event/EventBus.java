package nhom9.watchluxury.event;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public abstract class EventBus<T extends Event> {

    private final PublishSubject<T> subject = PublishSubject.create();

    protected void invoke(T event) {
        subject.onNext(event);
    }

    public Observable<T> getEvents() {
        return subject;
    }
}
