package me.andpay.timobileframework.sqlite.convert;

public interface DataConverter<T,F> {

	public T convertToString(F f);
	
	
	public F convertToObj(T t);

}
