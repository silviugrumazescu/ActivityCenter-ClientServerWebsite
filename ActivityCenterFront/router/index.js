import { createRouter, createWebHashHistory } from 'vue-router'

import LoginPage from '../views/LoginPage'
import RegisterPage from '../views/RegisterPage'
import HomePage from '../views/HomePage'
import ActivityCard from '../components/ActivityCard'
import ExplorePage from '../views/ExplorePage'
import EventsPage from '../views/EventsPage'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: LoginPage   
  },
  {
    path: '/register',
    name: 'Register',
    component: RegisterPage   
  },
  {
    path: '/',
    name: 'Home',
    component: HomePage
  },  
  {
    path: '/explore',
    name: 'Explore',
    component: ExplorePage
  },
  {
    path: '/events',
    name: 'Events',
    component: EventsPage
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
