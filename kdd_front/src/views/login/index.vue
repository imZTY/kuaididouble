<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      auto-complete="on"
      label-position="left"
    >

      <div class="title-container">
        <h3 class="title">kuaididouble</h3>
      </div>

      <el-form-item prop="account">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="account"
          v-model="loginForm.account"
          placeholder="账号"
          name="account"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="密码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span
          class="show-pwd"
          @click="showPwd"
        >
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>

      <el-button
        :loading="loading"
        type="primary"
        style="width:100%;margin-bottom:30px;"
        @click.native.prevent="handleLogin"
      >登录</el-button>

      <el-button
        type="info"
        style="width:100%;margin-bottom:30px;margin-left:0px;"
        @click.native.prevent="showRegisterDialog"
      >注册</el-button>

    </el-form>

    
    <!-- 注册对话框 -->
    <el-dialog
      ref="registerForm"
      :model="registerParam"
      title="用户注册"
      :visible.sync="registerDialogVisible"
      width="70%"
      :before-close="registerDialogClose"
    >
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 20%;text-align: center;">用户名称</span>
        <el-input style="width: 70%;color: 889aa4;" 
          auto-complete="off" v-model="registerParam.name" size="mini" placeholder="请输入内容"></el-input>
      </div>
       <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 20%;text-align: center;">手机号</span>
        <el-input style="width: 70%;color: 889aa4;" v-model="registerParam.phone" size="mini" placeholder="请输入内容"></el-input>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;" prop="password1">
        <span style="width: 20%;text-align: center;">密  码</span>
        <el-input ref="password1" style="width: 70%;color: 889aa4;" :type="passwordType" v-model="registerParam.password1" size="mini" placeholder="请输入内容"></el-input>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;" prop="password2">
        <span style="width: 20%;text-align: center;">确认密码</span>
        <el-input style="width: 70%;color: 889aa4;" :type="passwordType" v-model="registerParam.password2" size="mini" placeholder="请输入内容"></el-input>
      </div>
      
      <span slot="footer" class="dialog-footer">
        <el-button @click="registerDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleRegister">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'
import { register } from '@/api/user'
import { Message } from 'element-ui'

export default {
  name: 'Login',
  data() {
    const validateAccount = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error('Please enter the correct account'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('密码不能少于6位'))
      } else {
        callback()
      }
    }
    return {
      registerDialogVisible: false, //显示注册对话框
      registerParam: {
        name: "",
        phone: "",
        password1: "",
        password2: "",
        accountKind: 1,
      },
      loginForm: {
        account: '',
        password: ''
      },
      loginRules: {
        account: [{ required: true, trigger: 'blur', validator: validateAccount }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    showRegisterDialog() {
      this.registerDialogVisible = true;
    },
    registerDialogClose() {
      this.registerDialogVisible = false;
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.loginForm).then(() => {
            // console.log(JSON.stringify(this.redirect))
            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          return false
        }
      })
    },
    handleRegister() {
      if (this.registerParam.name.trim() == '' || this.registerParam.phone.trim() == '' ||
          this.registerParam.password1.trim() == '' || this.registerParam.password2.trim() == '') {
        alert('注册表单有输入项为空')
        return false;
      }
      if (this.registerParam.password1.trim().length < 6) {
        alert('密码不能少于6位')
        return false;
      }
      if (this.registerParam.password2 != this.registerParam.password1) {
        alert('两次输入的密码不一致')
        return false;
      }
      register({
        'phone': this.registerParam.phone,
        'name': this.registerParam.name,
        'accountKind': this.registerParam.accountKind,
        'password': this.registerParam.password1
      }).then((res) => {
        // console.log(res.data)
        Message({
          message: '注册成功',
          type: 'success',
          duration: 5 * 1000
        })
        this.$router.push({ path: this.redirect || '/' })
        return true;
      }).catch((e) => {
        console.error("注册失败")
      })
      return false;
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

// @supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
//   .login-container .el-input input {
//     color: $cursor;
//   }
// }

/* reset element-ui css */
.login-container {

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;

    .el-input {
      display: inline-block;
      height: 47px;
      width: 85%;

      input {
        background: transparent;
        border: 0px;
        -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        color: $light_gray;
        height: 47px;
        caret-color: $cursor;

        &:-webkit-autofill {
          box-shadow: 0 0 0px 1000px $bg inset !important;
          -webkit-text-fill-color: $cursor !important;
        }
      }
    }
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
