package com.example.aleksandra.a4inrow.presenters;

/**
 * Created by Aleksandra on 02/12/2017.
 */

import com.example.aleksandra.a4inrow.views.IBaseView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * Created by Aleksandra on 02/12/2017.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class BasePresenter<V extends IBaseView> extends MvpBasePresenter<V> {

    /*private CompositeSubscription subscriptions = new CompositeSubscription();*/


    /**
     * Subscribes on response single on <i>io</i> thread and observes on <i>main</i> thread.
     *  //@param <T> response data type
     * //@param response response single
     * //@param onSuccess action on success
     * //@param onError action on error
     */
    protected final <T> void subscribe(/*String response, Action1<String> onSuccess, Action1<Throwable> onError*/) {
        /*Subscription subscription = response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError);
        manageSubscription(subscription);*/
    }

    /**
     * Manages observable subscription to be unsubscribed on destroy.
     *
     * //@param subscription subscription
     */
    protected void manageSubscription(/*Subscription subscription*/) {
        //subscriptions.add(subscription);
    }

    public void onDestroy() {
        /*subscriptions.unsubscribe();*/
    }
}
