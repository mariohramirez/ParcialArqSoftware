<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="subject">
        <h2 class="jh-entity-heading" data-cy="subjectDetailsHeading"><span>Subject</span> {{ subject.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ subject.name }}</span>
          </dd>
          <dt>
            <span>Code</span>
          </dt>
          <dd>
            <span>{{ subject.code }}</span>
          </dd>
          <dt>
            <span>Teacher</span>
          </dt>
          <dd>
            <span>{{ subject.teacher }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="subject.id" :to="{ name: 'SubjectEdit', params: { subjectId: subject.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./subject-details.component.ts"></script>
