import { api } from "@/api/board";
import { defineStore } from "pinia";
import { ref } from "vue";
import Swal from "sweetalert2";
import { httpStatus } from "@/util/http-status";


export const useBoardStore = defineStore('board-store', () => {

    /**
     * state
     */
    const article = ref({});
    // 특정 집에 대한 게시글 목록 불러오기
    const articleList = ref([]);

    // 내가 작성한 모든 게시글 목록
    const myArticleList = ref([]);

    /**
     * action
     */
    const getArticleListByHome = async(homeId) => {
        await api.getArticleListByHome(homeId, 
            (response) => {
                if (response.data !== '') {
                    articleList.value = response.data
                }

            },
            (error) => {

            }
        )
    }

    const registArticle = async(newArticle) => {
        await api.registArticle(newArticle,
            (response) => {
                article.value = response.data;

                if (response.status === httpStatus.CREATED) {
                    Swal.fire({
                        title: 'Success!', text: '등록 성공', icon: 'success', position: 'center',
                        customClass: { container: 'custom-swal-container' }
                    })
                }
                else {
                    Swal.file({
                        title: 'Fail!', text: '등록 실패! 네트워크 상태를 확인해 주세요', icon:'error', position: 'center',
                        customClass: { container: 'custom-swal-container' }
                    })
                }
            },
            (error) => {
            }
        )
    }

    // 사용자가 작성한 모든 게시글 목록 불러오기
    const getArticleListById = async(userId) => {
        await api.getArticleListById(userId,
            (response) => {
                myArticleList.value = response.data;
            },
            (error) => {
            }
        )
    }

    const updateArticle = async(article) => {
        await api.updateArticle(article,
            (response) => {
                const index = myArticleList.value.findIndex((item) => item.id === article.id);
                myArticleList.value[index] = article;

                const targetItem = myArticleList.value[index];

                const idx = articleList.value.findIndex((item) => item.id === targetItem.id);
                articleList.value[idx] = targetItem;

                if (response.status === httpStatus.ACCEPTED) {
                    Swal.fire({
                        title: 'Success!', text: '수정 성공', icon: 'success', position: 'center',
                        customClass: { container: 'custom-swal-container' }
                    })
                }
                else {
                    Swal.file({
                        title: 'Fail!', text: '수정 실패! 네트워크 상태를 확인해 주세요', icon:'error', position: 'center',
                        customClass: { container: 'custom-swal-container' }
                    })
                }
            },
            (error) => {
            }
        )
    }

    const removeArticle = async(article) => {
        await api.removeArticle(article,
            (response) => {
                const myArticleListIndex = myArticleList.value.findIndex((item) => item.id === article.id);
                const targetItem = myArticleList.value[myArticleListIndex];

                const articleListIndex = articleList.value.findIndex((item) => item.id === targetItem.id);

                myArticleList.value.splice(myArticleListIndex, 1);
                articleList.value.splice(articleListIndex, 1);

                if (response.status === httpStatus.NO_CONTENT) {
                    Swal.fire({
                        title: 'Success!', text: '삭제 성공', icon: 'success', position: 'center',
                        customClass: { container: 'custom-swal-container' }
                    })
                }
                else {
                    Swal.file({
                        title: 'Fail!', text: '삭제 실패! 네트워크 상태를 확인해 주세요', icon:'error', position: 'center',
                        customClass: { container: 'custom-swal-container' }
                    })
                }
            },
            (error) => {
            }
        )
    }

    const getHouseName = async(homeId) => {
        
    }


    return {
        getArticleListByHome, registArticle, getArticleListById, updateArticle, removeArticle, articleList, article, myArticleList
    }
})