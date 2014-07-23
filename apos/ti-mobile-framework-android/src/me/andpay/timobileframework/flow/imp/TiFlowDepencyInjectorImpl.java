package me.andpay.timobileframework.flow.imp;

import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.TIFlowDiagram;
import me.andpay.timobileframework.flow.TiFlowAdmin;
import me.andpay.timobileframework.flow.TiFlowConfigException;
import me.andpay.timobileframework.flow.TiFlowDepencyInjector;
import me.andpay.timobileframework.flow.TiFlowErrorCode;
import me.andpay.timobileframework.flow.TiFlowForward;
import me.andpay.timobileframework.flow.TiFlowNode;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;

/**
 * 用于完成所有Tidiagram中所有依赖注入以及有效性检查
 * 
 * 
 * @author tinyliu
 * 
 */
public class TiFlowDepencyInjectorImpl implements TiFlowDepencyInjector {

	public void injectorDiagram(TIFlowDiagram diagram, TiFlowAdmin admin) {
		//注入状态图启动跳转服务
		String diagramStartForward = diagram.getForward();
		if (StringUtil.isEmpty(diagramStartForward)) {
			diagramStartForward = TiFlowForward.TIFLOW_FORWARD_TYPE_START;
		}
		TiFlowForward forward = admin.getFlowForward(diagramStartForward);
		if (forward == null) {
			throw new TiFlowConfigException(
					TiFlowErrorCode.CONFIG_FLOWS_FORWARD_NOTEXISTS,
					"the diagram forward not exists, the diagram is "
							+ diagram.getDiagramName());
		}
		diagram.setFlowForward(forward);

		if (StringUtil.isEmpty(diagram.getBeginNodeName())
				|| diagram.getBeginNode() == null) {
			throw new TiFlowConfigException(TiFlowErrorCode.CONFIG_FLOWS_NODES_NOTEXISTS,
					"the diagram beginNode not exists, the diagram is "
							+ diagram.getDiagramName());
		}
		if (diagram.getNodes().isEmpty()) {
			throw new TiFlowConfigException(TiFlowErrorCode.CONFIG_FLOWS_NODES_NOTEXISTS,
					"the diagram nodeSize is 0, the diagram is "
							+ diagram.getDiagramName());
		}
		for (TiFlowNode node : diagram.getNodes().values()) {
			injectNode(node, admin, diagram);
		}
	}

	protected void injectNode(TiFlowNode node, TiFlowAdmin admin, TIFlowDiagram diagram) {
		try {
			if (diagram.getBeginNodeName().equals(node.getNodeName())
					&& node.getRefClass() == null) {
				// ignore
			} else {
				Class.forName(node.getRefClass());//创建节点所在的activity
			}
		} catch (Exception e) {
			throw new TiFlowConfigException(
					TiFlowErrorCode.CONFIG_FLOWS_NODES_CONFIGERROR,
					String.format(
							"the node refClass is error, diagram Name is [%s], node is [%s]",
							node.getRefrenceDiagram().getDiagramName(),
							node.getNodeName()), e);
		}
		if (node.getCompletes().isEmpty()) {
			throw new TiFlowConfigException(
					TiFlowErrorCode.CONFIG_FLOWS_NODES_CONFIGERROR,
					String.format(
							"the node complete is empty, diagram Name is [%s], node is [%s]",
							node.getRefrenceDiagram().getDiagramName(),
							node.getNodeName()));
		}
		if (node.isStartByComplete()
				&& !diagram.getBeginNode().getNodeName()
						.equalsIgnoreCase(node.getNodeName())) {
			throw new TiFlowConfigException(
					TiFlowErrorCode.CONFIG_FLOWS_NODES_CONFIGERROR,
					String.format(
							"the start-complete attr must be define on the beginNode  , diagram Name is [%s], node is [%s]",
							node.getRefrenceDiagram().getDiagramName(),
							node.getNodeName()));
		}
		if (node.isStartByComplete() && node.getStartComplete() == null) {
			throw new TiFlowConfigException(
					TiFlowErrorCode.CONFIG_FLOWS_NODES_CONFIGERROR,
					String.format(
							"the start-complete not define , diagram Name is [%s], node is [%s]",
							node.getRefrenceDiagram().getDiagramName(),
							node.getNodeName()));
		}
		for (TiFlowNodeComplete complete : node.getCompletes().values()) {
			this.injectComplete(complete, admin);
		}
	}

	protected void injectComplete(TiFlowNodeComplete complete, TiFlowAdmin admin) {
		if (!complete.isFinishFlow() && StringUtil.isEmpty(complete.getRefClass())
				&& StringUtil.isEmpty(complete.getNextNodeName())
				&& StringUtil.isEmpty(complete.getSubFlowName())) {
			throw new TiFlowConfigException(
					TiFlowErrorCode.CONFIG_FLOWS_NODES_CONFIGERROR,
					String.format(
							"the node complete not config next Activity Class, the complete info = [%s]",
							complete.toString()));
		}
		if (!StringUtil.isEmpty(complete.getRefClass())) {
			try {
				Class.forName(complete.getRefClass());
			} catch (Exception e) {
				throw new TiFlowConfigException(
						TiFlowErrorCode.CONFIG_FLOWS_NODES_CONFIGERROR,
						String.format(
								"the complete refClass is error,  the complete info = [%s]",
								complete.toString()), e);
			}
		}
		if (!StringUtil.isEmpty(complete.getNextNodeName())
				&& complete.getRefrenceFlowNode().getRefrenceDiagram()
						.getNodeByName(complete.getNextNodeName()) == null) {
			throw new TiFlowConfigException(
					TiFlowErrorCode.CONFIG_FLOWS_NODES_CONFIGERROR,
					String.format(
							"the complete next node not exists,  the complete info = [%s]",
							complete.toString()));
		}
		if (!StringUtil.isEmpty(complete.getSubFinishToComplete())
				&& complete.getRefrenceFlowNode().getComplete(
						complete.getSubFinishToComplete()) == null) {
			throw new TiFlowConfigException(
					TiFlowErrorCode.CONFIG_FLOWS_NODES_CONFIGERROR,
					String.format(
							"the sub flow finised to complete next node not exists,  the complete info = [%s]",
							complete.toString()));
		}
		if (!StringUtil.isEmpty(complete.getSubFlowName())
				&& admin.getFlowStatusControl(complete.getSubFlowName()) == null) {
			throw new TiFlowConfigException(
					TiFlowErrorCode.CONFIG_FLOWS_NODES_CONFIGERROR,
					String.format(
							"the complete next sub flow not exists,  the complete info = [%s]",
							complete.toString()));
		}
		String forwardType = StringUtil.isEmpty(complete.getForwardType()) ? TiFlowForward.TIFLOW_FORWARD_TYPE_START
				: complete.getForwardType();
		TiFlowForward forward = admin.getFlowForward(forwardType);
		if (forward == null) {
			throw new TiFlowConfigException(
					TiFlowErrorCode.CONFIG_FLOWS_NODES_CONFIGERROR, String.format(
							"the complete forward not exists,  the complete info = [%s]",
							complete.toString()));
		}
		complete.setForward(forward);
		if (!StringUtil.isEmpty(complete.getTranferType())) {
			TiFlowNodeDataTransfer transfer = admin.getFlowDataTransfer(complete
					.getTranferType());
			if (transfer == null) {
				throw new TiFlowConfigException(
						TiFlowErrorCode.CONFIG_FLOWS_NODES_CONFIGERROR,
						String.format(
								"the complete transfer not exists,  the complete info = [%s]",
								complete.toString()));
			}
			complete.setTransfer(transfer);
		}
	}
}
