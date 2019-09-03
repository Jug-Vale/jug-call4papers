import Vue from 'vue'
import VueRouter from 'vue-router'
import ListaParticipantes from '../components/ListaParticipantes.vue'
// import lazyLoading from './lazyLoading'

Vue.use(VueRouter)

export default new VueRouter({
  routes: [
    {
      path: '*',
      redirect: { name: 'lista-participantes' },
    },
    {
      name: 'lista-participantes',
      default: true,
      path: '/lista-participantes',
      component: ListaParticipantes,
      children: [],
    },
    // { path: '/user/:id', component: User,
    //   children: [
    //     {
    //       // UserProfile will be rendered inside User's <router-view>
    //       // when /user/:id/profile is matched
    //       path: 'profile',
    //       component: UserProfile
    //     },
    //     {
    //       // UserPosts will be rendered inside User's <router-view>
    //       // when /user/:id/posts is matched
    //       path: 'posts',
    //       component: UserPosts
    //     }
    //   ]
    // }
  ]
})