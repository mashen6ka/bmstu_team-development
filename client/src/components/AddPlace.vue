<template>
  <div>
    <b-modal
      size="sm"
      align-v="center"
      v-model="showModal"
      centered
      hide-footer
      @hide="close"
    >
      <template #modal-header>
        <b-container class="mt-2 text-left">
          <h3>Create place</h3>
        </b-container>

        <b-col align-self="center">
          <b-button-close class="pl-0" @click="close"></b-button-close>
        </b-col>
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
            <b-form-input v-model="title" placeholder="Title"></b-form-input>
          </b-col>
        </b-row>

        <b-row class="my-3 mx-1">
          <b-col>
            <b-form-checkbox v-model="isVisited" placeholder="isVisited"
              >Visited</b-form-checkbox
            >
          </b-col>
        </b-row>

        <b-row class="my-3 mx-1">
          <b-col>
            <b-form-textarea
              v-model="cardText"
              placeholder="Description"
            ></b-form-textarea>
          </b-col>
        </b-row>

        <b-row class="mb-3 mx-1">
          <b-col>
            <b-button
              block
              @click="addPlace"
              class="flex-fill"
              variant="primary"
            >
              Confirm
            </b-button>
          </b-col>
        </b-row>
      </b-container>
    </b-modal>
  </div>
</template>

<script>
export default {
  name: "AddPlace",
  props: {},
  computed: {
    error() {
      return this.$store.getters["place/error"];
    },
  },
  data() {
    return {
      title: "",
      cardText: "",
      isVisited: false,
      showModal: true,
    };
  },
  methods: {
    close() {
      this.$emit("close");
    },
    async addPlace() {
      await this.$store.dispatch("place/add", {
        title: this.title,
        isVisited: this.isVisited,
        cardText: this.cardText,
        dttmUpdate: Math.floor(Date.now() / 1000),
      });

      if (!this.error) {
        this.showModal = false;
      } else if (this.error.status === 403) {
        this.$router.push("auth");
      }
    },
  },
};
</script>
