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
      <b-container class="mb-4">
        <b-row class="mx-1">
          <b-col class="px-0">
            <b-container class="text-center">
              <span v-if="error" class="text-danger"> {{ error.message }}</span>
            </b-container>
          </b-col>
        </b-row>

        <b-row class="mb-3 mx-1">
          <b-col>
            <b-container class="text-center">
              Are you sure you want to delete place "{{ place.title }}"
              ?</b-container
            >
          </b-col>
        </b-row>

        <b-row class="mb-3 mx-1">
          <b-col>
            <b-button
              block
              @click="deletePlace"
              class="flex-fill"
              variant="danger"
            >
              Delete
            </b-button>
          </b-col>
        </b-row>
      </b-container>
    </b-modal>
  </div>
</template>

<script>
export default {
  name: "DeletePlace",
  props: {
    place: {
      type: Object,
      required: true,
    },
  },
  computed: {
    error() {
      return this.$store.getters["place/error"];
    },
  },
  data() {
    return {
      showModal: true,
    };
  },
  methods: {
    close() {
      this.$emit("close");
    },
    async deletePlace() {
      await this.$store.dispatch("place/delete", {
        id: this.place.id,
      });

      if (!this.error) {
        await this.$store.dispatch("place/getList");
        if (this.error?.status === 403) this.$router.push("/signin");
        this.showModal = false;
      } else if (this.error.status === 403) {
        this.$router.push("/signin");
      }
    },
  },
};
</script>
