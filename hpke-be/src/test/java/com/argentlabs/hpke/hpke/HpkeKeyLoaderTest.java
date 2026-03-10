package com.argentlabs.hpke.hpke;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HpkeKeyLoaderTest {

    @Autowired
    private HpkeKeyLoader keyLoader;

    @Test
    void loadsStaticPemKeyMaterial() {
        HpkeKeyMaterial keyMaterial = keyLoader.getKeyMaterial();

        assertThat(keyMaterial.recipientKeyPair()).isNotNull();
        assertThat(keyMaterial.rawPublicKey()).hasSize(65);
    }
}
