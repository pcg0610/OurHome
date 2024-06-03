<script setup>
import { useChatRoomStore } from '@/stores/chatRoomStore';
import ChatRoomListItemComponent from '@/components/includes/ChatRoomListItemComponent.vue'

const chatRoomStore = useChatRoomStore()

</script>

<template>
    <v-list class="py-0">
        <v-list-item class="pa-0" v-for="chatRoom in chatRoomStore.chatRoomList" :key="chatRoom.id">
            <ChatRoomListItemComponent :chatRoom="chatRoom" />            
        </v-list-item>
    </v-list>
</template>

<style scoped>
</style>