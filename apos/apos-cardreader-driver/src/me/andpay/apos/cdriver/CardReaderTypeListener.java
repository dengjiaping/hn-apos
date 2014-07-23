package me.andpay.apos.cdriver;

import me.andpay.apos.cdriver.control.CardReaderControl;
import me.andpay.timobileframework.util.ReflectUtil;
import android.app.Application;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

public class CardReaderTypeListener implements TypeListener {

	@Inject
	public Application application;

	public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {

		if (ReflectUtil.isImplInterface(type.getRawType(),
				CardReaderControl.class.getName())) {
			try {
//
//				CardReaderControl conReaderControl = (CardReaderControl) RoboGuiceUtil
//						.getInjectObject(type.getRawType(),
//								application.getApplicationContext());
//
//				CardReaderManager.registerControl(conReaderControl);
			} catch (Exception e) {
				throw new RuntimeException(
						"register cardReaderCantron error! type="
								+ type.getRawType());
			}
		}

	}
}
