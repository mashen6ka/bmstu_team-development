import axios from "axios";
import router from "./router";
import store from "./store";

const ax = axios.create({
  baseURL: process.env.VUE_APP_SERVER_ADDRESS,
  headers: {
    "Content-type": "application/json",
  },
});

ax.interceptors.response.use(
  (response) => {
    return response;
  },
  (err) => {
    if (err.response.status !== 403) {
      return new Promise((resolve, reject) => {
        reject(err);
      });
    }
    if (err.response.config.url === "/auth") {
      return new Promise((resolve, reject) => {
        reject(err);
      });
    }
    if (err.response.config.url === "/refresh") {
      store.dispatch("auth/logout");
      router.push("/signin");
      return new Promise((resolve, reject) => {
        reject(err);
      });
    }
    const refreshToken = localStorage.getItem("refreshToken");
    delete ax.defaults.headers.common["Authorization"];
    return ax
      .post("/refresh", refreshToken, {
        headers: {
          "Content-Type": "text/plain",
        },
      })
      .then((res) => {
        const config = err.response.config;
        localStorage.setItem("refreshToken", res.data.refreshToken);
        localStorage.setItem("accessToken", res.data.accessToken);
        config.headers.Authorization = "Bearer " + res.data.accessToken;
        return ax(config);
      });
  }
);

export default ax;
