import axios from "axios";
import buildAuthHeader from "../build-auth-header";
import getUserId from "../get-user-id";
import { BaseError } from "../error";

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
    try {
      if (title === "") throw new BaseError(400, "Empty title!");
      const authHeader = buildAuthHeader();
      if (!authHeader) throw new BaseError(403);
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
        throw new BaseError(err.response.status);
      }
    } catch (err) {
      context.commit("setError", err);
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
