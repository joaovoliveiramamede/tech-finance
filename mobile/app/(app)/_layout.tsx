import { Tabs, useRouter } from 'expo-router';
import { useEffect, useState } from 'react';
import { Pressable, StyleSheet, Text, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

import { HapticTab } from '@/components/haptic-tab';
import { LoadingScreen } from '@/components/ui/loading-screen';
import { IconSymbol } from '@/components/ui/icon-symbol';
import { AppColors, Spacing } from '@/constants/app-theme';
import { useAuth } from '@/contexts/auth-context';
import { accountApiService } from '@/core/services/account-api.service';

export default function AppLayout() {
  const { isAuthenticated, isLoading, name, username, logout } = useAuth();
  const router = useRouter();
  const [checkingOnboarding, setCheckingOnboarding] = useState(true);

  useEffect(() => {
    if (isLoading) {
      return;
    }

    if (!isAuthenticated) {
      router.replace('/(auth)/login');
      return;
    }

    accountApiService
      .findAll()
      .then((accounts) => {
        if (accounts.length === 0) {
          router.replace('/(auth)/criar-conta');
        }
      })
      .catch(() => {
        router.replace('/(auth)/criar-conta');
      })
      .finally(() => setCheckingOnboarding(false));
  }, [isAuthenticated, isLoading, router]);

  if (isLoading || checkingOnboarding || !isAuthenticated) {
    return <LoadingScreen />;
  }

  const displayName = name ?? username ?? 'Usuário';

  async function handleLogout() {
    await logout();
    router.replace('/(auth)/login');
  }

  return (
    <View style={styles.container}>
      <SafeAreaView edges={['top']} style={styles.header}>
        <View>
          <Text style={styles.brand}>TechFinance</Text>
          <Text style={styles.user}>{displayName}</Text>
        </View>
        <Pressable onPress={handleLogout} hitSlop={8}>
          <Text style={styles.logout}>Sair</Text>
        </Pressable>
      </SafeAreaView>

      <Tabs
        screenOptions={{
          headerShown: false,
          tabBarActiveTintColor: AppColors.accent,
          tabBarInactiveTintColor: AppColors.textMuted,
          tabBarStyle: styles.tabBar,
          tabBarButton: HapticTab,
        }}>
        <Tabs.Screen
          name="index"
          options={{
            title: 'Home',
            tabBarIcon: ({ color }) => <IconSymbol size={24} name="house.fill" color={color} />,
          }}
        />
        <Tabs.Screen
          name="contas"
          options={{
            title: 'Contas',
            tabBarIcon: ({ color }) => <IconSymbol size={24} name="creditcard.fill" color={color} />,
          }}
        />
        <Tabs.Screen
          name="transacoes"
          options={{
            title: 'Transações',
            tabBarIcon: ({ color }) => (
              <IconSymbol size={24} name="arrow.left.arrow.right" color={color} />
            ),
          }}
        />
        <Tabs.Screen
          name="categorias"
          options={{
            title: 'Categorias',
            tabBarIcon: ({ color }) => <IconSymbol size={24} name="tag.fill" color={color} />,
          }}
        />
      </Tabs>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: AppColors.background,
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: Spacing.lg,
    paddingBottom: Spacing.sm,
    backgroundColor: AppColors.sidebar,
  },
  brand: {
    fontSize: 18,
    fontWeight: '700',
    color: AppColors.white,
  },
  user: {
    fontSize: 12,
    color: AppColors.textSidebarMuted,
    marginTop: 2,
  },
  logout: {
    fontSize: 14,
    color: AppColors.textSidebar,
    fontWeight: '600',
  },
  tabBar: {
    backgroundColor: AppColors.white,
    borderTopColor: AppColors.cardBorder,
  },
});
