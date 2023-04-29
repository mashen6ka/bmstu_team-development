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
              <span v-if="errorMessage" class="text-danger">
                {{ errorMessage }}</span
              >
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
            <router-link to="/register">Not registered?</router-link>
          </b-col>
        </b-row>
      </b-container>
    </b-modal>
  </div>
</template>

<script>
import {
  BRow,
  BCol,
  BModal,
  BButton,
  BContainer,
  BFormInput,
} from "bootstrap-vue";

export default {
  components: {
    BRow,
    BCol,
    BModal,
    BButton,
    BContainer,
    BFormInput,
  },
  computed: {
    errorMessage() {
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
      if (this.login === "") {
        this.$store.commit("auth/setError", "Empty login!");
        return;
      }
      if (this.password === "") {
        this.$store.commit("auth/setError", "Empty password!");
        return;
      }

      await this.$store.dispatch("auth/login", {
        login: this.login,
        password: this.password,
      });

      if (!this.errorMessage) {
        this.showModal = false;
        this.$router.push("home");
      }
    },
  },
};
</script>
