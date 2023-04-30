import axios from "axios";
import buildAuthHeader from "../build-auth-header";
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
  setList: (state, placeList) => (state.placeList = placeList),
  setError: (state, error) => (state.error = error),
};

const actions = {
  add: async (context, { title, isVisited, cardText, dttmUpdate }) => {
    try {
      if (title === "") throw new BaseError(400, "Empty title!");
      const authHeader = buildAuthHeader();
      if (!authHeader) throw new BaseError(403);

      try {
        await axios.post(
          process.env.VUE_APP_SERVER_ADDRESS + "/places",
          { title, isVisited, cardText, dttmUpdate },
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
      if (err.status === 403) {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
      }
    }
  },
  edit: async (context, { id, title, isVisited, cardText, dttmUpdate }) => {
    try {
      if (title === "") throw new BaseError(400, "Empty title!");
      const authHeader = buildAuthHeader();
      if (!authHeader) throw new BaseError(403);

      try {
        await axios.put(
          process.env.VUE_APP_SERVER_ADDRESS + `/places/${id}`,
          { title, isVisited, cardText, dttmUpdate },
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
      if (err.status === 403) {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
      }
    }
  },
  move: async (context, { id, isVisited, dttmUpdate }) => {
    try {
      const authHeader = buildAuthHeader();
      if (!authHeader) throw new BaseError(403);
      try {
        await axios.patch(
          process.env.VUE_APP_SERVER_ADDRESS + `/places/${id}`,
          { isVisited, dttmUpdate },
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
      if (err.status === 403) {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
      }
    }
  },
  delete: async (context, { id }) => {
    try {
      const authHeader = buildAuthHeader();
      if (!authHeader) throw new BaseError(403);

      try {
        await axios.delete(
          process.env.VUE_APP_SERVER_ADDRESS + `/places/${id}`,
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
      if (err.status === 403) {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
      }
    }
  },
  getList: async (context) => {
    try {
      const authHeader = buildAuthHeader();
      if (!authHeader) throw new BaseError(403);
      try {
        const res = await axios.get(
          process.env.VUE_APP_SERVER_ADDRESS + `/places`,
          {
            withCredentials: false,
            headers: { Authorization: authHeader },
          }
        );
        context.commit("setError", null);
        context.commit("setList", res.data);
      } catch (err) {
        throw new BaseError(err.response.status);
      }
    } catch (err) {
      context.commit("setError", err);
      if (err.status === 403) {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
      }
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
