<script setup>
  import { useAppStore } from '@/stores/appStore';
import { useChatRoomStore } from '@/stores/chatRoomStore';
  import { useUserStore } from '@/stores/userStore';
  import { instance } from '@/util/http-commons';
  import { ref } from 'vue';

  const props = defineProps({
      item: Object
  })

  const appStore = useAppStore();
  const userStore = useUserStore();
  const name = ref('');
  const chatRoomStore = useChatRoomStore();

  if (userStore.user.id !== '') {
    await instance.get('/user/name', { params: {userId: props.item.userId}})
    .then((response) => {
      console.log(response.data);
      name.value = response.data;
    })
    .catch((error) => {
      console.log(error);
    })
  }

  userStore.getItemList(props.item.userId);

  const enterChatRoom = function(postId, authorId, userId) {
    console.log(props.item);
    chatRoomStore.enterChatRoom(props.item.id,props.item.userId,props.item.id)
    
  }

</script>

<template>
    <v-card class="w-100 mb-1 border">
      <v-card-title class="font-weight-bold pb-1 short-text">
        {{ props.item.title }}
      </v-card-title>
      <div style="display:flex; align-items: center;">
        <v-card-text class="py-0 short-text">
          작성자 {{ name }}
        </v-card-text>
        <v-btn class="me-4" variant="tonal" color="primary" @click="enterChatRoom">
          채팅 참여하기
        </v-btn>
      </div>
      <v-card-text class="pt-0 short-text">
        {{ appStore.changeBirth(props.item.registeredDate) }}
      </v-card-text>

      <div class="ms-3">
          <v-chip
            class="me-3 mb-2"
            size="small"
            color="primary"
            variant="elevated" v-for="item in userStore.items"> {{ item }}</v-chip>
      </div>
    </v-card>
</template>

<style scoped>

</style>