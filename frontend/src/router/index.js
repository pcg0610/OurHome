import { createRouter, createWebHistory } from 'vue-router'
import MainView from '@/views/MainView.vue'
import MyPageView from '@/views/MyPageView.vue'
import MyPageInfoComponent from '@/components/mypage/MyPageInfoComponent.vue'
import MyPagePostComponent from '@/components/mypage/MyPagePostComponent.vue'
import { useUserStore } from '@/stores/userStore'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: MainView
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: MyPageView,
      beforeEnter: () => {
        const userStore = useUserStore();
        if (userStore.user.id === '') {
          return {name: 'main'};
        }
      },
      children: [
        {
          path: 'info',
          name: 'mypage-info',
          component: MyPageInfoComponent
        },
        {
          path: 'posts',
          name: 'mypage-posts',
          component: MyPagePostComponent
        },
      ]
    }
  ]
})

export default router
