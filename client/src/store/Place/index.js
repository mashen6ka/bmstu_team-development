import { BaseError } from "../error";
import ax from "../../axios-instance";

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

      try {
        await ax.post("/places", { title, isVisited, cardText, dttmUpdate });
        context.commit("setError", null);
      } catch (err) {
        throw new BaseError(err.response.status);
      }
    } catch (err) {
      context.commit("setError", err);
    }
  },
  edit: async (context, { id, title, isVisited, cardText, dttmUpdate }) => {
    try {
      if (title === "") throw new BaseError(400, "Empty title!");

      try {
        await ax.put(`/places/${id}`, {
          title,
          isVisited,
          cardText,
          dttmUpdate,
        });

        context.commit("setError", null);
      } catch (err) {
        throw new BaseError(err.response.status);
      }
    } catch (err) {
      context.commit("setError", err);
    }
  },
  move: async (context, { id, isVisited, dttmUpdate }) => {
    try {
      try {
        await ax.patch(`/places/${id}`, { isVisited, dttmUpdate });

        context.commit("setError", null);
      } catch (err) {
        throw new BaseError(err.response.status);
      }
    } catch (err) {
      context.commit("setError", err);
    }
  },
  delete: async (context, { id }) => {
    try {
      try {
        await ax.delete(`/places/${id}`);

        context.commit("setError", null);
      } catch (err) {
        throw new BaseError(err.response.status);
      }
    } catch (err) {
      context.commit("setError", err);
    }
  },
  getList: async (context) => {
    try {
      try {
        const res = await ax.get(`/places`);
        context.commit("setError", null);
        context.commit("setList", res.data);
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
