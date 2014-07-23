package me.andpay.timobileframework.util.tlv;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.andpay.ti.util.ByteUtil;
import me.andpay.timobileframework.util.HexUtils;

public class TlvUtil {

	public static <T> String encodeTvl(T tlvObj) throws RuntimeException {

		try {
			Map<Integer, Field> tlvFields = new TreeMap<Integer, Field>();

			Field[] fields = tlvObj.getClass().getDeclaredFields();
			for (Field field : fields) {
				TlvField tlvFieldAnnotation = field
						.getAnnotation(TlvField.class);
				if(tlvFieldAnnotation == null) {
					continue;
				}
				tlvFields.put(tlvFieldAnnotation.index(), field);
			}

			StringBuffer hexStr = new StringBuffer();
			for (Field field : tlvFields.values()) {
				TlvField tlvFieldAnnotation = field
						.getAnnotation(TlvField.class);
				field.setAccessible(true);
				Object value = field.get(tlvObj);
				if(value == null) {
					continue;
				}

				Class<?> tlvType = field.getType();

				ConvertData convertDataAnnotation = field
						.getAnnotation(ConvertData.class);
				byte[] tlvValueByte = null;
				if (null != convertDataAnnotation) {
					Class<? extends DataConvertor<?>> dataConvertorClass = convertDataAnnotation
							.value();
					DataConvertor<?> dataConvertor = dataConvertorClass
							.newInstance();
					tlvValueByte = dataConvertor.convertByte(value);

				
				} else if (tlvType.isArray()
						&& Byte.TYPE == tlvType.getComponentType()) {
					tlvValueByte =(byte[]) value;
				} else {
					tlvValueByte = value.toString().getBytes(); 
				}

				hexStr.append(tlvFieldAnnotation.value())
						.append(HexUtils.bytesToHexString( getTLVLength(tlvValueByte.length)))
						.append(HexUtils.bytesToHexString(tlvValueByte));

			}

			return hexStr.toString().toUpperCase();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static <T> T decodeTlv(byte[] file, Class<T> tlvClass)
			throws Exception {
		Field[] fields = tlvClass.getDeclaredFields();
		Map<String, Field> tlvFields = new HashMap<String, Field>();
		for (Field field : fields) {
			TlvField tlvFieldAnnotation = field.getAnnotation(TlvField.class);
			if (null == tlvFieldAnnotation) {
				continue;
			}
			String tagId = tlvFieldAnnotation.value();
			if (tlvFields.containsKey(tagId)) {
				throw new IllegalArgumentException("TLV field duplicate: "
						+ tagId);
			}
			tlvFields.put(tagId, field);
		}
		T tlvObject = tlvClass.newInstance();

		int idx = 0;
		while (idx < file.length - 1) {
			byte tag1 = file[idx];
			String tag = null;
			tag = HexUtils.bytesToHexString(new byte[]{tag1}).toUpperCase();
			if(tag.substring(1,2).equals("F")) {
				idx++;
				byte tag2 = file[idx];
				byte[] tags2 = { tag1, tag2 };
				tag = HexUtils.bytesToHexString(tags2).toUpperCase();
			}

			idx++;
			byte lengthByte = file[idx];
			int length = lengthByte & 0x7f;
			while ((lengthByte & 0x80) == 0x80) {
				idx++;
				lengthByte = file[idx];
				length = (length << 7) + (lengthByte & 0x7f);
			}
			idx++;
	
			if (tlvFields.containsKey(tag)) {
				Field tlvField = tlvFields.get(tag);
				Class<?> tlvType = tlvField.getType();
				ConvertData convertDataAnnotation = tlvField
						.getAnnotation(ConvertData.class);
				byte[] tlvValue = copy(file, idx, length);
				Object fieldValue;
				if (null != convertDataAnnotation) {
					Class<? extends DataConvertor<?>> dataConvertorClass = convertDataAnnotation
							.value();
					DataConvertor<?> dataConvertor = dataConvertorClass
							.newInstance();
					fieldValue = dataConvertor.convert(tlvValue);
				} else if (String.class == tlvType) {
					fieldValue = new String(tlvValue, "UTF-8");
				} else if (Boolean.TYPE == tlvType) {
					fieldValue = true;
				} else if (tlvType.isArray()
						&& Byte.TYPE == tlvType.getComponentType()) {
					fieldValue = tlvValue;
				} else {
					throw new IllegalArgumentException(
							"unsupported field type: " + tlvType.getName());
				}
				tlvField.setAccessible(true);
				if (null != tlvField.get(tlvObject)
						&& false == tlvField.getType().isPrimitive()) {
					throw new RuntimeException("field was already set: "
							+ tlvField.getName());
				}
				tlvField.set(tlvObject, fieldValue);
			} else {

			}
			idx += length;
		}
		return tlvObject;

	}

	public static <T> T parse(byte[] file, Class<T> tlvClass) {
		T t;
		try {
			t = decodeTlv(file, tlvClass);
		} catch (Exception e) {
			throw new RuntimeException("error parsing file: "
					+ tlvClass.getName(), e);
		}
		return t;
	}

	public static <T> T decodeTlv(String hexString, Class<T> tlvClass) {

		byte[] file = HexUtils.hexString2Bytes(hexString);
		T t;
		try {
			t = decodeTlv(file, tlvClass);
		} catch (Exception e) {
			throw new RuntimeException("error parsing file: "
					+ tlvClass.getName(), e);
		}
		return t;

	}

	private static byte[] copy(byte[] source, int idx, int count) {
		byte[] result = new byte[count];
		System.arraycopy(source, idx, result, 0, count);
		return result;
	}
	
	private static byte[] getTLVLength(int length) {
		byte[] lenInBytes = ByteUtil.parseBytes(length);

		if (lenInBytes.length == 1 && length <= 127) {
			// 内容长度在0-127，长度位为1字节
			return lenInBytes;
		}

		// 内容长度大于127，1字节表示长度所占字节数，N字节表示实际长度
		return ByteUtil.concat(new byte[] { (byte) (0x80 | lenInBytes.length) }, lenInBytes);
	}

}
