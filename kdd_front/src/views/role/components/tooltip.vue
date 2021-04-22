<template>
  <div>
    <el-tooltip class="item" effect="dark" :content="tips" placement="top">
      <span>{{ txt }}</span>
    </el-tooltip>
  </div>
</template>

<script>
export default {
  name: '',
  props: {
    rights: {
      type: Array,
      default: () => {}
    }
  },

  data() {
    return {
      tips: '',
      txt: ''
    }
  },
  mounted() {
    const tmp = this.rights.slice()
    this.tips = this.rights.join('、')
    if (tmp.length > 2) {
      this.txt = tmp.splice(0, 2).join('、') + '等'
    } else {
      this.txt = tmp.join('、')
    }
  }
}
</script>

