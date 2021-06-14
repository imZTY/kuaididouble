const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  name: state => state.user.name,
  phone: state => state.user.phone,
  userId: state => state.user.userId,
  roleId: state => state.user.roleId,
  addRouters: state => state.permission.addRouters,
  routers: state => state.permission.routers
}
export default getters
