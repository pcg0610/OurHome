<script setup>
import { useAppStore } from '@/stores/appStore';
import { useChatRoomStore } from '@/stores/chatRoomStore';
import { useWebSocketStore } from '@/stores/webSocketStore';
import { computed } from 'vue';

const appStore = useAppStore()
const chatRoomStore = useChatRoomStore()
const webSocketStore = useWebSocketStore()

const props = defineProps(
    {chatRoom: Object}
)

const enterRoom = (chatRoomId) => {
    chatRoomStore.chatRoom = props.chatRoom
    webSocketStore.setChannel(chatRoomId)
    webSocketStore.subscribe()
    appStore.chatViewTab = appStore.chatViewTabMap.roomTab;
}

const date = computed(() => {
    return new Date(props.chatRoom.registeredDate)
})

</script>

<template>
    <v-hover v-slot="{isHovering, props}">
        <v-card v-bind="props" class="pa-1 px-0" :class="{hover: isHovering}" @click="enterRoom(chatRoom.id)">
            <v-card-title class="mp-0 ps-2 py-0 text-h6">{{ chatRoom.name }}</v-card-title>
            <v-card-text class="ps-2 pb-0">{{ date.getFullYear() }}.{{ date.getMonth() < 10 ? `0${date.getMonth()}` : date.getMonth() }}.{{ date.getDate() < 10 ? `0${date.getDate()}` : date.getDate() }}</v-card-text>
        </v-card>
    </v-hover>
</template>

<style scoped>
</style>