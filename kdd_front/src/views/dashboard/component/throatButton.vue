<template>
  <el-button v-bind="$attrs" :disabled="disabled" v-on="evet"><slot /></el-button>
</template>
<script>
export default {
  name: 'ThroatBtn',
  props: {
    canClick: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      timer: null
    }
  },
  computed: {
    evet() {
      if (this.$listeners.click) {
        this.$listeners.click = this.throat('click')
      }
      return this.$listeners
    },
    disabled() {
      if (this.canClick) {
        return true
      }
      if (this.timer) {
        return true
      } else {
        return false
      }
    }
  },
  methods: {
    throat(method) {
      const me = this
      return (...args) => {
        if (!me.timer) {
          me.$emit(method, ...args)
          me.timer = setTimeout(() => {
            me.timer = null
          }, me.$attrs.throat || 5000) // 默认5s的，节流
        } else {
          me.$emit('droped', ...args) // 点击太频繁的事件提示
        }
      }
    }
  }
}
</script>
