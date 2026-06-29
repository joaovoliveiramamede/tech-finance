import { Platform } from 'react-native';

const defaultHost = Platform.select({
  android: 'http://10.0.2.2:8080',
  ios: 'http://localhost:8080',
  default: 'http://localhost:8080',
});

export const environment = {
  apiBaseUrl: process.env.EXPO_PUBLIC_API_BASE_URL ?? `${defaultHost}/api/v1`,
};
