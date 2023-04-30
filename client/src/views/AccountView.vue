<template>
  <div>
    <b-row class="text-right m-3">
      <b-col><b-link @click="logout">Logout</b-link> </b-col>
    </b-row>
    <b-card no-body class="m-3">
      <b-tabs content-class="mt-3" card>
        <b-tab title="Favourite" active class="p-0">
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
            ><b>+</b></b-nav-item
          >
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
  mounted() {
    const accessToken = localStorage.getItem("accessToken");
    if (!accessToken) {
      this.$router.push("/auth");
    }
    Promise.all([this.$store.dispatch("place/getList")]);
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
      this.$router.push("/auth");
    },
  },
};
</script>
