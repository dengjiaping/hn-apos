package me.andpay.timobileframework.flow;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import me.andpay.timobileframework.flow.forward.TiFlowDefaultForward;
import me.andpay.timobileframework.flow.forward.TiFlowWaitForResultForward;
import me.andpay.timobileframework.flow.imp.TiFlowContrlAndroidConfigLoader;
import me.andpay.timobileframework.flow.imp.TiFlowControlStatusRecordImpl;
import me.andpay.timobileframework.flow.imp.TiFlowDepencyInjectorImpl;
import me.andpay.timobileframework.flow.persistence.TiFlowControlJsonPersitencer;
import me.andpay.timobileframework.flow.persistence.TiFlowControlPersistDataOperator;
import me.andpay.timobileframework.flow.persistence.TiFlowControlPersistencer;
import me.andpay.timobileframework.flow.transfer.TiFlowStringResourceTransfer;
import android.app.Activity;
import android.content.Context;

/**
 * TiFlow控制台 1.加载flow配置文件。 2.提供FlowControl获取服务
 * 
 * @author tinyliu
 * 
 */
public final class TiFlowAdmin {

	/**
	 * 存储文件名称
	 */
	private static final String PERSISTENCE_RECORD_NAME = "record.save";

	private static final String PERSISTENCE_CONTEXT_NAME = "contextData.save";

	private static final String TRANSFER_STRINGS_KEY = "strings";

	private static TiFlowAdmin admin = null;

	private TiFlowControlConfigLoader loader;

	private TiFlowControlFactory factory;

	private TiFlowDepencyInjector injector;

	private TiFlowControlStatusRecord recorder;

	private Map<String, TiFlowStatusControl> controlCaches;

	private Map<String, TiFlowForward> forwardCache;

	private TiFlowControlPersistencer persistencer;

	private Map<String, TiFlowNodeDataTransfer> transferCache;

	public static TiFlowAdmin singletonInstance() {
		if (admin == null) {
			admin = new TiFlowAdmin();
			admin.flowInit();
		}
		return admin;
	}

	TiFlowAdmin() {
		this.loader = new TiFlowContrlAndroidConfigLoader();
		this.factory = new TiFlowControlFactory();
		controlCaches = new HashMap<String, TiFlowStatusControl>();
		forwardCache = new HashMap<String, TiFlowForward>();
		transferCache = new HashMap<String, TiFlowNodeDataTransfer>();
		injector = new TiFlowDepencyInjectorImpl();
		persistencer = new TiFlowControlJsonPersitencer();
		this.recorder = new TiFlowControlStatusRecordImpl();
		initForward();
		initTransfer();
	}

	/**
	 * 初始化默认forward
	 */
	void initForward() {
		TiFlowForward forward = new TiFlowDefaultForward();
		this.forwardCache.put(TiFlowForward.TIFLOW_FORWARD_TYPE_START, forward);
		forward = new TiFlowWaitForResultForward();
		this.forwardCache.put(TiFlowForward.TIFLOW_FORWARD_TYPE_STARTFORRESULT, forward);
	}

	void initTransfer() {
		TiFlowNodeDataTransfer transfer = new TiFlowStringResourceTransfer();
		transferCache.put(TRANSFER_STRINGS_KEY, transfer);
	}

	/**
	 * 依赖注入完成后，初始化对象
	 */
	public void flowInit() {
		Map<String,TIFlowDiagram> diagrams = loader.loadFlow();//加载流程图
		this.forwardCache.putAll(loader.loadForward());
		this.transferCache.putAll(loader.loadTransfer());
		for (String key : diagrams.keySet()){
			TIFlowDiagram digram = diagrams.get(key);
			TiFlowStatusControl control = this.factory.newControl(this, digram,
					this.recorder);//流程状态控制
			this.controlCaches.put(control.getDiagram().getDiagramName(), control);
		}
		for (TIFlowDiagram diagram : diagrams.values()) {
			injector.injectorDiagram(diagram, this);
		}
	}

	/**
	 * 获取当前流程图集合
	 */
	public String[] getFlows() {
		return this.recorder.getFlows();
	}

	public void persistence(Context context) {
		TiFlowControlPersistDataOperator opertor = ((TiFlowControlPersistDataOperator) this.recorder);
		if (opertor.isNeedToPersistence()) {
			this.persistencer.persistenceData(context.getCacheDir().getAbsolutePath(),
					PERSISTENCE_RECORD_NAME, opertor.needToPersistenceData());
		}
		opertor = ((TiFlowControlPersistDataOperator) this.getCurrentFlowStatusControl());
		if (opertor != null) {
			this.persistencer.persistenceData(context.getCacheDir().getAbsolutePath(),
					PERSISTENCE_CONTEXT_NAME, opertor.needToPersistenceData());
		}
	}

	public void restoreFlowControl(Context context) {
		TiFlowControlPersistDataOperator opertor = ((TiFlowControlPersistDataOperator) this.recorder);
		opertor.restoreControlStatus(this.persistencer.restoreData(context.getCacheDir()
				.getAbsolutePath(), PERSISTENCE_RECORD_NAME, true));
		opertor = ((TiFlowControlPersistDataOperator) this.getCurrentFlowStatusControl());
		if (opertor != null) {
			opertor.restoreControlStatus(this.persistencer.restoreData(context
					.getCacheDir().getAbsolutePath(), PERSISTENCE_CONTEXT_NAME, true));
		}
	}

	public TiFlowStatusControl getFlowStatusControl(String diagramName) {
		return this.controlCaches.get(diagramName);
	}

	public TiFlowStatusControl getCurrentFlowStatusControl() {
		return this.controlCaches.get(recorder.getCurrentFlow());
	}

	public TiFlowForward getFlowForward(String forwardType) {
		return this.forwardCache.get(forwardType);
	}

	public TiFlowNodeDataTransfer getFlowDataTransfer(String transferType) {
		return this.transferCache.get(transferType);
	}

	public void setLoader(TiFlowControlConfigLoader loader) {
		this.loader = loader;
	}

	public void setFactory(TiFlowControlFactory factory) {
		this.factory = factory;
	}

	public TiFlowControlStatusRecord getRecorder() {
		return recorder;
	}

}
