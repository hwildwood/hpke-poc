import { describe, expect, it } from 'vitest';

import { base64ToBytes, bytesToBase64 } from './base64';

describe('base64 helpers', () => {
  it('round-trips arbitrary payload bytes', () => {
    const input = new Uint8Array([0, 1, 2, 15, 16, 32, 64, 128, 255]);
    const encoded = bytesToBase64(input);

    expect(base64ToBytes(encoded)).toEqual(input);
  });
});
