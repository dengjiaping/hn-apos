create table TxnConfirm  (
    id Integer not null PRIMARY KEY autoincrement,
	txnId Text,
	updateTime Text,
	retryCount Text,
	createTime Text
)

	