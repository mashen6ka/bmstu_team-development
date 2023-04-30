<template>
  <div>
    <b-row class="m-3">
      <b-col class="text-left">
        <h3 class="text-primary">
          Journey <b-icon-pin-map-fill variant="primary"></b-icon-pin-map-fill>
        </h3>
      </b-col>
      <b-col class="text-right" align-self="center"
        ><b-link @click="logout"
          >Logout
          <b-icon-box-arrow-right variant="primary"></b-icon-box-arrow-right
        ></b-link>
      </b-col>
    </b-row>
    <b-card no-body class="m-3">
      <b-tabs content-class="mt-3" card>
        <b-tab title="Favourite" class="p-0">
          <PlaceList v-bind:place-list="favouritePlaces"></PlaceList>
        </b-tab>
        <b-tab title="Visited" class="p-0"
          ><PlaceList v-bind:place-list="visitedPlaces"></PlaceList
        ></b-tab>

        <template #tabs-end>
          <b-nav-item
            role="presentation"
            @click.prevent="showAddModal = true"
            href="#"
            ><b-icon-plus-circle variant="primary"></b-icon-plus-circle
          ></b-nav-item>
        </template>
      </b-tabs>
    </b-card>
    <AddPlace
      v-if="showAddModal"
      @close="showAddModal = false"
      @hide="showAddModal = false"
    ></AddPlace>
  </div>
</template>

<script>
import AddPlace from "@/components/AddPlace.vue";
import PlaceList from "@/components/PlaceList.vue";

export default {
  components: {
    AddPlace,
    PlaceList,
  },
  async mounted() {
    const accessToken = localStorage.getItem("accessToken");
    if (!accessToken) {
      this.$router.push("/signin");
    }
    await this.$store.dispatch("place/getList");
    if (this.error?.status === 403) this.$router.push("/signin");
  },
  computed: {
    visitedPlaces() {
      return this.$store.getters["place/placeList"].filter((e) => e.isVisited);
    },
    favouritePlaces() {
      return this.$store.getters["place/placeList"].filter((e) => !e.isVisited);
    },
    error() {
      return this.$store.getters["place/error"];
    },
  },
  data() {
    return {
      showAddModal: false,
      place: null,
    };
  },
  methods: {
    logout() {
      this.$store.dispatch("auth/logout");
      this.$router.push("/signin");
    },
  },
};
</script>
