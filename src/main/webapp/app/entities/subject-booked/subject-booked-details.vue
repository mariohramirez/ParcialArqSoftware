<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="subjectBooked">
        <h2 class="jh-entity-heading" data-cy="subjectBookedDetailsHeading"><span>SubjectBooked</span> {{ subjectBooked.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Date Enroll</span>
          </dt>
          <dd>
            <span>{{ subjectBooked.dateEnroll }}</span>
          </dd>
          <dt>
            <span>Grades</span>
          </dt>
          <dd>
            <span>{{ subjectBooked.grades }}</span>
          </dd>
          <dt>
            <span>Student</span>
          </dt>
          <dd>
            <div v-if="subjectBooked.student">
              <router-link :to="{ name: 'StudentView', params: { studentId: subjectBooked.student.id } }">{{
                subjectBooked.student.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Subject</span>
          </dt>
          <dd>
            <div v-if="subjectBooked.subject">
              <router-link :to="{ name: 'SubjectView', params: { subjectId: subjectBooked.subject.id } }">{{
                subjectBooked.subject.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="subjectBooked.id"
          :to="{ name: 'SubjectBookedEdit', params: { subjectBookedId: subjectBooked.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./subject-booked-details.component.ts"></script>
