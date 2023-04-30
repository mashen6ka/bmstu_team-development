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
    if (login === "") {
      context.commit("setError", { message: "Empty login!", status: 400 });
      return;
    }
    if (password === "") {
      context.commit("setError", { message: "Empty password!", status: 400 });
      return;
    }
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
      let errorMessage;
      if (status === 403) errorMessage = "Incorrect login or password";
      else if (status === 500) errorMessage = "Internal server error";
      context.commit("setError", { message: errorMessage, status: status });
    }
  },
  logout: () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
  },
  register: async (context, { login, password, name }) => {
    if (login === "") {
      context.commit("setError", { message: "Empty login!", status: 400 });
      return;
    }
    if (password === "") {
      context.commit("setError", { message: "Empty login!", status: 400 });
      return;
    }
    if (name === "") {
      context.commit("contextsetError", {
        message: "Empty name!",
        status: 400,
      });
      return;
    }
    try {
      await axios.post(process.env.VUE_APP_SERVER_ADDRESS + "/register", {
        login: login,
        hash: hash(password),
        name: name,
      });
      context.commit("setError", "");
    } catch (err) {
      const status = err.response.status;
      let errorMessage;
      if (status === 409) errorMessage = "This login is already taken :(";
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
