package me.andpay.timobileframework.flow.imp;

import java.util.HashMap;
import java.util.Map;

import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.TIFlowDiagram;
import me.andpay.timobileframework.flow.TiFlowNode;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TiFlowXmlContentHandler extends DefaultHandler {

	private TIFlowDiagram diagram = null;

	private TiFlowNode currentNode = null;

	private TiFlowNodeComplete currentComplete = null;

	private Map<String, String> currentMap = null;

	private String diagramName = null;

	public TiFlowXmlContentHandler(String diagramName) {
		this.diagramName = diagramName;
	}

	/*
	 * 
	 * 接收文档的开始的通知。
	 */

	@Override
	public void startDocument() throws SAXException {
		diagram = new TIFlowDiagram();
		diagram.setDiagramName(diagramName);
	}

	public TIFlowDiagram getDiagram() {
		return this.diagram;
	}

	/*
	 * 
	 * 接收元素开始的通知。
	 * 
	 * 参数意义如下：
	 * 
	 * namespaceURI：元素的命名空间
	 * 
	 * localName ：元素的本地名称（不带前缀）
	 * 
	 * qName ：元素的限定名（带前缀）
	 * 
	 * atts ：元素的属性集合
	 */

	@Override
	public void startElement(String namespaceURI, String localName, String qName,
			Attributes atts) throws SAXException {
		if (StringUtil.isEmpty(localName)) {
			localName = qName;
		}
		if (localName.equals("flow-diagram")){
			diagram.setDiagramVersion(atts.getValue("version"));
			diagram.setBeginNodeName(atts.getValue("begin-node"));
		}

		if (localName.equals("flow-node")) {
			currentNode = new TiFlowNode();
			currentNode.setRefrenceDiagram(this.diagram);
			currentComplete = null;
			currentNode.setNodeName(atts.getValue("node-name"));
			if (!StringUtil.isEmpty(atts.getValue("start-complete"))) {
				currentNode.setStartByComplete(true);
				currentNode.setStartCompleteIdentity(atts.getValue("start-complete"));
			}
			currentNode.setRefClass(atts.getValue("ref-activity"));
			diagram.addFlowNode(currentNode);
		}

		if (localName.startsWith("node-complete")) {
			currentComplete = new TiFlowNodeComplete();
			currentComplete.setRefrenceFlowNode(this.currentNode);
			if (localName.equals("node-complete")) {
				currentComplete.setIdentity(atts.getValue("identity"));
			} else {
				currentComplete.setIdentity(localName.replace("node-complete-", ""));
			}
			currentComplete.setRefClass(atts.getValue("ref-activity"));
			currentComplete.setNextNodeName(atts.getValue("next-node"));
			currentComplete.setForwardType(atts.getValue("forward"));
			currentComplete.setSubFlowName(atts.getValue("sub-flow"));
			currentComplete.setTranferType(atts.getValue("transfer"));
			currentComplete.setSubFinishToComplete(atts.getValue("sub-finish-cpl"));
			currentComplete.setHoldAfterSubFlowFinished(Boolean.valueOf(atts.getValue("hold-sub-finish")));
			currentComplete.setRemoveNode(Boolean.valueOf(atts.getValue("remove-node")));
			currentComplete.setFlushContext(Boolean.valueOf(atts
					.getValue("flush-context")));
			currentComplete.setFinishFlow(Boolean.valueOf(atts.getValue("finish-flow")));
			if (!StringUtil.isEmpty(currentComplete.getSubFlowName())) {
				currentComplete.setForwardNextFlow(true);
			}
			currentNode.getCompletes()
					.put(currentComplete.getIdentity(), currentComplete);
		}
		if (localName.equals("flow-send-data")) {
			if (!StringUtil.isEmpty(currentComplete.getTranferType())) {
				currentMap = null;
				return;
			}
			currentMap = new HashMap<String, String>();
			currentComplete.setTranferType(atts.getValue("transfer"));
			currentComplete.setUnTransferData(currentMap);
		}
		if (localName.equals("entry")) {
			if (currentMap == null) {
				return;
			}
			currentMap.put(atts.getValue("key"), atts.getValue("value"));
		}
	}
}
