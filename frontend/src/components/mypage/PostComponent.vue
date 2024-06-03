<script setup>
    import { usePostStore } from '@/stores/postStore';
    import UserModifyArticleComponent from '@/components/modal/UserModifyArticleComponent.vue';
    import { useAppStore } from '@/stores/appStore';
    import { useBoardStore } from '@/stores/boardStore';
    import Swal from 'sweetalert2';
    
    const props = defineProps({
        post: Object
    })

    const appStore = useAppStore();
    const boardStore = useBoardStore();

    const removeArticle = function() {
        Swal.fire({
            title: "Warning!",
            text: "삭제하시겠습니까? 삭제한 데이터는 복구되지 않습니다.",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "삭제"
        }).then((result) => {
            if (result.isConfirmed) {
                console.log(props.post.id);
                boardStore.removeArticle(props.post);
                Swal.fire({
                title: "Deleted!",
                text: "삭제 완료",
                icon: "success"
                });
            }
        });
    }

    const update = function() {
        appStore.showModifyForm = true;
        boardStore.article = props.post;
    }
</script>

<template>
    <div>
        <v-card variant="elevated">
            <v-card-title>
                {{ post.title }}
            </v-card-title>
            <v-card-text>
                내용: {{ post.content }} <br>
                집 정보: {{ post.homeId }}
            </v-card-text>
            <v-card-subtitle>
                {{ appStore.changeBirth(post.registeredDate) }}
            </v-card-subtitle>
            <v-card-actions>
                <v-btn>
                    매물보기
                </v-btn>
                <v-btn @click="update">
                    수정하기
                </v-btn>
                <!-- 게시글 삭제 기능 추가 -->
                <v-btn @click="removeArticle">
                    삭제하기
                </v-btn>
            </v-card-actions>
        </v-card>
        <UserModifyArticleComponent v-model="appStore.showModifyForm" :post="post"/>
    </div>
</template>

<style scoped>

</style>