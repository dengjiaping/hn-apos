create table ProductInfo (
    idProductInfo Integer not null PRIMARY KEY autoincrement,
	productId Long,
	merchPartyId Text,
	productType Text,
	skuNo Text,
	name Text,
	namePinyin Text,
	description Text,
	attr Text,
	shopCartFlag Boolean,
	status Text,
	autoRefNo Text,
	prices Text,
	updataTime Text,
	exclusive Boolean,
	price Text
)