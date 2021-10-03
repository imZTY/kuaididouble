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
        meta: { title: '进件审核', icon: 'certificate', role: [1, 4] }
      }
    ]
  },
  {
    path: '/product',
    redirect: '/product/index',
    component: Layout,
    meta: { role: [1, 4] },
    children: [
      {
        path: 'index',
        name: 'product',
        component: () => import('@/views/product/index'),
        meta: { title: '产品管理', icon: 'interface', role: [1, 4] }
      }
    ]
  },
  {
    path: '/charge',
    redirect: '/charge/index',
    component: Layout,
    meta: { role: [1, 4] },
    children: [
      {
        path: 'index',
        name: 'charge',
        component: () => import('@/views/charge/index'),
        meta: { title: '收费规则', icon: 'charge', role: [1, 4] }
      }
    ]
  },
  {
    path: '/order',
    redirect: '/order/index',
    component: Layout,
    meta: { role: [1, 4] },
    children: [
      {
        path: 'index',
        name: 'order',
        component: () => import('@/views/order/index'),
        meta: { title: '订单记录', icon: 'charge-order', role: [1, 4] }
      }
    ]
  },
  {
    path: '/history',
    redirect: '/history/index',
    component: Layout,
    meta: { role: [1, 3, 4] },
    children: [
      {
        path: 'index',
        name: 'history',
        component: () => import('@/views/history/index'),
        meta: { title: '请求记录', icon: 'query-log', role: [1, 3, 4] }
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
