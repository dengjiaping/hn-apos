package me.andpay.timobileframework.flow.imp;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import me.andpay.timobileframework.flow.TIFlowDiagram;
import me.andpay.timobileframework.flow.TiFlowConfigException;
import me.andpay.timobileframework.flow.TiFlowControlConfigLoader;
import me.andpay.timobileframework.flow.TiFlowErrorCode;
import me.andpay.timobileframework.flow.TiFlowException;
import me.andpay.timobileframework.flow.TiFlowForward;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import me.andpay.timobileframework.util.ReflectUtil;

/**
 * TiFlow 配置文件加载
 * 
 * @author tinyliu
 * 
 */
public class TiFlowContrlAndroidConfigLoader implements
		TiFlowControlConfigLoader {

	static final String FLOW_XML_FOLDER = "flows/";

	static final String FLOW_XML_PROPERTIES = "flows/tiflow.properties";

	static final String FLOW_TRANSFER_PROPERTIES = "flows/tiflow-transfer.properties";

	static final String FLOW_FORWARD_PROPERTIES = "flows/tiflow-forward.properties";

	public Map<String, TIFlowDiagram> loadFlow() throws TiFlowConfigException {
		InputStream stream = null;
		Map<String, TIFlowDiagram> diagrams = null;
		try {
			stream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(FLOW_XML_PROPERTIES);
			if (stream == null) {
				throw new TiFlowConfigException(
						TiFlowErrorCode.CONFIG_FLOWS_CONFIG_NOTEXISTS,
						"the flows folder is not exists");
			}
			Properties prop = new Properties();
			try {
				prop.load(stream);
			} catch (Exception e) {
				throw new TiFlowConfigException(
						TiFlowErrorCode.CONFIG_FLOWS_CONFIG_NOTEXISTS,
						"the flows properties file not exists", e);
			}
			if (prop == null || prop.keySet().isEmpty()) {
				throw new TiFlowConfigException(
						TiFlowErrorCode.CONFIG_FLOWS_CONFIG_NOTEXISTS,
						"the flows xml is not exists");
			}
			diagrams = new HashMap<String, TIFlowDiagram>();
			for (Object diagramName : prop.keySet()) {
				InputStream xmlStream = Thread
						.currentThread()
						.getContextClassLoader()
						.getResourceAsStream(
								FLOW_XML_FOLDER
										+ prop.getProperty(diagramName
												.toString()));
				if (stream == null) {
					throw new TiFlowConfigException(
							TiFlowErrorCode.CONFIG_FLOWS_CONFIG_NOTEXISTS,
							"the flows folder is not exists");
				}
				TIFlowDiagram diagram = TiFlowXmlParser.parseXML(
						diagramName.toString(), xmlStream);
				diagrams.put(diagram.getDiagramName(), diagram);
				try {
					stream.close();
				} catch (IOException e) {
				}
			}

		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
			}
		}
		return diagrams;
	}

	public <T> Map<String, T> loadConfig(Class<T> objClass, String properties) {
		InputStream stream = null;
		Map<String, T> configs = new HashMap<String, T>();
		stream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(properties);
		if (stream == null) {
			return configs;
		}
		Properties prop = new Properties();
		try {
			prop.load(stream);
		} catch (Exception e) {
			return configs;
		}
		if (prop == null || prop.keySet().isEmpty()) {
			return configs;
		}
		for (Object key : prop.keySet()) {
			String objName = prop.getProperty(key.toString());
			T obj = (T) getClassObj(objName, objClass);
			configs.put(key.toString(), obj);
		}
		return configs;
	}

	public Map<String, TiFlowNodeDataTransfer> loadTransfer()
			throws TiFlowConfigException {
		return this.loadConfig(TiFlowNodeDataTransfer.class,
				FLOW_TRANSFER_PROPERTIES);
	}

	public Map<String, TiFlowForward> loadForward()
			throws TiFlowConfigException {
		return this.loadConfig(TiFlowForward.class, FLOW_FORWARD_PROPERTIES);
	}

	protected Object getClassObj(String classStr, Class<?> interfaceClass) {
		try {
			Class<?> tempClass = Class.forName(classStr);
			if (!ReflectUtil.isImplInterface(tempClass,
					interfaceClass.getName())) {
				throw new ClassNotFoundException("not implements "
						+ interfaceClass.getName());
			}
			return tempClass.newInstance();
		} catch (Exception e) {
			throw new TiFlowException(TiFlowException.TIFLOWEX_GROUP_PROCESS,
					TiFlowErrorCode.PROCESS_SUBFLOW_ISNULL,
					" the load Class happend error, class type is " + classStr,
					e);
		}
	}
}
