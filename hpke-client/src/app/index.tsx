import { useState } from 'react';
import { ActivityIndicator, Pressable, StyleSheet, Text, View } from 'react-native';

import { sendHpkeRequest } from '../lib/hpke';

export default function IndexScreen() {
  const [status, setStatus] = useState<'idle' | 'sending' | 'success' | 'error'>('idle');
  const [detail, setDetail] = useState('Ready to encrypt and send {"key":"secret"}.');

  const onPress = async () => {
    setStatus('sending');
    setDetail('Encrypting with the backend public key...');

    try {
      const result = await sendHpkeRequest();
      setStatus('success');
      setDetail(`Request sent. Backend responded with "${result.status}".`);
    } catch (error) {
      setStatus('error');
      setDetail(error instanceof Error ? error.message : 'Failed to send HPKE request.');
    }
  };

  return (
    <View style={styles.screen}>
      <View style={styles.card}>
        <Text style={styles.eyebrow}>HPKE POC</Text>
        <Text style={styles.title}>Expo client to Spring Boot receiver</Text>
        <Text style={styles.description}>
          Encrypts a fixed JSON payload with HPKE P-256, HKDF-SHA256, AES-128-GCM.
        </Text>

        <Pressable onPress={onPress} style={[styles.button, status === 'sending' && styles.buttonDisabled]}>
          {status === 'sending' ? (
            <ActivityIndicator color="#08111c" />
          ) : (
            <Text style={styles.buttonText}>Send HPKE Request</Text>
          )}
        </Pressable>

        <View style={styles.statusRow}>
          <Text style={styles.statusLabel}>Status</Text>
          <Text style={styles.statusValue}>{status}</Text>
        </View>
        <Text style={styles.detail}>{detail}</Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  screen: {
    flex: 1,
    backgroundColor: '#d7e7f2',
    alignItems: 'center',
    justifyContent: 'center',
    padding: 24,
  },
  card: {
    width: '100%',
    maxWidth: 420,
    borderRadius: 28,
    backgroundColor: '#08111c',
    padding: 24,
    gap: 16,
    shadowColor: '#08111c',
    shadowOpacity: 0.18,
    shadowRadius: 18,
    shadowOffset: { width: 0, height: 10 },
    elevation: 6,
  },
  eyebrow: {
    color: '#85c0ff',
    fontSize: 12,
    fontWeight: '700',
    letterSpacing: 1.2,
    textTransform: 'uppercase',
  },
  title: {
    color: '#f3f7fb',
    fontSize: 28,
    fontWeight: '700',
    lineHeight: 34,
  },
  description: {
    color: '#c1d1df',
    fontSize: 15,
    lineHeight: 22,
  },
  button: {
    alignItems: 'center',
    borderRadius: 16,
    backgroundColor: '#8bd3ff',
    justifyContent: 'center',
    minHeight: 56,
    paddingHorizontal: 18,
  },
  buttonDisabled: {
    opacity: 0.75,
  },
  buttonText: {
    color: '#08111c',
    fontSize: 16,
    fontWeight: '700',
  },
  statusRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginTop: 4,
  },
  statusLabel: {
    color: '#8ca3b7',
    fontSize: 13,
    fontWeight: '600',
    textTransform: 'uppercase',
  },
  statusValue: {
    color: '#f3f7fb',
    fontSize: 13,
    fontWeight: '700',
    textTransform: 'uppercase',
  },
  detail: {
    color: '#dce7f1',
    fontSize: 14,
    lineHeight: 20,
  },
});
