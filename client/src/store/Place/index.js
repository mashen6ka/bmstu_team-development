import axios from "axios";
import buildAuthHeader from "../build-auth-header";
import getUserId from "../get-user-id";

const state = {
  placeList: [],
  error: null,
};

const getters = {
  placeList: (state) => state.placeList,
  error: (state) => state.error,
};

const mutations = {
  setPlaceList: (state, placeList) => (state.place = placeList),
  setError: (state, error) => (state.error = error),
};

const actions = {
  add: async (context, { title, isVisited, cardText, dttmUpdate }) => {
    const authHeader = buildAuthHeader();
    const authorId = getUserId();
    try {
      await axios.post(
        process.env.VUE_APP_SERVER_ADDRESS + "/places",
        { authorId, title, isVisited, cardText, dttmUpdate },
        {
          withCredentials: false,
          headers: { Authorization: authHeader },
        }
      );

      context.commit("setError", null);
    } catch (err) {
      const status = err.response.status;
      let errorMessage;
      if (status === 403) errorMessage = "";
      else if (status === 500) errorMessage = "Internal server error";

      context.commit("setError", { message: errorMessage, status: status });
    }
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
