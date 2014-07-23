create table OrderInfo (
    idOrder Integer not null PRIMARY KEY autoincrement,
    orderRecordId Long not null,
	orderId Text not null,
	orderAmt Double not null,
	synDate Text not null,
	createDate Text not null,
	expiredTime,
	orderStatus   not null,
	orderAttrs1 BLOB,
	orderAttrs2 BLOB,
	userName Text,
	partyId Text,
	txnId Text
)