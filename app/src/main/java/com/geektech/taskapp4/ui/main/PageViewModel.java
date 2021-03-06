package com.geektech.taskapp4.ui.main;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    public MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    public LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return ""+input;
        }
    });

    void setIndex(int index) {
        mIndex.setValue(index);
    }

    LiveData<String> getText() {
        return mText;
    }
}
