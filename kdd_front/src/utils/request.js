import axios from 'axios'
import store from '@/store'
import { getToken } from '@/utils/auth'
import { Message, MessageBox } from 'element-ui'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  timeout: 20000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // 请求时带上token
    if (store.getters.token) {
      config.headers['token'] = getToken()
    }
    return config
  },
  error => {
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(

  response => {
    const res = response.data

    // if the custom code is not 20000, it is judged as an error.
    if (res.resultCode !== 200) {
      console.log(JSON.stringify(res))
      Message({
        message: res.resultMsg || 'Error',
        type: 'error',
        duration: 5 * 1000
      })

      // token过期 re-login 此处判断不合理，应该通过resultCode来判断
      if (res.resultCode === 501) {
        MessageBox.confirm('登录过期, 您可以点击取消留在此页面，或者重新登录', '确认退出', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }

      return Promise.reject(new Error('Error'))
    } else {
      return response
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
