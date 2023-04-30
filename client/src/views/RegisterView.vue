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
              <span v-if="errorMessage !== ''" class="text-danger">
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
            <router-link to="/auth">Already have an account?</router-link>
          </b-col>
        </b-row>
      </b-container>
    </b-modal>
    <!-- TODO: сделать алерт об успешной авторизации до редиректа -->
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
  BAlert,
} from "bootstrap-vue";

export default {
  components: {
    BRow,
    BCol,
    BModal,
    BButton,
    BContainer,
    BFormInput,
    BAlert,
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
      name: "",
    };
  },
  methods: {
    register() {
      if (this.login === "") {
        this.$store.commit("auth/setError", "Empty login!");
        return;
      }
      if (this.password === "") {
        this.$store.commit("auth/setError", "Empty password!");
        return;
      }
      if (this.name === "") {
        this.$store.commit("auth/setError", "Empty name!");
        return;
      }

      this.$store.dispatch("auth/register", {
        login: this.login,
        password: this.password,
        name: this.name,
      });

      if (!this.errorMessage) {
        this.showModal = false;
        this.$router.push("auth");
      }
    },
  },
};
</script>
