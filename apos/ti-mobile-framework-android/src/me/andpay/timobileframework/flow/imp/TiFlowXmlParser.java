package me.andpay.timobileframework.flow.imp;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import me.andpay.timobileframework.flow.TIFlowDiagram;
import me.andpay.timobileframework.flow.TiFlowConfigException;
import me.andpay.timobileframework.flow.TiFlowErrorCode;

/**
 * 用于解析xml文件
 * 
 * @author tinyliu
 * 
 */
public class TiFlowXmlParser {

	public static TIFlowDiagram parseXML(String diagramName, InputStream stream) {

		SAXParserFactory spf = SAXParserFactory.newInstance();

		SAXParser saxParser;

		InputStream inStream = null;
		try {
			saxParser = spf.newSAXParser();
			// 设置解析器的相关特性，http://xml.org/sax/features/namespaces = true
			// 表示开启命名空间特性

			// saxParser.setProperty("http://xml.org/sax/features/namespaces",true)
			TiFlowXmlContentHandler handler = new TiFlowXmlContentHandler(diagramName);
			saxParser.parse(stream, handler);
			return handler.getDiagram();
		} catch (Exception ex) {
			throw new TiFlowConfigException(TiFlowErrorCode.CONFIG_FLOWS_CONFIG_NOTEXISTS,
					"parse the flows xml happend error, the flow xml is " + diagramName, ex);
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {

				}
			}
		}
	}

}
