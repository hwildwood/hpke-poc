package com.argentlabs.hpke.hpke;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

public record HpkeKeyMaterial(
    AsymmetricCipherKeyPair recipientKeyPair,
    byte[] rawPublicKey
) {
}
