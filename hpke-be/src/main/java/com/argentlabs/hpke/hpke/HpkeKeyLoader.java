package com.argentlabs.hpke.hpke;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.crypto.hpke.HPKE;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class HpkeKeyLoader {

    private static final HPKE HPKE_SUITE = new HPKE(
        HPKE.mode_base,
        HPKE.kem_P256_SHA256,
        HPKE.kdf_HKDF_SHA256,
        HPKE.aead_AES_GCM128
    );

    private final HpkeKeyMaterial keyMaterial;

    public HpkeKeyLoader(
        @Value("classpath:keys/recipient-private-key.pem") Resource privateKeyResource,
        @Value("classpath:keys/recipient-public-key.pem") Resource publicKeyResource
    ) {
        try {
            AsymmetricKeyParameter privateKey = PrivateKeyFactory.createKey(readPemContent(privateKeyResource));
            AsymmetricKeyParameter publicKey = PublicKeyFactory.createKey(readPemContent(publicKeyResource));
            this.keyMaterial = new HpkeKeyMaterial(
                new AsymmetricCipherKeyPair(publicKey, privateKey),
                HPKE_SUITE.serializePublicKey(publicKey)
            );
        } catch (IOException exception) {
            throw new HpkeException("Unable to load HPKE key material", exception);
        }
    }

    public HpkeKeyMaterial getKeyMaterial() {
        return keyMaterial;
    }

    private byte[] readPemContent(Resource resource) throws IOException {
        try (PemReader reader = new PemReader(new InputStreamReader(resource.getInputStream()))) {
            PemObject pemObject = reader.readPemObject();
            if (pemObject == null) {
                throw new IOException("PEM content missing for " + resource.getFilename());
            }
            return pemObject.getContent();
        }
    }
}
