<script setup>
    import PostComponent from '@/components/mypage/PostComponent.vue'
    import { useUserStore} from '@/stores/userStore.js'
    import { useBoardStore } from '@/stores/boardStore';

    const userStore = useUserStore()
    const boardStore = useBoardStore();

    // 로그인한 사용자가 작성한 게시글 목록 불러오기
    boardStore.getArticleListById(userStore.user.id);
</script>

<template>
    <v-container :fluid="true">
        <v-row>
            <v-col class="pb-0">
                <h2>작성한 게시글</h2>
            </v-col>
        </v-row>
        <v-row>
            <v-col class="pt-0">
                <v-infinite-scroll height="100%" maxHeight="740px" :items="boardStore.myArticleList" :onLoad="({done}) => {done('empty')}">
                    <PostComponent class="mt-3 w-50" v-for="post in boardStore.myArticleList"
                        :key="post.id" :post="post"/>
                </v-infinite-scroll>
            </v-col>
        </v-row>
  
    </v-container>
</template>

<style scoped>

</style>