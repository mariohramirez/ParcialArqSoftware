<template>
  <div>
    <b-dropdown-item to="/student">
      <font-awesome-icon icon="asterisk" />
      <span>Student</span>
    </b-dropdown-item>
    <b-dropdown-item to="/subject">
      <font-awesome-icon icon="asterisk" />
      <span>Subject</span>
    </b-dropdown-item>
    <b-dropdown-item to="/subject-booked">
      <font-awesome-icon icon="asterisk" />
      <span>Subject Booked</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
