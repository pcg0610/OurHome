<script setup>
import { useAppStore } from '@/stores/appStore';
import { useUserStore } from '@/stores/userStore';
import { ref } from 'vue';
import ChatRoomListComponent from '@/components/includes/ChatRoomListComponent.vue'
import ChatRoomComponent from '@/components/includes/ChatRoomComponent.vue'

const appStore = useAppStore()
const userStore = useUserStore()

const chatShow = ref(false);

const chatBtnClickHandler = () => {
    chatShow.value = !chatShow.value
}

</script>

<template>
  <div>
    <v-card 
    id="chat-view"
    class="chat-card border-md" 
    variant="outlined"
    rounded="lg" 
    v-show="chatShow">
      <v-tabs-window class="chat-tabs-window" v-model="appStore.chatViewTab">
        <v-tabs-window-item class="chat-tabs-window" :value="appStore.chatViewTabMap.roomListTab">
          <v-container class="mp-0">
            <v-row class="mp-0 chat-header mb-1">
              <v-col class="mp-0">
                <h3 class="pa-2 align-self-center">채팅</h3>
              </v-col>
            </v-row>
            <v-divider></v-divider>
            <v-row class="mp-0 chat-body">
              <v-col class="mp-0 d-flex align-center justify-center" v-if="!userStore.user.id">
                <div class="d-inline">로그인이 필요합니다!</div>
              </v-col>
              <v-col class="mp-0" v-if="userStore.user.id">
                <ChatRoomListComponent class="room-list-body"/>
              </v-col>
            </v-row>
          </v-container>

        </v-tabs-window-item>
        <v-tabs-window-item :value="appStore.chatViewTabMap.roomTab">
            <ChatRoomComponent />
        </v-tabs-window-item>
      </v-tabs-window>
    </v-card>
    
    <v-btn @click="chatBtnClickHandler" class="chat-btn" icon="mdi-chat"></v-btn>
  </div>
</template>

<style scoped>

.chat-card {
  z-index: 1;
  position: fixed;
  right: 60px;
  bottom: 120px;
  width: 300px; /* Fixed width */
  height: 400px; /* Fixed height */
  background-color: #ffffff;
  display: flex;
  flex-direction: column;
}

.chat-header {
  height: 36px;
}

.chat-body {
  height: 356px;
}

.vertical-center {
  vertical-align: middle;
}

.chat-btn {
  z-index: 1;
  position: fixed;
  right: 60px;
  bottom: 60px;
}

.mp-0{
  padding: 0;
  margin: 0;
}

</style>