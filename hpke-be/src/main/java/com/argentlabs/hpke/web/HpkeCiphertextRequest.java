package com.argentlabs.hpke.web;

public record HpkeCiphertextRequest(
    String encBase64,
    String ciphertextBase64
) {
}
