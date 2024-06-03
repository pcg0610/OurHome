<script setup>
import DetailPostListItemComponent from '@/components/main/detail/DetailPostListItemComponent.vue'
import { useBoardStore } from '@/stores/boardStore';

const boardStore = useBoardStore()

</script>
<template class="mt-1">
  <v-infinite-scroll width="100%" height="360px" :items="boardStore.articleList" :onLoad="({done}) => {done('empty')}">
    <div v-for="post in boardStore.articleList" :key="post.id">
      <DetailPostListItemComponent :item="post"/>
    </div>
    
  </v-infinite-scroll>
</template>
<style scoped>
.short-text {
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
</style>