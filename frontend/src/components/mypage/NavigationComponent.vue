<script setup>

</script>

<template>
    <v-card>
        <v-layout>
        <v-navigation-drawer
            location="left"
            permanent
        >
            <v-divider></v-divider>

            <v-list density="compact" nav>
            <v-list-item prepend-icon="mdi-account" title="내 정보" value="mypage-info" :to="{name: 'mypage-info'}"></v-list-item>
            <v-list-item prepend-icon="mdi-text-box-multiple" title="작성한 게시글" value="mypage-posts" :to="{name: 'mypage-posts'}"></v-list-item>
            </v-list>
        </v-navigation-drawer>
        <v-main style="height: 889px">
            <RouterView />
        </v-main>
        </v-layout>
    </v-card>
</template>

<style scoped>

</style>