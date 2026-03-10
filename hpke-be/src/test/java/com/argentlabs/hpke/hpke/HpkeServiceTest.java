package com.argentlabs.hpke.hpke;

import com.argentlabs.hpke.web.HpkeCiphertextRequest;
import org.bouncycastle.crypto.hpke.HPKE;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HpkeServiceTest {

    private static final byte[] EMPTY = new byte[0];
    private static final HPKE HPKE_SUITE = new HPKE(
        HPKE.mode_base,
        HPKE.kem_P256_SHA256,
        HPKE.kdf_HKDF_SHA256,
        HPKE.aead_AES_GCM128
    );

    @Autowired
    private HpkeKeyLoader keyLoader;

    @Autowired
    private HpkeService hpkeService;

    @Test
    void decryptsEnvelopeProducedWithMatchingSuite() throws Exception {
        byte[][] encrypted = HPKE_SUITE.seal(
            keyLoader.getKeyMaterial().recipientKeyPair().getPublic(),
            HpkeConstants.INFO,
            EMPTY,
            "{\"key\":\"secret\"}".getBytes(StandardCharsets.UTF_8),
            null,
            null,
            null
        );

        String plaintext = hpkeService.decryptPayload(
            new HpkeCiphertextRequest(
                Base64.getEncoder().encodeToString(encrypted[1]),
                Base64.getEncoder().encodeToString(encrypted[0])
            )
        );

        assertThat(plaintext).isEqualTo("{\"key\":\"secret\"}");
    }
}
