package com.argentlabs.hpke.hpke;

import com.argentlabs.hpke.web.HpkeCiphertextRequest;
import com.argentlabs.hpke.web.HpkePublicKeyResponse;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.hpke.HPKE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class HpkeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HpkeService.class);
    private static final byte[] EMPTY = new byte[0];
    private static final HPKE HPKE_SUITE = new HPKE(
        HPKE.mode_base,
        HPKE.kem_P256_SHA256,
        HPKE.kdf_HKDF_SHA256,
        HPKE.aead_AES_GCM128
    );

    private final HpkeKeyLoader keyLoader;

    public HpkeService(HpkeKeyLoader keyLoader) {
        this.keyLoader = keyLoader;
    }

    public HpkePublicKeyResponse getPublicKey() {
        return new HpkePublicKeyResponse(
            HpkeConstants.KEM,
            HpkeConstants.KDF,
            HpkeConstants.AEAD,
            Base64.getEncoder().encodeToString(keyLoader.getKeyMaterial().rawPublicKey())
        );
    }

    public String decryptPayload(HpkeCiphertextRequest request) {
        long startedAt = System.nanoTime();
        try {
            byte[] plaintext = HPKE_SUITE.open(
                Base64.getDecoder().decode(request.encBase64()),
                keyLoader.getKeyMaterial().recipientKeyPair(),
                HpkeConstants.INFO,
                EMPTY,
                Base64.getDecoder().decode(request.ciphertextBase64()),
                null,
                null,
                null
            );
            String payload = new String(plaintext, StandardCharsets.UTF_8);
            double elapsedMs = (System.nanoTime() - startedAt) / 1_000_000.0;
            LOGGER.info("Decrypted HPKE payload in {} ms: {}", String.format("%.3f", elapsedMs), payload);
            return payload;
        } catch (IllegalArgumentException | InvalidCipherTextException exception) {
            throw new HpkeException("Unable to decrypt HPKE request", exception);
        }
    }
}
