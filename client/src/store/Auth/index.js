import hash from "../hash";
import { AuthError } from "../error";
import ax from "../../axios-instance";

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
    try {
      if (login === "") throw new AuthError(400, "Empty login!");
      if (password === "") throw new AuthError(400, "Empty password!");

      try {
        const res = await ax.post("/auth", {
          login: login,
          hash: hash(password),
        });
        context.commit("setError", null);

        ax.defaults.headers.common["Authorization"] =
          "Bearer " + res.data.accessToken;
        localStorage.setItem("refreshToken", res.data.refreshToken);
        localStorage.setItem("accessToken", res.data.accessToken);
      } catch (err) {
        throw new AuthError(err.response.status);
      }
    } catch (err) {
      context.commit("setError", err);
    }
  },
  logout: () => {
    delete ax.defaults.headers.common["Authorization"];
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("accessToken");
  },
  register: async (context, { login, password, name }) => {
    try {
      if (login === "") throw new AuthError(400, "Empty login!");
      if (password === "") throw new AuthError(400, "Empty password!");
      if (name === "") throw new AuthError(400, "Empty name!");

      try {
        await ax.post("/register", {
          login: login,
          hash: hash(password),
          name: name,
        });
        context.commit("setError", "");
      } catch (err) {
        throw new AuthError(err.response.status);
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
