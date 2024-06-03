<script setup>
import SearchBarComponent from '@/components/main/side/SideSearchBarComponent.vue';
import SideListComponent from '@/components/main/side/SideListComponent.vue'
import { useAppStore } from '@/stores/appStore';

const appStore = useAppStore()

</script>

<template>
    <v-container>
      <v-row>
        <SearchBarComponent />
      </v-row>
    </v-container>

    <v-tabs
      v-model="appStore.tab"
      align-tabs="start"
      color="deep-purple-accent-4"
    >
      
      <v-tab :value="appStore.tabs.search">검색 결과</v-tab>
      <v-tab :value="appStore.tabs.favorite">즐겨찾기</v-tab>
    </v-tabs>

    <v-tabs-window v-model="appStore.tab">
      <v-tabs-window-item
        v-for="tab in appStore.tabs"
        :key="tab"
        :value="tab"
      >
      
      <SideListComponent :tab="tab"/>

      </v-tabs-window-item>
    </v-tabs-window>
</template>

<style scoped>
</style>