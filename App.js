
import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

export default function App() {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>🛡️ Verum Omnis</Text>
      <Text style={styles.subtitle}>AI That Cannot Lie</Text>
      <Text style={styles.message}>
        This application is sealed by a sovereign law. It cannot be overridden, misled, or coerced.
        It operates without bias, without surveillance, and without compromise.
        Truth is not a feature — it is the foundation.
      </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1, justifyContent: 'center', alignItems: 'center', backgroundColor: '#000'
  },
  title: {
    fontSize: 24, color: '#00e676', fontWeight: 'bold'
  },
  subtitle: {
    fontSize: 16, color: '#ccc', marginVertical: 10
  },
  message: {
    fontSize: 14, color: '#aaa', padding: 20, textAlign: 'center'
  }
});
