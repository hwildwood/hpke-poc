import { Stack } from 'expo-router';
import { StatusBar } from 'expo-status-bar';

export default function RootLayout() {
  return (
    <>
      <StatusBar style="auto" />
      <Stack
        screenOptions={{
          headerStyle: {
            backgroundColor: '#08111c',
          },
          headerTintColor: '#f3f7fb',
          headerShadowVisible: false,
          contentStyle: {
            backgroundColor: '#d7e7f2',
          },
        }}
      >
        <Stack.Screen name="index" options={{ title: 'HPKE POC' }} />
      </Stack>
    </>
  );
}
