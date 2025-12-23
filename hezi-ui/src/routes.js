import { createWebHistory, createRouter } from 'vue-router'

const routes = [
    {
        path: '/',
        component: () => import('./view/common/Index.vue'),
        children: [
            {
                path: '/',
                component: () => import('./view/common/Home.vue'),
                meta: {
                    title: '闪电的个人空间'
                }
            },
            {
                path: 'login',
                component: () => import('./view/common/Login.vue'),
                meta: {
                    title: '登录'
                }
            },
        ]
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

router.afterEach((to) => {
    const DEFAULT_TITLE = '闪电的个人空间'
    document.title = to.meta.title===undefined ?
        DEFAULT_TITLE : to.meta.title + ' - ' + DEFAULT_TITLE
})

export default router;