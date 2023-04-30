import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import Vuelidate from "vuelidate";

import {
  BRow,
  BCol,
  BModal,
  BButton,
  BContainer,
  BFormInput,
  BFormCheckbox,
  BFormTextarea,
  BButtonClose,
  BListGroup,
  BListGroupItem,
  BIconXCircle,
  BIconCheckCircle,
  BIconPencil,
  BIconTrash,
  BLink,
  BTabs,
  BTab,
  BCard,
  BNavItem,
  BIconBoxArrowRight,
  BIconPlusCircle,
} from "bootstrap-vue";

Vue.component("BRow", BRow);
Vue.component("BCol", BCol);
Vue.component("BModal", BModal);
Vue.component("BButton", BButton);
Vue.component("BContainer", BContainer);
Vue.component("BFormInput", BFormInput);
Vue.component("BFormCheckbox", BFormCheckbox);
Vue.component("BFormTextarea", BFormTextarea);
Vue.component("BButtonClose", BButtonClose);
Vue.component("BListGroup", BListGroup);
Vue.component("BListGroupItem", BListGroupItem);
Vue.component("BIconXCircle", BIconXCircle);
Vue.component("BIconCheckCircle", BIconCheckCircle);
Vue.component("BIconPencil", BIconPencil);
Vue.component("BIconTrash", BIconTrash);
Vue.component("BLink", BLink);
Vue.component("BTabs", BTabs);
Vue.component("BTab", BTab);
Vue.component("BCard", BCard);
Vue.component("BNavItem", BNavItem);
Vue.component("BIconBoxArrowRight", BIconBoxArrowRight);
Vue.component("BIconPlusCircle", BIconPlusCircle);

Vue.config.productionTip = false;
Vue.use(Vuelidate);

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");

Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
