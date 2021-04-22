/* eslint-disable indent */
import { asyncRouterMap, constantRouterMap } from '@/router'

function hasPermission(roleIdArr, route) {
    if (route.meta && route.meta.role) {
        for (let i = 0; i < roleIdArr.length; i++) {
            const roleId = parseInt(roleIdArr[i])
            // console.log('roleId=' + roleId)
            // console.log('route.meta.role=' + route.meta.role)
            // console.log('route.meta.role.indexOf(roleId)=' + route.meta.role.indexOf(roleId))
            if (route.meta.role.indexOf(roleId) >= 0) {
                return true
            }
        }
        return false
    } else {
        return true
    }
}

const state = {
    routers: constantRouterMap,
    addRouters: []
}
const mutations = {
    SET_ROUTERS: (state, routers) => {
        state.addRouters = routers
        state.routers = constantRouterMap.concat(routers)
    }
}

const actions = {

    GenerateRoutes({ commit }, roleId) {
        const roleIdStr = roleId.roleId + ''
        const roleIdArr = roleIdStr.split(',')
        const accessedRouters = asyncRouterMap.filter(v => {
            if (roleIdArr.includes('1')) return true
            if (hasPermission(roleIdArr, v)) {
                return true
            }
            return false
        })
        return new Promise((resolve, reject) => {
            commit('SET_ROUTERS', accessedRouters)
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
