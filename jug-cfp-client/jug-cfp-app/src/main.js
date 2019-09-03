import Vue from 'vue'
import BootstrapVue from "bootstrap-vue"
import App from './App.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap-vue/dist/bootstrap-vue.css"
import VueRouter from "./router"
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import VueLodash from 'vue-lodash'

Vue.use(BootstrapVue)
Vue.use(VueAxios, axios)
Vue.use(require('vue-moment'))
Vue.use(VueLodash, { name: 'lodash' })



new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
