import {createRouter} from 'vue-router'
import Homepage from './home/Home.vue';
import {SignInComponent} from './user';
import Cart from './cart/Cart.vue';
import Admin from './admin/Admin.vue';

const routes = [
  {
    path: '/',
    component: Homepage
  },

  {
    path: '/sign-in/',
    component: SignInComponent
  },

  {
    path: '/cart/',
    component: Cart,
    meta: {
      requiresAuth: true, // protected route
    }
  },
  {
    path: '/admin/',
    component: Admin,
    meta: {
      requiresAuth: true, // protected route
      requiredClaims:['admin']
    }
  },
]

export default function (history) {
  return createRouter({
    history,
    routes
  })
}