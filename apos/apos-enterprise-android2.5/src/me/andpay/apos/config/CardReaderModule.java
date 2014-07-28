package me.andpay.apos.config;

import me.andpay.apos.cardreader.InitMsrKeyServiceImpl;
import me.andpay.apos.cardreader.callback.CardreaderSetSearchCallback;
import me.andpay.apos.cardreader.callback.DefaultCardReaderCallBack;
import me.andpay.apos.cardreader.callback.SearchCardreaderCallBack;
import me.andpay.apos.cardreader.callback.SwipCardReaderCallBack;
import me.andpay.timobileframework.config.TiMobileModule;

import com.google.inject.Scopes;

public class CardReaderModule extends TiMobileModule {

	@Override
	protected void configure() {

		
		bind(SearchCardreaderCallBack.class).in(Scopes.SINGLETON);
		bind(CardreaderSetSearchCallback.class).in(Scopes.SINGLETON);
		bind(DefaultCardReaderCallBack.class).in(Scopes.SINGLETON);
		bind(InitMsrKeyServiceImpl.class).in(Scopes.SINGLETON);
		bind(SwipCardReaderCallBack.class).in(Scopes.SINGLETON);
	}

}
