<template>
  <div>
    <b-modal
      size="sm"
      align-v="center"
      v-model="showModal"
      centered
      no-close-on-backdrop
      no-close-on-esc
      hide-footer
    >
      <template #modal-header>
        <b-container class="mt-2 text-center">
          <h3>Sign in</h3>
        </b-container>
      </template>
      <b-container class="mb-4">
        <b-row class="mx-1">
          <b-col class="px-0">
            <b-container class="text-center">
              <span v-if="error" class="text-danger"> {{ error.message }}</span>
            </b-container>
          </b-col>
        </b-row>

        <b-row class="my-3 mx-1">
          <b-col>
            <b-form-input v-model="login" placeholder="Login"></b-form-input>
          </b-col>
        </b-row>

        <b-row class="mb-3 mx-1">
          <b-col>
            <b-form-input
              v-model="password"
              type="password"
              placeholder="Password"
            ></b-form-input>
          </b-col>
        </b-row>

        <b-row class="mb-3 mx-1">
          <b-col>
            <b-button
              block
              @click="authorize"
              class="flex-fill"
              variant="primary"
            >
              Sign in
            </b-button>
          </b-col>
        </b-row>

        <b-row class="mb-3 mx-1 text-right">
          <b-col>
            <b-link @click="goToRegister">Not registered?</b-link>
          </b-col>
        </b-row>
      </b-container>
    </b-modal>
  </div>
</template>

<script>
export default {
  mounted() {
    const accessToken = localStorage.getItem("accessToken");
    const refreshToken = localStorage.getItem("refreshToken");
    if (accessToken || refreshToken) {
      this.$router.push("/account");
    }
  },
  computed: {
    error() {
      return this.$store.getters["auth/error"];
    },
  },
  data() {
    return {
      showModal: true,
      login: "",
      password: "",
    };
  },
  methods: {
    async authorize() {
      await this.$store.dispatch("auth/login", {
        login: this.login,
        password: this.password,
      });

      if (!this.error) {
        this.showModal = false;
        this.$router.push("/account");
      }
    },
    goToRegister() {
      this.$store.commit("auth/setError", null);
      this.showModal = false;
      this.$router.push("/signup");
    },
  },
};
</script>
