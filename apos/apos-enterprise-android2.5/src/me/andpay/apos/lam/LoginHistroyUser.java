package me.andpay.apos.lam;

import java.util.LinkedList;

import me.andpay.ti.util.StringUtil;

public class LoginHistroyUser {

	/**
	 * 用户保存数
	 */
	public static final int SAVE_COUNT = 3;
	/**
	 * 分隔符
	 */
	public static final String SEPARATOR = ",";

	public LinkedList<String> users = new LinkedList<String>();

	public LoginHistroyUser(String oldUsers, String newUser) {

		if (StringUtil.isBlank(oldUsers)) {
			users.add(newUser);
			return;
		}

		String[] oldUserArray = oldUsers.split(SEPARATOR);
		for (String user : oldUserArray) {
			users.add(user);
		}

		if (users.contains(newUser)) {
			return;
		}

		if (users.size() == SAVE_COUNT) {
			users.removeFirst();
		}

		users.add(newUser);

	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (String user : users) {
			buf.append(user).append(SEPARATOR);
		}
		return buf.deleteCharAt(buf.length() - 1).toString();
	}

}
