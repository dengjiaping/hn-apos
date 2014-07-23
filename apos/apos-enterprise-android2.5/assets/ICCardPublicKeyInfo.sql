 create table ICCardPublicKeyInfo (
    idICCardPublicKeyInfo Integer not null PRIMARY KEY autoincrement,
	expirationDate Text,
	exponent Text,
	hashAlgorithmIndicator Text,
	indexText Text,
    modulus Text,
	signatureAlgorithmIndicator Text,
	rid Text,
	sha1CheckSum Text
)