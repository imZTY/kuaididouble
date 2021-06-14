import router from './router'
import store from './store'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import Cookies from 'js-cookie'

import { getUserInfo } from '@/utils/localStorage'
NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // determine whether the user has logged in
  const hasToken = getToken()

  if (hasToken) {
    console.log('有token')
    if (to.path === '/login') {
      // if is logged in, redirect to the home page
      next({ path: '/' })
      NProgress.done()
    } else {
      if (!store.getters.roleId) {
        const userInfo = getUserInfo()
        console.log('无roleId')
        if (!userInfo) {
          // 务必将cookies清除 否则死循环
          console.log('死循环')
          Cookies.remove('token')
          next(`/login`)
          NProgress.done()
        } else {
          store.commit('user/SET_ROLEID', userInfo.roleId)
          store.commit('user/SET_NAME', userInfo.name)
          store.commit('user/SET_PHONE', userInfo.phone)
          store.commit('user/SET_USERID', userInfo.id)
          store
            .dispatch('permission/GenerateRoutes', { roleId: userInfo.roleId })
            .then(() => {
              // 根据roles权限生成可访问的路由表
              router.addRoutes(store.getters.addRouters) // 动态添加可访问权限路由表
              next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
              NProgress.done()
            })
        }
      } else {
        console.log('有roleId:' + store.getters.roleId)
        next()
        NProgress.done()
      }
    }
  } else {
    /* has no token*/
    console.log('无token')
    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
