package com.argentlabs.hpke.web;

import com.argentlabs.hpke.hpke.HpkeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/hpke", produces = MediaType.APPLICATION_JSON_VALUE)
public class HpkeController {

    private final HpkeService hpkeService;

    public HpkeController(HpkeService hpkeService) {
        this.hpkeService = hpkeService;
    }

    @GetMapping("/public-key")
    public HpkePublicKeyResponse getPublicKey() {
        return hpkeService.getPublicKey();
    }

    @PostMapping(path = "/requests", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HpkeStatusResponse receiveEncryptedRequest(@RequestBody HpkeCiphertextRequest request) {
        hpkeService.decryptPayload(request);
        return new HpkeStatusResponse("ok");
    }
}
