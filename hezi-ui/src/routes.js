import { createWebHistory, createRouter } from 'vue-router'

import HomeView from './view/common/Home.vue'
import LoginView from './view/common/Login.vue'
import CommonView from './view/common/Index.vue';

const routes = [
    {
        path: '/',
        component: CommonView,
        children: [
            {
                path: '/',
                component: HomeView
            },
            {
                path: 'login',
                component: LoginView
            },
        ]
    },


]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router;