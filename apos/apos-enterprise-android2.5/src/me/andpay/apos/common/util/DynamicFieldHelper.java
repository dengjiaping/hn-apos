package me.andpay.apos.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.base.FlexFieldDefine;
import me.andpay.apos.R;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.mvc.support.TiApplication;
import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.inject.Inject;

/**
 * 动态字段辅助
 * 
 * @author cpz
 * 
 */
public class DynamicFieldHelper {

	private Map<String, FlexFieldDefine> fieldDefines = new HashMap<String, FlexFieldDefine>();

	@Inject
	private Application application;

	/**
	 * 设置字段名称
	 * 
	 * @param view
	 * @param fieldName
	 */
	public void setTextView(TextView view, String fieldName, String txnType) {

		FlexFieldDefine flexFieldDefine = getFieldDefine(fieldName, txnType);
		if (flexFieldDefine != null) {
			view.setText(flexFieldDefine.getFieldNameAlias());
		}
	}

	/**
	 * 设置空间隐藏字体
	 * 
	 * @param view
	 * @param fieldName
	 */
	public void setTextViewHide(TextView view, String fieldName, String txnType) {
		FlexFieldDefine flexFieldDefine = getFieldDefine(fieldName, txnType);
		if (flexFieldDefine != null) {
			view.setHint(flexFieldDefine.getTips());
		}
	}

	/**
	 * 设置控件显示状态
	 * 
	 * @param fieldName
	 * @param views
	 */
	public void setViewShowStatus(String fieldName, String txnType,
			View... views) {

		FlexFieldDefine flexFieldDefine = getFieldDefine(fieldName, txnType);
		int status;
		if (flexFieldDefine != null) {
			status = View.VISIBLE;
		} else {
			status = View.GONE;
		}

		for (int i = 0; i < views.length; i++) {
			views[i].setVisibility(status);
		}
	}

	// /**
	// * 数据校验
	// *
	// * @param input
	// * @param fieldName
	// */
	// public FlexFieldDefine checkLength(String input, String fieldName,
	// String txnType) {
	// FlexFieldDefine flexFieldDefine = getFieldDefine(fieldName, txnType);
	// if (flexFieldDefine != null) {
	//
	// if (input == null || input.length() == 0) {
	// return null;
	// }
	//
	// if(input.length() <= flexFieldDefine.getMaxLength()&&
	// input.length()>=flexFieldDefine.getMaxLength()) {
	// return null;
	// }
	//
	// return flexFieldDefine;
	// }
	// return null;
	// }

	public FlexFieldDefine getFieldDefine(String fieldName, String txnType) {
		return getFieldDefines().get(fieldName + "_" + txnType);
	}

	@SuppressWarnings("unchecked")
	public Map<String, FlexFieldDefine> getFieldDefines() {
		if (fieldDefines.isEmpty()) {
			try {
				TiApplication tiApplication = (TiApplication) application;
				TiContext tiContext = tiApplication.getContextProvider()
						.provider(TiContext.CONTEXT_SCOPE_APPLICATION);
				
				Field field = ClassTypes.class.getField(ClassTypes.FLEX_FIELD_NAME);
				
				Object fieldDefineObj = tiContext
						.getAttribute(field.getGenericType(),RuntimeAttrNames.FIELD_DEFINE);
				setFieldDefines((List<FlexFieldDefine>)fieldDefineObj);
			} catch (Exception e) {
				Log.i("acti", "acti", e);
			}
		}

		return fieldDefines;
	}

	public void setFieldDefines(List<FlexFieldDefine> fieldDefines) {
		if(fieldDefines == null) { 
			return;
		}
		this.fieldDefines.clear();
		for (FlexFieldDefine fieldDefine : fieldDefines) {
			if (fieldDefine.getTxnMode().equals(
					FlexFieldDefine.TXN_MODE_PURCHASE)) {
				this.fieldDefines.put(fieldDefine.getFieldName() + "_"
						+ TxnTypes.PURCHASE, fieldDefine);
			} else if (fieldDefine.getTxnMode().equals(
					FlexFieldDefine.TXN_MODE_REFUND)) {
				this.fieldDefines.put(fieldDefine.getFieldName() + "_"
						+ TxnTypes.REFUND, fieldDefine);
				this.fieldDefines.put(fieldDefine.getFieldName() + "_"
						+ TxnTypes.VOID_PURCHASE, fieldDefine);
			}
		}
	}

	public String checkDate(String fieldName, String txnType, String field) {

		FlexFieldDefine flexFieldDefine = getFieldDefine(fieldName, txnType);

		if (flexFieldDefine != null) {
			if (!flexFieldDefine.isOptional() && StringUtil.isBlank(field)) {
				return flexFieldDefine.getFieldNameAlias()
						+ ResourceUtil.getString(application,
								R.string.com_can_not_null_str);
			}

			if (StringUtil.isBlank(field) && flexFieldDefine.isOptional()) {
				return null;
			}

			if (field.length() < flexFieldDefine.getMinLength()
					|| field.length() > flexFieldDefine.getMaxLength()) {
				return flexFieldDefine.getFieldNameAlias()
						+ ResourceUtil.getString(application,
								R.string.com_check_must_gt_str)
						+ flexFieldDefine.getMinLength()
						+ ResourceUtil.getString(application,
								R.string.com_check_must_lt_str)
						+ flexFieldDefine.getMaxLength()
						+ ResourceUtil.getString(application,
								R.string.com_check_char_count_str);
			}

		}

		return null;
	}

}
