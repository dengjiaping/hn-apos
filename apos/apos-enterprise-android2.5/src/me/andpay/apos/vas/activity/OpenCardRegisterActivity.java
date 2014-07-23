package me.andpay.apos.vas.activity;

import me.andpay.ac.consts.CardHolderCertTypes;
import me.andpay.ac.consts.ShopProductTypes;
import me.andpay.apos.R;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.cmview.tispinner.TiNewSpinner;
import me.andpay.apos.cmview.tispinner.TiNewSpinner.OnSpinnerItemClickListener;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.PreviousClickEventController;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.common.util.ValidateHelper;
import me.andpay.apos.vas.VasProvider;
import me.andpay.apos.vas.activity.adapter.IdTypeSipinnerAdapter;
import me.andpay.apos.vas.activity.adapter.IdTypeSpinnerItemFactory;
import me.andpay.apos.vas.callback.OpenCardCallbackImpl;
import me.andpay.apos.vas.event.OpenCardClickControl;
import me.andpay.apos.vas.event.TypesSpinnerSpinnerItemClickConrol;
import me.andpay.apos.vas.flow.model.OpenCardContext;
import me.andpay.apos.vas.form.OpenCardForm;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.form.FormProcesser;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.mvc.form.annotation.FormBind;
import me.andpay.timobileframework.mvc.form.exception.FormException;
import me.andpay.timobileframework.mvc.form.validation.ValidateErrorInfo;
import me.andpay.timobileframework.util.IDCardUtil;
import me.andpay.timobileframework.util.IntentDataConvert;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

/**
 * 开卡用户登记
 * 
 * @author cpz
 * 
 */
@ContentView(R.layout.vas_open_card_register_layout)
@FormBind(formBean = OpenCardForm.class)
public class OpenCardRegisterActivity extends AposBaseActivity implements
		ValueContainer {

	@InjectView(R.id.vas_open_card_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = true, delegateClass = OnClickListener.class, toEventController = OpenCardClickControl.class)
	public Button openCardBt;

	public CommonDialog openCardDialog;

	@Inject
	private FormProcesser formProcesser;

	OpenCardContext openCardContext;

	@InjectView(R.id.vas_telno_text)
	public TextView holderPhone;
	@InjectView(R.id.vas_repeat_telno_text)
	public TextView reHolderPhone;

	@InjectView(R.id.vas_id_type_spinner)
	@EventDelegate(delegateClass = OnSpinnerItemClickListener.class, toEventController = TypesSpinnerSpinnerItemClickConrol.class)
	public TiNewSpinner typesSpinner;

	@InjectView(R.id.com_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = PreviousClickEventController.class)
	ImageView backBtn;

	@InjectView(R.id.vas_id_NO_text)
	public TextView idNoText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		typesSpinner.setAdadter(new IdTypeSipinnerAdapter(
				IdTypeSpinnerItemFactory.getItems(), this));
		typesSpinner.selectItem(0);
	}

	@Override
	protected void onResumeProcess() {
		openCardContext = IntentDataConvert.getActivityContext(
				OpenCardContext.class, getIntent());
	
		backBtn.setVisibility(openCardContext.isBack() ? View.VISIBLE : View.GONE);
		String productType = openCardContext.getProductType();
		if (ShopProductTypes.E_SVC.equals(productType)) {
			holderPhone.setHint(R.string.vas_tel_no_must_str);
			reHolderPhone.setHint(ResourceUtil.getString(this,
					R.string.vas_repeat_tel_no_must_str));
		} else if (ShopProductTypes.PHYSICAL_SVC.equals(productType)) {
			holderPhone.setHint(ResourceUtil.getString(this,
					R.string.vas_tel_no_str));
			reHolderPhone.setHint(ResourceUtil.getString(this,
					R.string.vas_repeat_tel_no_str));
		}

	}

	public ValidateErrorInfo checkInputData(OpenCardForm openCardForm) {

		String productType = openCardContext.getProductType();
		ValidateErrorInfo validateErrorInfo = new ValidateErrorInfo(null, null);
		if (ShopProductTypes.E_SVC.equals(productType)) {
			if (StringUtil.isBlank(holderPhone.getText().toString())) {
				validateErrorInfo.setErrorDescription("请输入手机号。");
				validateErrorInfo.setParamId(holderPhone.getId());
				return validateErrorInfo;
			}
		}

		if (StringUtil.isNotBlank(openCardForm.getIdNo())
				&& (openCardForm.getIdType() == null || openCardForm
						.getIdType().equals(CardHolderCertTypes.ID))) {
			if (!IDCardUtil.validate(openCardForm.getIdNo())) {
				validateErrorInfo.setErrorDescription("身份证号校验失败。");
				validateErrorInfo.setParamId(idNoText.getId());
				return validateErrorInfo;
			}
		}
		return null;
	}

	public void openCardSubmit(FormBean formBean) {
		if (formBean == null) {
			try {
				formBean = formProcesser.loadFormBean(this,
						FormProcesser.FormProcessPattern.ANDROID);
			} catch (FormException e) {
			}
		}
		OpenCardForm openCardForm = (OpenCardForm) formBean.getData();
		if (ValidateHelper.validate(this, formBean, null)) {
			return;
		}

		ValidateErrorInfo validateErrorInfo = checkInputData(openCardForm);
		if (validateErrorInfo != null) {
			ValidateHelper.showErrorMsg(this, validateErrorInfo);
			return;
		}

		openCardForm.setOrderId(openCardContext.getOrderId());
		openCardForm.setEndBlankPartCardNo(openCardContext
				.getEndBlankPartCardNo());
		openCardForm.setStartBlankPartCardNo(openCardContext
				.getStartBlankPartCardNo());

		openCardContext.setPhoneNo(openCardForm.getPhoneNo());

		this.openCardDialog = new CommonDialog(this, "处理中...");
		this.openCardDialog.show();
		EventRequest request = this.generateSubmitRequest(this);
		request.open(formBean, Pattern.async);
		request.callBack(new OpenCardCallbackImpl(this));
		request.submit();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
