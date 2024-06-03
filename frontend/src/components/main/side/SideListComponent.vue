<script setup>
import SideListItemComponent from '@/components/main/side/SideListItemComponent.vue';
import { useAppStore } from '@/stores/appStore';
import { useHomeStore } from '@/stores/homeStore';

const appStore = useAppStore()
const homeStore = useHomeStore()

const props = defineProps({
  tab: String
})

const load = ({ done }) => {
  
  let res = undefined
  let empty = false

  if (props.tab === appStore.tabs.search) {
    if (appStore.itemList.length + 10 >= homeStore.homeList.length) {
      empty = true;
    }
    res = homeStore.homeList.slice(appStore.itemList.length, Math.min(appStore.itemList.length + 10, homeStore.homeList.length))

  } else if (props.tab === appStore.tabs.favorite) {
    if (appStore.itemList.length + 10 >= homeStore.favoriteList.length) {
      empty = true;
    }
    res = homeStore.favoriteList.slice(appStore.itemList.length, Math.min(appStore.itemList.length + 10, homeStore.favoriteList.length))

  }

  appStore.itemList.push(...res)

  if (empty) {
    done('empty')
  } else {
    done('ok')
  }
}

</script>

<template>
  <v-container>
    <v-row>
      <v-infinite-scroll v-if="tab === appStore.tabs.search" height="100%" maxHeight="730px" :items="appStore.itemList" :onLoad="load" width="100%">
        <template v-for="home in appStore.itemList" :key="home.id">
          <v-col cols="12">
            <SideListItemComponent :item="home"/>
          </v-col>
        </template>
      </v-infinite-scroll>
      <v-infinite-scroll v-if="tab === appStore.tabs.favorite" height="100%" maxHeight="730px" :items="homeStore.favoriteList" :onLoad="load" width="100%">
        <template v-for="home in homeStore.favoriteList" :key="home.id">
          <v-col cols="12">
            <SideListItemComponent :item="home"/>
          </v-col>
        </template>
      </v-infinite-scroll>
    </v-row>
  </v-container>
</template>

<style scoped>

</style>