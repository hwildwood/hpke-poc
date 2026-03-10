package com.argentlabs.hpke.hpke;

import java.nio.charset.StandardCharsets;

public final class HpkeConstants {

    public static final String KEM = "P-256";
    public static final String KDF = "HKDF-SHA256";
    public static final String AEAD = "AES-128-GCM";
    public static final byte[] INFO = "argentlabs-hpke-poc".getBytes(StandardCharsets.UTF_8);

    private HpkeConstants() {
    }
}
