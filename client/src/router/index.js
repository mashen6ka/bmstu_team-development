import Vue from "vue";
import VueRouter from "vue-router";
import AuthView from "../views/AuthView.vue";
import RegisterView from "../views/RegisterView.vue";
import AccountView from "../views/AccountView.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/signin",
    name: "signin",
    component: AuthView,
  },
  {
    path: "/signup",
    name: "signup",
    component: RegisterView,
  },
  {
    path: "/account",
    name: "account",
    component: AccountView,
  },
  {
    path: "/*",
    redirect: () => {
      return "account";
    },
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
