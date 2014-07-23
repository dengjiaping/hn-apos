package me.andpay.timobileframework.mvc.form.android;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.andpay.timobileframework.mvc.form.android.componet.AutoCompleteTextViewGetter;
import me.andpay.timobileframework.mvc.form.android.componet.CheckBoxValueGetter;
import me.andpay.timobileframework.mvc.form.android.componet.EditTextValueGetter;
import me.andpay.timobileframework.mvc.form.android.componet.RadioGroupValueGetter;
import me.andpay.timobileframework.mvc.form.android.componet.SpinnerValueGetter;
import me.andpay.timobileframework.mvc.form.android.componet.TextViewValueGetter;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class WidgetValueGetterContainer {
	
	private static Map<String, WidgetValueGetter> getters = new ConcurrentHashMap<String, WidgetValueGetter>();
	/**
	 * 初始化默认组件
	 */
	static {
		getters.put(EditText.class.getName(), new EditTextValueGetter());
		getters.put(CheckBox.class.getName(), new CheckBoxValueGetter());
		getters.put(RadioGroup.class.getName(), new RadioGroupValueGetter());
		getters.put(Spinner.class.getName(), new SpinnerValueGetter());
		getters.put(TextView.class.getName(), new TextViewValueGetter());
		getters.put(AutoCompleteTextView.class.getName(), new AutoCompleteTextViewGetter());
	}
	
	public static void registeGetter(WidgetValueGetter getter) {
		getters.put(getter.supportType(), getter);
	}
	
	public static WidgetValueGetter getGetter(View view) {
		return getters.get(view.getClass().getName());
	}
}
