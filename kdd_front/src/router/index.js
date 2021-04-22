import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)
import Layout from '@/layout'

export const constantRouterMap = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: '首页',
        component: () => import('@/views/home/index'),
        meta: { title: '首页', icon: 'home' }
      }
    ]
  }
]

export default new Router({
  routes: constantRouterMap
})

export const asyncRouterMap = [
  // {
  //   path: '/file',
  //   component: Layout,
  //   redirect: '/file/index',
  //   meta: { role: [1, 2, 3] },
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/upload/index'),
  //       name: 'Upload',
  //       meta: {
  //         title: '文件管理',
  //         icon: 'document',
  //         affix: true,
  //         role: [1, 2, 3]
  //       }
  //     }
  //   ]
  // },
  // {
  //   path: '/model',
  //   component: Layout,
  //   redirect: '/model/index',
  //   meta: { role: [1, 2, 3] },
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/model/index'),
  //       name: 'Model',
  //       meta: {
  //         title: '模型管理',
  //         icon: 'model',
  //         affix: true,
  //         role: [1, 2, 3]
  //       }
  //     }
  //   ]
  // },
  // {
  //   path: '/interface',
  //   redirect: '/interface/index',
  //   component: Layout,
  //   meta: { role: [1, 2, 3] },
  //   children: [
  //     {
  //       path: 'index',
  //       name: 'Interface',
  //       component: () => import('@/views/interface/index'),
  //       meta: { title: '接口管理', icon: 'interface', role: [1, 2, 3] }
  //     }
  //   ]
  // },
  {
    path: '/certificate',
    redirect: '/certificate/index',
    component: Layout,
    meta: { role: [1, 4] },
    children: [
      {
        path: 'index',
        name: 'certificate',
        component: () => import('@/views/certificate/index'),
        meta: { title: '进件审核', icon: 'interface', role: [1, 4] }
      }
    ]
  },
  {
    path: '/user',
    redirect: '/user/index',
    component: Layout,
    meta: { role: [1] },
    children: [
      {
        path: 'index',
        name: 'User',
        component: () => import('@/views/user/index'),
        meta: { title: '人员管理', icon: 'user', role: [1] }
      }
    ]
  },
  {
    path: '/role',
    redirect: '/role/index',
    component: Layout,
    meta: { role: [1] },
    children: [
      {
        path: 'index',
        name: 'Role',
        component: () => import('@/views/role/index'),
        meta: { title: '角色管理', icon: 'role', role: [1] }
      }
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]
