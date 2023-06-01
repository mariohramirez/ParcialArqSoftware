<template>
  <div>
    <h2 id="page-heading" data-cy="SubjectBookedHeading">
      <span id="subject-booked-heading">Subject Bookeds</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'SubjectBookedCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-subject-booked"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Subject Booked </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && subjectBookeds && subjectBookeds.length === 0">
      <span>No subjectBookeds found</span>
    </div>
    <div class="table-responsive" v-if="subjectBookeds && subjectBookeds.length > 0">
      <table class="table table-striped" aria-describedby="subjectBookeds">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Date Enroll</span></th>
            <th scope="row"><span>Grades</span></th>
            <th scope="row"><span>Student</span></th>
            <th scope="row"><span>Subject</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="subjectBooked in subjectBookeds" :key="subjectBooked.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SubjectBookedView', params: { subjectBookedId: subjectBooked.id } }">{{
                subjectBooked.id
              }}</router-link>
            </td>
            <td>{{ subjectBooked.dateEnroll }}</td>
            <td>{{ subjectBooked.grades }}</td>
            <td>
              <div v-if="subjectBooked.student">
                <router-link :to="{ name: 'StudentView', params: { studentId: subjectBooked.student.id } }">{{
                  subjectBooked.student.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="subjectBooked.subject">
                <router-link :to="{ name: 'SubjectView', params: { subjectId: subjectBooked.subject.id } }">{{
                  subjectBooked.subject.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'SubjectBookedView', params: { subjectBookedId: subjectBooked.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'SubjectBookedEdit', params: { subjectBookedId: subjectBooked.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(subjectBooked)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="parcial2App.subjectBooked.delete.question" data-cy="subjectBookedDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-subjectBooked-heading">Are you sure you want to delete this Subject Booked?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-subjectBooked"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeSubjectBooked()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./subject-booked.component.ts"></script>
