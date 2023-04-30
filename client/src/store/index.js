import Vue from "vue";
import Vuex from "vuex";

import Auth from "./Auth";
import Place from "./Place";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    auth: Auth,
    place: Place,
  },
});
