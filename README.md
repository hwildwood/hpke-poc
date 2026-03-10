# HPKE POC

This folder contains a minimal HPKE interoperability POC between:

- `hpke-client`: Expo SDK 55 React Native client using the latest Expo Router, `hpke`, and `react-native-quick-crypto`
- `hpke-be`: Spring Boot 3.5 Java backend using Bouncy Castle HPKE

## Flow

1. The backend exposes a static recipient public key at `GET /hpke/public-key`.
2. The Expo client fetches that key, encrypts `{"key":"secret"}`, and posts the HPKE envelope to `POST /hpke/requests`.
3. The backend decrypts the envelope and logs the plaintext.

## Backend

```bash
cd hpke-be
./mvnw spring-boot:run
```

Endpoints:

- `GET /hpke/public-key`
- `POST /hpke/requests`

## Client

```bash
cd hpke-client
npx expo prebuild
npx pod-install
npm run ios
```

Notes:

- `react-native-quick-crypto` requires a prebuilt Expo dev client, not Expo Go.
- The app entry lives under `src/app`.
- `API_BASE_URL` is hardcoded in `src/config.ts` to `http://127.0.0.1:8080`.
- If you run the app on a physical device, replace that base URL with your machine's LAN IP.

## Verification

Backend tests:

```bash
cd hpke-be
./mvnw test
```

Client checks:

```bash
cd hpke-client
npm run typecheck
npm test
```
