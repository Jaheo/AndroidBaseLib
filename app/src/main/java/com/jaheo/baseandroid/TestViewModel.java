package com.jaheo.baseandroid;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestViewModel extends ViewModel {

    public MutableLiveData<String> testData = new MutableLiveData<>();
}
