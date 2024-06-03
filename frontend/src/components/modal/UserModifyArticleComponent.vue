<template>
    <!-- 모달 창 바깥 쪽을 클릭하는 경우 모달창 close -->
    <v-dialog max-width="448" @click:outside="close">
      <v-card>
        <v-card-title class="text-center mt-5">
          <v-icon icon="mdi-pencil"></v-icon>
          <span class="font-weight-black">게시글 수정하기</span>
        </v-card-title>

        <v-card-text class="mt-10">
          <v-text-field hide-details="auto" label="제목" variant="outlined" v-model="title" @keyup="checkTitle"></v-text-field>

          <div v-if="checkTitleMessage !== ''" class="mt-2">
            <p class="text-red">{{ checkTitleMessage }}</p>
          </div>

          <div style="min-height: 20px" v-else></div>

          <v-textarea hide-details="auto" class="mt-2" label="내용 작성" variant="outlined" v-model="content" @keyup="checkContent"></v-textarea>
          <div v-if="checkContentMessage !== ''" class="mt-2">
            <p class="text-red">내용을 입력해 주세요</p>
          </div>

          <div style="min-height: 20px" v-else></div>
        </v-card-text>

        <!-- 게시글 수정 기능 추가 -->
        <v-card-actions class="justify-center">
          <v-btn color="primary" :disabled = checkValidation variant="flat" class="me-3 mb-5" @click="updateArticle"> 등록하기 </v-btn>
          <v-btn color="red" variant="flat" class="ms-3 mb-5" @click="close"> 닫기 </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
</template>

<script setup>
  import { useAppStore } from '@/stores/appStore';
  import { useBoardStore } from '@/stores/boardStore';
  import { ref, computed } from 'vue';

  const boardStore = useBoardStore();
  const appStore = useAppStore();

  const props = defineProps({
    post:Object,
  })

  // 외부를 클릭 or 닫기 버튼 클릭 시 내용 초기화
  function close() {
    appStore.showModifyForm = false;
  }

  const title = ref('');
  const content = ref('');
  const alertTitle = ref('');
  const alretContent = ref('');
  const checkTitleMessage = ref('');
  const checkContentMessage = ref('');

  // 글을 업데이트 하는 경우
  function updateArticle() {
    boardStore.article.title = title.value;
    boardStore.article.content = content.value;
    boardStore.updateArticle(boardStore.article);

    title.value = '';
    content.value = '';
    appStore.showModifyForm = false;
  }

  const checkTitle = function() {
    if (title.value === '') {
      checkTitleMessage.value = '제목을 입력해 주세요';
      alertTitle.value = 'text-red'
    } else {
      checkTitleMessage.value = '';
      alertTitle.value = '';
    }
  }

  const checkContent = function() {
    if (content.value === '') {
      checkContentMessage.value = '내용을 입력해 주세요';
      alretContent.value = 'text-red'
    } else {
      checkContentMessage.value = '';
      alretContent.value = '';
    }
  }
  
  const checkValidation = computed(() => {
    return (title.value === '' || content.value === '')
  })

</script>

<style scoped>

</style>