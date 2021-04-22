/**
 * Created by ChenChangeCheng on 19/09/01.
 */

import Storage from 'web-storage-cache'

const localStorage = new Storage()
export function getLocalStorage(key) {
  return localStorage.get(key)
}

export function setLocalStorage(key, value, expire = 30 * 24 * 3600) {
  return localStorage.set(key, value, { exp: expire })
}

export function removeLocalStorage(key) {
  return localStorage.delete(key)
}

export function clearLocalStorage() {
  return localStorage.clear()
}

export function getUserInfo() {
  return getLocalStorage('loginAccountInfo')
}

export function saveUserInfo(info) {
  return setLocalStorage('loginAccountInfo', info, 36000)
}
