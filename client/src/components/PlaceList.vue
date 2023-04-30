<template>
  <div>
    <b-list-group class="m-3" v-if="placeList.length !== 0">
      <b-list-group-item
        v-for="place in placeList"
        :key="place.id"
        class="border-0 py-1 px-0"
      >
        <div class="card-body p-0 border rounded">
          <div class="my-0 p-0 text-left card-header" style="height: 2rem">
            <b-button
              @click="movePlace(place)"
              v-if="place.isVisited"
              variant="light"
              class="first_button"
            >
              <b-icon-x-circle variant="primary" class="icon"></b-icon-x-circle>
            </b-button>
            <b-button
              @click="movePlace(place)"
              class="first_button"
              v-if="!place.isVisited"
              variant="light"
            >
              <b-icon-check-circle
                variant="primary"
                class="icon"
              ></b-icon-check-circle>
            </b-button>
            <b-button
              @click="editPlace(place)"
              variant="light"
              class="non_first_button"
              ><b-icon-pencil variant="primary" class="icon"></b-icon-pencil
            ></b-button>
            <b-button
              @click="deletePlace(place)"
              variant="light"
              class="non_first_button"
              ><b-icon-trash variant="primary" class="icon"></b-icon-trash
            ></b-button>
          </div>

          <div class="card-text p-4">
            <b-row>
              <b-col class="text-left text-secondary" style="font-size: 12px">
                <label>
                  {{ formatTimestamp(place.dttmUpdate) }}
                </label>
              </b-col>
            </b-row>
            <b-row>
              <b-col class="text-left">
                <h5>{{ place.title }}</h5>
              </b-col>
            </b-row>
            <b-row>
              <b-col class="text-left">
                <label>{{ place.cardText || "-" }} </label>
              </b-col>
            </b-row>
            <b-row>
              <b-col class="text-left text-secondary" style="font-size: 12px">
                <label>{{ place.authorName }} </label>
              </b-col>
            </b-row>
          </div>
        </div>
      </b-list-group-item>
    </b-list-group>
    <DeletePlace
      v-if="showDeleteModal"
      v-bind:place="place"
      @close="showDeleteModal = false"
    ></DeletePlace>
    <EditPlace
      v-if="showEditModal"
      v-bind:place="place"
      @close="showEditModal = false"
    ></EditPlace>
    <div class="mx-3">
      <p v-if="placeList.length === 0">Oops! No places available</p>
    </div>
  </div>
</template>

<script>
import formatTimestamp from "../format-timestamp";
import DeletePlace from "./DeletePlace.vue";
import EditPlace from "./EditPlace.vue";

export default {
  name: "PlaceList",
  props: {
    placeList: {
      type: Array,
      required: true,
    },
  },
  components: {
    DeletePlace,
    EditPlace,
  },
  computed: {
    error() {
      return this.$store.getters["place/error"];
    },
  },
  data() {
    return {
      showDeleteModal: false,
      showEditModal: false,
      place: null,
      formatTimestamp,
    };
  },
  methods: {
    close() {
      this.$emit("close");
    },
    async deletePlace(place) {
      this.place = place;
      this.showDeleteModal = true;
    },
    async editPlace(place) {
      this.place = place;
      this.showEditModal = true;
    },
    async movePlace(place) {
      this.place = place;
      await this.$store.dispatch("place/move", {
        id: this.place.id,
        isVisited: !this.place.isVisited,
        dttmUpdate: Math.floor(Date.now() / 1000),
      });

      if (!this.error) {
        this.$store.dispatch("place/getList");
      } else if (this.error.status === 403) {
        this.$router.push("/auth");
      }
    },
  },
};
</script>

<style scoped>
.first_button {
  border-top-left-radius: 10%;
  border-bottom-left-radius: 0%;
  border-bottom-right-radius: 0%;
  border-top-right-radius: 0%;
  height: 100%;
}

.non_first_button {
  border-radius: 0%;
  height: 100%;
}

.icon {
  position: relative;
  height: 80%;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -100%);
}
</style>
