import { login, logout } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { saveUserInfo, clearLocalStorage } from '@/utils/localStorage'
import { resetRouter } from '@/router'

const state = {
  token: getToken(),
  roleId: null,
  name: null,
  phone: null,
  userId: 0
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_ROLEID: (state, roleId) => {
    state.roleId = roleId
  },
  SET_PHONE: (state, phone) => {
    state.phone = phone
  },
  SET_USERID: (state, userId) => {
    state.userId = userId
  }
}

const actions = {
  // user login
  login({ commit }, loginAccountInfo) {
    const { account, password } = loginAccountInfo
    return new Promise((resolve, reject) => {
      login({ account: account.trim(), password: password })
        .then(response => {
          const { data } = response.data
          // console.log(JSON.stringify(data))
          commit('SET_TOKEN', data.message)
          commit('SET_NAME', data.loginAccountInfo.name)
          commit('SET_PHONE', data.loginAccountInfo.phone)
          commit('SET_USERID', data.loginAccountInfo.id)

          saveUserInfo(data.loginAccountInfo)
          setToken(data.message) // 放到cookie里
          resolve()
        })
        .catch(error => {
          reject(error)
        })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token)
        .then(() => {
          commit('SET_TOKEN', '')
          removeToken()
          resetRouter()
          resolve()
        })
        .catch(error => {
          reject(error)
        })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      removeToken()
      clearLocalStorage()
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
