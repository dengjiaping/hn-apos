package me.andpay.apos.scm.event;

import java.util.HashMap;
import java.util.Map;

import me.andpay.apos.R;
import me.andpay.apos.common.constant.AposContext;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.flow.FlowNames;
import me.andpay.apos.common.log.OperationCodes;
import me.andpay.apos.scm.ScmProvider;
import me.andpay.apos.scm.flow.model.CardReaderSetContext;
import me.andpay.apos.ssm.SsmProvider;
import me.andpay.timobileframework.flow.TiFlowControl;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.inject.Inject;

public class IntentOnclickController extends AbstractEventController {

	private Map<Integer, String> intentAction = new HashMap<Integer, String>();
	
	@Inject
	private AposContext aposContext;

	public IntentOnclickController() {
		
		intentAction
				.put(R.id.scm_main_pwd_layout, ScmProvider.SCM_ACTIVITY_PWD);
		intentAction
				.put(R.id.scm_main_set_layout, ScmProvider.SCM_ACTIVITY_SET);
		intentAction.put(R.id.scm_main_help_layout,
				ScmProvider.SCM_ACTIVITY_HELP);
		intentAction.put(R.id.scm_main_update_layout,
				ScmProvider.SCM_ACTIVITY_UPDATE);
		intentAction.put(R.id.scm_main_about_layout,
				ScmProvider.SCM_ACTIVITY_ABOUT);
		intentAction.put(R.id.scm_party_limit_layout,
				ScmProvider.SCM_ACTIVITY_PARTY_LIMIT);
		intentAction.put(R.id.scm_settle_layout, SsmProvider.SSM_ACTIVITY_MAIN);

	}

	public boolean onTouch(Activity refActivty, FormBean formBean, View v,
			MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_UP
				|| event.getAction() == MotionEvent.ACTION_CANCEL) {
			String actionStr  = intentAction.get(v.getId());
			if(actionStr.equals(ScmProvider.SCM_ACTIVITY_SET)) {
			  TiFlowControl tiFlowControl =	TiFlowControlImpl.instanceControl();
			  tiFlowControl.startFlow(refActivty, FlowNames.SCM_CARDREADER_SET_FLOW);
			  CardReaderSetContext cardReaderSetContext =  new CardReaderSetContext();
			  
			  Object cardreaderType = aposContext.getAppConfig().getAttribute(ConfigAttrNames.CARD_READER_TYPE);
			  if(cardreaderType==null || cardreaderType.toString().trim().equals("")) {
				  cardReaderSetContext.setHasSelectCardreader(false); 
			  }else {
				  cardReaderSetContext.setHasSelectCardreader(true);
				  cardReaderSetContext.setCardReaderType(Integer.valueOf(cardreaderType.toString()));
			  }
			  
			  cardReaderSetContext.setOpTraceNo(String.valueOf(System.currentTimeMillis()));
			  tiFlowControl.setFlowContextData(cardReaderSetContext);
			}else {
				Intent intent = new Intent(actionStr);
				refActivty.startActivity(intent);
			}
		
			v.setPressed(false);
			RelativeLayout rel = (RelativeLayout) v;
			for (int i = 0; i < rel.getChildCount(); i++) {
				rel.getChildAt(i).setPressed(false);
			}

		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			RelativeLayout rel = (RelativeLayout) v;
			v.setPressed(true);
			for (int i = 0; i < rel.getChildCount(); i++) {
				rel.getChildAt(i).setPressed(true);
			}
		}
		return true;
	}



}
