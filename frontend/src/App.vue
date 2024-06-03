<script setup>
import HeaderView from '@/views/includes/HeaderView.vue'
import ChatComponent from '@/components/includes/ChatComponent.vue'

import { RouterView } from 'vue-router'

</script>

<template>
  <v-layout class="full-screen">
    <HeaderView />

    <v-main class="h-100">
        <v-container fluid class="pa-0 h-100">
            <RouterView />
        </v-container>
        
        <ChatComponent />
    </v-main>
  </v-layout>
  
</template>

<style scoped>
.full-screen {
  height: 100vh;
  min-width: 1920px;
}
</style>