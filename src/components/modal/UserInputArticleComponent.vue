<template>
    <!-- 모달 창 바깥 쪽을 클릭하는 경우 모달창 close -->
    <v-dialog max-width="448" @click:outside="close">
      <v-card>
        <v-card-title class="text-center mt-5">
          <v-icon icon="mdi-pencil"></v-icon>
          <span class="font-weight-black">게시글 등록하기</span>
        </v-card-title>

        <v-card-text class="mt-10">
          <v-text-field label="제목" variant="outlined" v-model="title" @keyup="checkTitle" hide-details="auto"> </v-text-field>

          <div v-if="checkTitleMessage !== ''" class="mt-2">
            <p class="text-red">{{ checkTitleMessage }}</p>
          </div>

          <div style="min-height: 20px" v-else></div>

          <v-textarea class="mt-2" hide-details="auto" label="내용 작성" variant="outlined" v-model="content" @keyup="checkContent"></v-textarea>
          <div v-if="checkContentMessage !== ''" class="mt-2">
            <p class="text-red">내용을 입력해 주세요</p>
          </div>

          <div style="min-height: 20px" v-else></div>
        </v-card-text>

        <v-card-actions class="justify-center">
          <v-btn :disabled="checkValidation" color="primary" variant="flat" class="me-3 mb-5" @click="registArticle"> 등록하기 </v-btn>
          <v-btn color="red" variant="flat" class="ms-3 mb-5" @click="close"> 닫기 </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
</template>

<script setup>
  import { ref, computed } from 'vue';
  import  clone from 'lodash';
  import { useAppStore } from '@/stores/appStore';
  import { useBoardStore } from '@/stores/boardStore';
  import { useUserStore } from '@/stores/userStore';
  import { useHomeStore } from '@/stores/homeStore';
  import { useChatRoomStore } from '@/stores/chatRoomStore';

  const appStore = useAppStore();
  const boardStore = useBoardStore();
  const userStore = useUserStore();
  const homeStore = useHomeStore();
  const chatRoomStore = useChatRoomStore();

  const title = ref('');
  const content = ref('');

  const article = ref({
    userId : userStore.user.id,
    homeId : homeStore.home.id,
    title : '',
    content: '',
    registeredDate: new Date()
  })

  // 외부를 클릭 or 닫기 버튼 클릭 시 내용 초기화
  function close() {
    clear();
    appStore.showArticle = false;
  }

  // 글을 등록하는 경우
  async function registArticle() {
    article.value.userId = userStore.user.id;
    article.value.homeId = homeStore.home.id;
    article.value.title = title.value;
    article.value.content = content.value;

    const copyArticle = clone.cloneDeep(article);

    await boardStore.registArticle(copyArticle.value);

    boardStore.articleList.push(copyArticle.value);
    console.log(copyArticle.value);

    chatRoomStore.getPostId(copyArticle.value.userId, article.value.homeId, homeStore.home.name);
    //chatRoomStore.createChatRoom(chatRoomStore.postId, copyArticle.value.userId, homeStore.home.name);

    //chatRoomStore.createChatRoom(copyArticle.value.id, copyArticle.value.userId, homeStore.home.name);
    clear();
    appStore.showArticle = false;
  }

  // 내용 초기화
  function clear() {
    title.value = '';
    content.value ='';
  }

  const checkTitleMessage = ref('');
  const checkContentMessage = ref('');

  const checkTitle = function() {
    if (title.value === '') {
      checkTitleMessage.value = '제목을 입력해 주세요';
    } else {
      checkTitleMessage.value = '';
    }
  }

  const checkContent = function() {
    if (content.value === '') {
      checkContentMessage.value = '내용을 입력해 주세요';
    } else {
      checkContentMessage.value = '';
    }
  }
  
  const checkValidation = computed(() => {
    return (title.value === '' || content.value === '')
  })

</script>

<style scoped>

</style>