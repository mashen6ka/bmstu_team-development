import axios from "axios";
import buildAuthHeader from "../build-auth-header";
import hash from "../hash";

const state = {
  user: null,
  error: null,
};

const getters = {
  USER: (state) => state.user,
  ERROR: (state) => state.error,
};

const mutations = {
  SET_USER: (state, user) => (state.user = user),
  SET_ERROR: (state, error) => (state.error = error),
};

const actions = {
  AUTHORIZE: async (context, { login, password }) => {
    const authHeader = buildAuthHeader();
    try {
      const res = await axios.post(
        process.env.VUE_APP_SERVER_ADDRESS + "/auth",
        { login: login, hash: hash(password) },
        {
          withCredentials: false,
          headers: { Authorization: authHeader },
        }
      );
      context.commit("SET_ERROR", null);

      localStorage.setItem("accessToken", res.data.accessToken);
      localStorage.setItem("refreshToken", res.data.refreshToken);
    } catch (err) {
      console.log("HERE");
      const status = err.response.status;
      let errorText;
      if (status === 403) errorText = "Incorrect login or password";
      else if (status === 500) errorText = "Internal server error";

      console.log(err.response.status);
      context.commit("SET_ERROR", errorText);
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
