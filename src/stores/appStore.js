import { defineStore } from "pinia";
import { ref } from "vue";

export const useAppStore = defineStore('app-store', () => {
    /**
     * state
     */
    const tab = ref('search')
    const tabs = ref({search: 'search', favorite: 'favorite'})
    const detailShow = ref(false)

    const chatViewTab = ref('roomList')
    const chatViewTabMap = ref({
        roomListTab: 'roomList',
        roomTab: 'room',
    })

    const pathShow = ref(false);

    // 로그인 창을 띄우기 위한 변수
    const showLogin = ref(false);
    const showSignup = ref(false);

    // 게시물 등록 modal을 띄우기 위한 변수
    const showArticle = ref(false);

    // 게시물 수정 modal을 띄우기 위한 변수
    const showModifyForm = ref(false);
    const itemList = ref([])

    /**
     * action
     */
    const changeBirth = function(item) {
        const date = new Date(item);
        const year = date.getFullYear();
        const month = date.getMonth() + 1;
        const day = date.getDate();
  
        return year + ". " + month + ". " + day;
      }

    return {
        tab, tabs, detailShow, chatViewTab, pathShow, chatViewTabMap, showLogin, showSignup, itemList, showArticle, showModifyForm, changeBirth
    }
})