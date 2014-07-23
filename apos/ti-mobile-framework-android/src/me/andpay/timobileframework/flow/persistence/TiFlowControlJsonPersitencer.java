package me.andpay.timobileframework.flow.persistence;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import me.andpay.ti.util.JacksonSerializer;

/**
 * jackson持久化对象
 * @author tinyliu
 *
 */
public class TiFlowControlJsonPersitencer implements TiFlowControlPersistencer {

	public void persistenceData(String path, String storeName, Map<String, Serializable> data) {
		if(data == null || data.isEmpty()) {
			return;
		}
		byte[] bytes = JacksonSerializer.newPrettySerializer().serialize(data);
		try {

			FileOutputStream outStream = new FileOutputStream(new File(path, storeName));
			outStream.write(bytes);
			outStream.close();
		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			return;
		}

	}

	public Map<String, Serializable> restoreData(String path, String storeName, boolean isNeedToDel) {
		try {
			FileInputStream inStream = new FileInputStream(new File(path, storeName));
			;
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				stream.write(buffer, 0, length);
			}
			stream.close();
			inStream.close();
			return JacksonSerializer.newPrettySerializer().deserialize((new HashMap<String, Serializable>()).getClass(),
					stream.toByteArray());
		} catch (Exception e) {}
		finally {
			if(isNeedToDel) {
				File f = new File(path, storeName);
				f.delete();
			}
		}
		return null;
	}

}
