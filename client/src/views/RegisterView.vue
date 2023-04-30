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
          <h3>Register</h3>
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
            <b-form-input v-model="name" placeholder="Name"></b-form-input>
          </b-col>
        </b-row>

        <b-row class="mb-3 mx-1">
          <b-col>
            <b-button
              block
              @click="register"
              class="flex-fill"
              variant="primary"
            >
              Register
            </b-button>
          </b-col>
        </b-row>

        <b-row class="mb-3 mx-1 text-right">
          <b-col>
            <b-link @click="goToAuth">Already have an account?</b-link>
          </b-col>
        </b-row>
      </b-container>
    </b-modal>
    <!-- TODO: сделать алерт об успешной авторизации до редиректа -->
  </div>
</template>

<script>
export default {
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
      name: "",
    };
  },
  methods: {
    async register() {
      await this.$store.dispatch("auth/register", {
        login: this.login,
        password: this.password,
        name: this.name,
      });

      if (!this.error) {
        this.showModal = false;
        this.$router.push("auth");
      }
    },
    goToAuth() {
      this.$store.commit("auth/setError", null);
      this.showModal = false;
      this.$router.push("auth");
    },
  },
};
</script>
