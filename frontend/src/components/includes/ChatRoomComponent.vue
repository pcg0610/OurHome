<script setup>
import { useChatRoomStore } from '@/stores/chatRoomStore.js'
import { useUserStore } from '@/stores/userStore';
import { ref } from 'vue';
import { useAppStore } from "@/stores/appStore";
import { useWebSocketStore } from '@/stores/webSocketStore';

const chatRoomStore = useChatRoomStore()
const webSocketStore = useWebSocketStore()
const userStore = useUserStore()
const appStore = useAppStore()
const newMessage = ref('')

const leaveRoom = () => {
    webSocketStore.setChannel(null)
    webSocketStore.unSubscribe()
    webSocketStore.clearMessages()
    appStore.chatViewTab = appStore.chatViewTabMap.roomListTab
}

const sendMessage = () => {
    if (newMessage.value.trim() === '') return;

    const message = { content: newMessage.value, sender: userStore.user.name, type: 'CHAT' };
    webSocketStore.sendMessage(message);
    newMessage.value = '';
}

</script>

<template>
    <v-container :fluid="true" class="pa-0">
        <v-row class="room-header ma-0">
            <v-col class="d-flex align-center px-1">
                <v-btn class="me-2" density="compact" variant="plain" icon="mdi-arrow-left" @click="leaveRoom()" />
                <h3>{{ chatRoomStore.chatRoom.name }}</h3>
            </v-col>
        </v-row>
        <v-row class="ma-0">
            <v-divider></v-divider>
        </v-row>
        <v-row class="room-body pa-0 ma-0 ">
            <v-col class="d-flex flex-column">
                <v-card class="my-1 fit-content" variant="tonal" v-for="(message, index) in webSocketStore.messages" :key="index" :class="{right : userStore.user.name === message.sender}">
                    <v-card-text class="pa-2">{{ message.content }}</v-card-text>
                </v-card>
            </v-col>
        </v-row>
        <v-row class="ma-0">
            <v-divider></v-divider>
        </v-row>
        <v-row class="room-footer ma-0">
            <v-col class="pa-2 d-flex justify-between">
                <v-text-field hide-details="true" density="compact" append-inner-icon="mdi-send" @click:append-inner="sendMessage" @keyup.enter="sendMessage" v-model="newMessage" variant="outlined"></v-text-field>
            </v-col>
        </v-row>
    </v-container>
</template>

<style scoped>

.right {
    margin-left: auto;
}

.fit-content {
    width: fit-content;
}

.room-body {
    height: 290px;
}

.room-footer {
    height: 100%;
}

</style>