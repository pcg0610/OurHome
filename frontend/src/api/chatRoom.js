import { instance } from "@/util/http-commons"

/**
 * 사용자가 참여하고 있는 채팅방 목록을 요청하는 메서드
 * @param {BigInt} userId 사용자 아이디
 * @param {Function} success 
 * @param {Function} fail 
 */
const listChatRoom = async (userId, success, fail) => {
    await instance.get(`/room/entered`, { params: { userId: userId } })
        .then(success)
        .catch(fail)
}

/**
 * 채팅방의 모든 메시지를 요청하는 메서드
 * @param {BigInt} roomId 채팅방 아이디
 * @param {Function} success 
 * @param {Function} fail 
 */
const listMessagesByRoomId = (roomId, success, fail) => {
    instance.get(`/room/${roomId}/message`).then(success).catch(fail)
}
/**
 * 채팅방을 생성하는 메소드
 * const makeChat POST (게시글 id, user_id : 상대방, myId: 내 아이디) => RequestBody
 */
const createChatRoom = async (postId, authorId, name, success, fail) => {
    await instance.post(`/room`, { 'postId' : postId, 'userId' : authorId, 'name' : name })
    .then(success)
    .catch(fail);
}

/**
 * 생성된 채팅방 입장 메소드
 */
const enterChatRoom = async (postId, authorId, userId, success, fail) => {
    await instance.post(`/room/enter`, { 'postId' : postId, 'postAuthorId' : authorId, 'userId' : userId })
    .then(success)
    .catch(fail);
}

/**
 * 게시글 ID를 받아오는 메소드
 */
const getPostId = async (userId, homeId, success, fail) => {
    await instance.get(`/room/article`, {params: {'userId' : userId, 'homeId' : homeId}})
    .then(success)
    .catch(fail);
}

export const api =  {
    listChatRoom,
    listMessagesByRoomId,
    createChatRoom,
    enterChatRoom,
    getPostId
}