 create table ICCardInfo (
    idICCardInfo Integer not null PRIMARY KEY autoincrement,
	appCryptogram Text,
	cryptogramInfoData Text,
	issuerAppData Text,
	unpredictableNumber Text,
	appTxnCounter Text,
	termVerificationResult Text,
	txnDate Text,
	txnType Text,
	txnAmtOrAuthAmt Text,
	txnCurrCode Text,
	appInterProfile Text,
	termCountryCode Text,
	amtOther Text,
	termCapabilities Text,
	eCashIssuerAuthCode Text,
	holerVeriMethodResult Text,
	termType Text,
	interfaceDeviceSeNo Text,
	dedicatedFileName Text,
	termAppVerNO Text,
	txnSequenceCounter Text,
	issuerAuthData Text,
	issuerScriptTem1 Text,
	issuerScriptTem2 Text,
	issuerScriptResult Text,
	cardProductID Text,
	idTxn Integer,
	cardSerialNumber Text

	
)