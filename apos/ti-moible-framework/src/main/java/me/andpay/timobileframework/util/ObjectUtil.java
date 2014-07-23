package me.andpay.timobileframework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectUtil {

	public static <T> T cloneObject(T obj) {

		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out;
			out = new ObjectOutputStream(byteOut);
			out.writeObject(obj);
			ByteArrayInputStream byteIn = new ByteArrayInputStream(
					byteOut.toByteArray());
			ObjectInputStream in = new ObjectInputStream(byteIn);
			Object objnew = in.readObject();
			return (T) objnew;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
