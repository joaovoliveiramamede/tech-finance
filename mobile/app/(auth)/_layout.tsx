import { Stack, useRouter, useSegments } from 'expo-router';
import { useEffect } from 'react';

import { LoadingScreen } from '@/components/ui/loading-screen';
import { useAuth } from '@/contexts/auth-context';

export default function AuthLayout() {
  const { isAuthenticated, isLoading } = useAuth();
  const router = useRouter();
  const segments = useSegments();

  useEffect(() => {
    if (isLoading) {
      return;
    }

    const current = segments[segments.length - 1];

    if (!isAuthenticated) {
      if (current === 'criar-conta') {
        router.replace('/(auth)/login');
      }
      return;
    }

    if (current === 'register') {
      router.replace('/(auth)/criar-conta');
      return;
    }

    if (current !== 'criar-conta') {
      router.replace('/(app)');
    }
  }, [isAuthenticated, isLoading, router, segments]);

  if (isLoading) {
    return <LoadingScreen />;
  }

  if (isAuthenticated && segments[segments.length - 1] !== 'criar-conta') {
    return <LoadingScreen />;
  }

  return (
    <Stack screenOptions={{ headerShown: false }}>
      <Stack.Screen name="login" />
      <Stack.Screen name="register" />
      <Stack.Screen name="criar-conta" />
    </Stack>
  );
}
