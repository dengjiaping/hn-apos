package me.andpay.timobileframework.mvc;

import me.andpay.timobileframework.mvc.form.DefaultFormProcesser;
import me.andpay.timobileframework.mvc.form.FormProcesser;
import me.andpay.timobileframework.mvc.form.FormProcesser.FormProcessPattern;
import me.andpay.timobileframework.mvc.form.android.AndroidFieldValueLoader;

import com.google.inject.Provider;

public class FormProcessProvider implements Provider<FormProcesser>{
	
	public FormProcesser get(){
		FormProcesser processer = new DefaultFormProcesser(); 
		processer.resigteFieldValueLoader(FormProcessPattern.ANDROID, new AndroidFieldValueLoader());
		return processer;
	}

}
