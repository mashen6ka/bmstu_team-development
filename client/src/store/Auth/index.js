import axios from "axios";
import buildAuthHeader from "../build-auth-header";
import hash from "../hash";

const state = {
  user: null,
  error: null,
};

const getters = {
  user: (state) => state.user,
  error: (state) => state.error,
};

const mutations = {
  setUser: (state, user) => (state.user = user),
  setError: (state, error) => (state.error = error),
};

const actions = {
  login: async (context, { login, password }) => {
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
      context.commit("setError", null);

      localStorage.setItem("accessToken", res.data.accessToken);
      localStorage.setItem("refreshToken", res.data.refreshToken);
    } catch (err) {
      const status = err.response.status;
      let errorText;
      if (status === 403) errorText = "Incorrect login or password";
      else if (status === 500) errorText = "Internal server error";
      context.commit("setError", errorText);
    }
  },
  logout: () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
