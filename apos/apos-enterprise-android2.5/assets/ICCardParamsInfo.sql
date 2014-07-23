 create table ICCardParamsInfo (
    idICCardParamsInfo Integer not null PRIMARY KEY autoincrement,
	aid Text,
	ais Text,
	appViersionId Text,
	tacDefault Text,
    tacOnline Text,
	tacReject Text,
	terminalFloorLimit Text,
	thresholdBias Text,
	maxTargetPercentageBias Text,
	targetPercentage Text,
    defaultDool Text,
	terminalOnlinePinBlance Text,
	terminalEcashTxnQuota Text ,
	contactlessReaderOfflineMinQuoata Text,
    conteactLessReaderTxnQuota Text,
   	readerCvmLimit Text
)