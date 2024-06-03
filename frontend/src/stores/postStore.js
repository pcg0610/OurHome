import { defineStore } from "pinia";
import { ref } from "vue";

export const usePostStore = defineStore('post-store', () => {
    //const post = ref({})
    //const posts = ref([
        /*
        {
            id: 1,
            author: '김싸피',
            title: '같이 살 분 구합니다1',
            content: 'sdjflaksjflksdjflsdjflaj',
            registerDate: '2024.05.12',
            authorPersonality: []
        },
        {
            id: 2,
            author: '김싸피',
            title: '같이 살 분 구합니다2',
            content: 'sdjflaksjflksdjflsdjflaj',
            registerDate: '2024.05.12',
            authorPersonality: []
        },
        {
            id: 3,
            author: '김싸피',
            title: '같이 살 분 구합니다3',
            content: 'sdjflaksjflksdjflsdjflaj',
            registerDate: '2024.05.12',
            authorPersonality: []
        },
        {
            id: 4,
            author: '김싸피',
            title: '같이 살 분 구합니다4',
            content: 'sdjflaksjflksdjflsdjflaj',
            registerDate: '2024.05.12',
            authorPersonality: []
        },
        {
            id: 5,
            author: '김싸피',
            title: '같이 살 분 구합니다5',
            content: 'sdjflaksjflksdjflsdjflaj',
            registerDate: '2024.05.12',
            authorPersonality: []
        },
        {
            id: 6,
            author: '김싸피',
            title: '같이 살 분 구합니다6',
            content: 'sdjflaksjflksdjflsdjflaj',
            registerDate: '2024.05.12',
            authorPersonality: []
        },
        {
            id: 7,
            author: '김싸피',
            title: '같이 살 분 구합니다7',
            content: 'sdjflaksjflksdjflsdjflaj',
            registerDate: '2024.05.12',
            authorPersonality: []
        },
        {
            id: 8,
            author: '김싸피',
            title: '같이 살 분 구합니다8',
            content: 'sdjflaksjflksdjflsdjflaj',
            registerDate: '2024.05.12',
            authorPersonality: []
        },
        {
            id: 9,
            author: '김싸피',
            title: '같이 살 분 구합니다9',
            content: 'sdjflaksjflksdjflsdjflaj',
            registerDate: '2024.05.12',
            authorPersonality: []
        },*/
    //])

    // 사용자가 작성한 글 목록 받아오기
    // const getList = function() {
    //     console.log("글 목록 받아오기");
    // }

    // // 선택한 특정 글 받아오기
    // const getArticle = function() {

    // }

    // const updateArticle = function(article) {
    //     const index = posts.value.findIndex((item) => item.id === article.id);
    //     console.log(index);

    //     if (index != -1) {
    //         posts.value[index].title = article.title;
    //         posts.value[index].content = article.content;
    //         posts.value[index].registerDate = new Date().toLocaleDateString();
    //     }
    // }
    
    // const removeArticle = function(article) {
    //     const index = posts.value.findIndex((item) => item.id === article.id)
        
    //     console.log(index);

    //     if (index != -1) {
    //         posts.value.splice(index, 1);
    //     }

    //     console.log(posts.value);
    // }

    // return {
    //     post, posts, updateArticle, getList, getArticle, removeArticle
    // }
})