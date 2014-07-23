package me.andpay.apos.vas.activity.adapter;

import java.util.ArrayList;
import java.util.List;

import me.andpay.ac.consts.CardHolderCertTypes;
import me.andpay.apos.cmview.tispinner.TiSpinnerItem;

public class IdTypeSpinnerItemFactory {
	
	private static List<TiSpinnerItem> tiSpinnerItems = new ArrayList<TiSpinnerItem>();

	static {
		addItem(CardHolderCertTypes.ID,"身份证号");
		addItem(CardHolderCertTypes.MILITARY_OFFICER_CERT,"军官证");
		addItem(CardHolderCertTypes.PASSPORT,"护照");
		addItem(CardHolderCertTypes.HOME_VISITING_CERT,"回乡证");
		addItem(CardHolderCertTypes.TAIWAN_COMP_CERT,"台胞证");
		addItem(CardHolderCertTypes.POLICE_OFFICER_CERT,"警官证");
		addItem(CardHolderCertTypes.SOLDIER_CERT,"士兵证");
		addItem(CardHolderCertTypes.OTHER,"其他");

	}

	private static void addItem(String id, String name) {
		TiSpinnerItem tiSpinnerItem = new TiSpinnerItem();
		tiSpinnerItem.setName(name);
		tiSpinnerItem.setValue(id);
		tiSpinnerItems.add(tiSpinnerItem);
	}
	
	public static List<TiSpinnerItem> getItems() {
		return tiSpinnerItems;
	}
}
