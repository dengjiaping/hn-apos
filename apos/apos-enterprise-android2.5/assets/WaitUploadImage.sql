create table WaitUploadImage (
    id Integer not null PRIMARY KEY autoincrement,
	createDate Text,
	filePath Text,
	itemType Text,
    itemId Text,
    termTraceNo Text not null,
    termTxnTime Text not null,
    times Integer,
    readyUpload Boolean
)

	