import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control
import vueInfinite from 'vue-infinite-scroll'
Vue.use(vueInfinite)
import echarts from 'echarts'
Vue.prototype.$echarts = echarts
Vue.use(ElementUI)

Vue.config.productionTip = false

Vue.directive('loadmore', {
  bind(el, binding) {
    const selectWrap = el.querySelector('.el-table__body-wrapper')
    selectWrap.addEventListener('scroll', function() {
      const sign = 0
      const scrollDistance = this.scrollHeight - this.scrollTop - this.clientHeight
      if (scrollDistance <= sign) {
        binding.value()
      }
    })
  }
})

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
