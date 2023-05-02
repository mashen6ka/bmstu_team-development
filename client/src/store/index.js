import Vue from "vue";
import Vuex from "vuex";

import Auth from "./Auth";
import Place from "./Place";
import ax from "@/axios-instance";

ax.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem(
  "accessToken"
)}`;

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    auth: Auth,
    place: Place,
  },
});
