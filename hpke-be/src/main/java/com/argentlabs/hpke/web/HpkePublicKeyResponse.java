package com.argentlabs.hpke.web;

public record HpkePublicKeyResponse(
    String kem,
    String kdf,
    String aead,
    String publicKeyBase64
) {
}
