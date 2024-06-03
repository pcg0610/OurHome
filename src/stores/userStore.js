import { defineStore } from "pinia";
import { ref, computed } from "vue";
import { userApi } from "@/api/user.js"
import { httpStatus } from "@/util/http-status";
import { useChatRoomStore } from "./chatRoomStore";
import { useWebSocketStore } from "./webSocketStore";
import { instance } from "@/util/http-commons";
import { useHomeStore } from "./homeStore";
import router from "@/router";
import { useMapStore } from "./kakaoMapStore";

export const useUserStore = defineStore('user-store', () => {
    const user = ref({
        id: '',
        userId: '',
        phoneNumber: '',
        password: '',
        name: '',
        gender: '',
        birth: '',
    })

    const chatRoomStore = useChatRoomStore();
    const webSocketStore = useWebSocketStore();
    const homeStore = useHomeStore();
    const mapStore = useMapStore()

    // login modal component에 전달해야 하는 code 값
    const code = ref(0);

    // 사용자가 입력한 자기 소개 뱃지
    const items = ref([])

    const infoLength = computed(() => {
        return items.value.length;
    })

    /**
     * action
     */
    const login = async (userId, password) => {
        await userApi.login(userId, password, 
            (response) => {
                instance.defaults.headers.common['Authorization'] = response.headers.authorization;

                if (response.status === httpStatus.OK) {
                    user.value = response.data;
                    code.value = response.status;

                    // 로그인 성공 시 socket 연결 수행
                    webSocketStore.connect();
                    
                    // 로그인 성공 시 해당 사용자가 참여하고 있는 채팅 방 목록을 불러온다.
                    chatRoomStore.getChatRoomList(user.value.id);

                    // 즐겨찾기 목록 가져오기
                    homeStore.getFavorites(user.value.id);

                    mapStore.mapMoveEventHandler()

                    //localStorage.setItem('id', user.value.id);
                    //localStorage.setItem('userId', user.value.userId);
                }
                else if (response.status === httpStatus.NO_CONTENT) {
                    code.value = response.status;
                }
            },
            (error) => {
                console.log(error);
                code.value = error.status;
            }
        )
    }

    const signUp = async (newUser) => {
        await userApi.signUp(newUser, 
            (response) => {
                console.log(response.status);
                if (response.status === httpStatus.CREATED) {
                    console.log("회원가입 성공");
                    code.value = response.status;
                }
                else if (response.status === httpStatus.BAD_REQUEST) {
                    console.log("회원가입 실패");
                    code.value = response.status;
                }
            },
            (error) => {
                console.error(error);
            }
        )
    }

    // client에 존재하는 JWT 값을 제거한다.
    const logout = async() => {
        await userApi.logout(
            (response) => {
                instance.defaults.headers.common['Authorization'] = ''
                user.value.id = '';
                user.value.userId = '';
                user.value.phoneNumber = '';
                user.value.password = '';
                user.value.name = '';
                user.value.gender = '';
                user.value.birth = '';

                homeStore.favoriteList = [];
                homeStore.homeList.forEach((item) => item.favorite = false)
                
                // socket 연결 해제
                webSocketStore.disconnect();
                
                alert("로그아웃 완료");
                router.replace({name: 'main'});

            },
            (error) => {
                console.log(error);
            }
        )
    }

    // 사용자가 입력한 뱃지 정보 불러오기
    const getItemList = async(userId) => {
        await userApi.getItemList(userId,
            (response) => {
                if (response.status === httpStatus.OK) {
                    items.value = response.data;
                }
                console.log(items.value);
            },
            (error) => {
            }
        )
    }

    // 사용자가 입력한 뱃지를 item 배열에 동적으로 추가
    const addInfo = (infoText) => {
        items.value.push(infoText);
    }
    
    // 사용자가 입력한 뱃지에 대하여 x버튼 클릭시 해당 뱃지 제거
    const removeInfo = (info) => {
        const index = items.value.findIndex((item) => item === info)
        items.value.splice(index, 1);
    }

    return {
        user, items, addInfo, infoLength, removeInfo, login, code, logout, signUp, getItemList
    }
})