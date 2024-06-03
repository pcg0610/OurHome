import { defineStore } from 'pinia';
import * as StompJs from '@stomp/stompjs';
import { ref } from 'vue';
import { instance } from '@/util/http-commons';
export const useWebSocketStore = defineStore('webSocket', () => {

    /**
     * state
     */
    // 서버와의 웹소켓 통신을 담당하는 객체
    const client = ref(null)

    // 서버와 웹소켓이 연결되어 있는지 상태를 저장하는 객체
    const connected = ref(false)

    // 웹소켓 채널(채팅방)
    const channel = ref(null)

    // 웹소켓 채널을 구독하고 있는 객체
    const subscription = ref(null)

    // 구독한 채널로부터 받은 메시지를 저장하고 있는 객체
    const messages = ref([])

    /**
     * action
     */

    /**
     * 브로커 서버와 웹소켓 통신 연결하는 메서드
     */
    const connect = () => {
        // 연결 객체 생성
        client.value = new StompJs.Client({
            // 웹소켓 브로커 URL 설정
            brokerURL: import.meta.env.VITE_WEBSOCKET_BROCKER_URL,
            reconnectDelay: 5000,

            debug: (str) => {
                console.log(str)
            },

            // 웹소켓 연결을 성공했을 때
            onConnect: () => {

                // 연결 상태를 true로 설정
                connected.value = true;
            },

            // 웹소켓 연결을 실패했을 때
            onStompError: () => {

                // 연결 상태를 false로 설정
                connected.value = false;

                // 연결 종료
                client.value.deactivate();
            }
        })

        // 연결 시도
        client.value.activate()
    }

    /**
     * 연결을 종료하는 메서드
     */
    const disconnect = () => {
        if (!connected.value) return;

        client.value.deactivate();
    }
    
    /**
     * 메시지를 전송하는 메서드
     * @param {Object} body 
     */
    const sendMessage = (body) => {
        if (!connected.value) return;

        client.value.publish({
            destination: `/pub/${channel.value}`,
            
            body: JSON.stringify(body),

            skipContentLengthHeader: true
        });
    }

    /**
     * 연결할 채널을 설정하는 메서드
     * @param {Bigint} channelId 
     */
    const setChannel = (channelId) => {
        if (!connected.value) return;

        channel.value = channelId;
    }

    /**
     * 채널을 구독하는 메서드
     */
    const subscribe = () => {
        if (!connected.value) return;

        subscription.value = client.value.subscribe(

            // 구독 URL
            `/sub/${channel.value}`,
            
            // 채널로부터 받아온 메시지를 받았을 때 실행되는 콜백함수
            (message) => {

                // 메시지 리스트에 추가한다.
                messages.value.push(JSON.parse(message.body))

                console.log(messages.value)
            }
        )
    }

    /**
     * 구독을 취소하는 메서드
     */
    const unSubscribe = () => {
        subscription.value.unsubscribe()
    }

    /**
     * 구독으로 받아온 메시지들을 모두 삭제하는 메서드
     */
    const clearMessages = () => {
        messages.value = [];
    }
    
    return {
        messages, connect, disconnect, setChannel, sendMessage, subscribe, unSubscribe, clearMessages
    }
});
