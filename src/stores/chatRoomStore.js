import { defineStore } from "pinia";
import { ref } from "vue";
import { api } from "@/api/chatRoom.js";
import { instance } from "@/util/http-commons";
import { useUserStore } from "./userStore";

export const useChatRoomStore = defineStore('chatRoom-store', () => {

    const userStore = useUserStore()

    /**
     * state
     */
    // 사용자가 참여하고 있는 채팅방 목록
    const chatRoomList = ref([])

    const chatRoom = ref({})


    /**
     * getter
     */

    /**
     * action
     */

    /**
     * 사용자가 참여하고 있는 채팅방 목록을 조회하는 메서드
     * @param {BigInt} userId 
     */
    const getChatRoomList = (userId) => {
        api.listChatRoom(userId,
            (response) => {
                console.log(response.data)
                chatRoomList.value = response.data
            },
            (error) => {
                console.log(error)
            }
        )
    }

    /**
     * 채팅방의 메시지를 조회하는 메서드
     * @param {Bigint} roomId 
     */
    const getMessageByRoomId = (roomId) => {
        api.listMessagesByRoomId(roomId,
            (response) => {
                messageList.value.messages = response.data
            },
            (error) => {
                console.error(error)
            }
        )
    }

    const enterChatRoom = async (postId, authorId) => {
        api.enterChatRoom(postId, authorId, userStore.user.id,
            (response) => {
                console.log(response);
                getChatRoomList(userStore.user.id);
            },
            (error) => {
                console.log(error);
            }
        )
    }

    const createChatRoom = async(postId, authorId, name) => {
        await api.createChatRoom(postId, authorId, name,
            (response) => {
                enterChatRoom(postId, authorId)
            },
            (error) => {
                console.log(error);
            }
        );
    }
    
    const getPostId = async(userId, homeId, homeName) => {
        console.log(userId + " " + homeId + " " + homeName);

        await api.getPostId(userId, homeId,
             (response) => {
                 const postId = response.data;
                 console.log(postId);
                 createChatRoom(postId, userId, homeName);
                 

                 console.log(chatRoomList.value);
             },
             (error) => {
                 console.log(error);
             }
         )
    }

    return {
        chatRoom, chatRoomList, getChatRoomList, getMessageByRoomId, enterChatRoom, createChatRoom, getPostId
    }
})