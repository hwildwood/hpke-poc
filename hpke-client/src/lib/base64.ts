import { fromByteArray, toByteArray } from 'base64-js';

export function bytesToBase64(bytes: Uint8Array): string {
  return fromByteArray(bytes);
}

export function base64ToBytes(value: string): Uint8Array {
  return toByteArray(value);
}
