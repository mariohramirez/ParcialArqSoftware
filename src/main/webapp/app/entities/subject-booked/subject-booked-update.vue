<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="parcial2App.subjectBooked.home.createOrEditLabel" data-cy="SubjectBookedCreateUpdateHeading">
          Create or edit a SubjectBooked
        </h2>
        <div>
          <div class="form-group" v-if="subjectBooked.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="subjectBooked.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="subject-booked-dateEnroll">Date Enroll</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="subject-booked-dateEnroll"
                  v-model="$v.subjectBooked.dateEnroll.$model"
                  name="dateEnroll"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="subject-booked-dateEnroll"
                data-cy="dateEnroll"
                type="text"
                class="form-control"
                name="dateEnroll"
                :class="{ valid: !$v.subjectBooked.dateEnroll.$invalid, invalid: $v.subjectBooked.dateEnroll.$invalid }"
                v-model="$v.subjectBooked.dateEnroll.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="subject-booked-grades">Grades</label>
            <input
              type="text"
              class="form-control"
              name="grades"
              id="subject-booked-grades"
              data-cy="grades"
              :class="{ valid: !$v.subjectBooked.grades.$invalid, invalid: $v.subjectBooked.grades.$invalid }"
              v-model="$v.subjectBooked.grades.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="subject-booked-student">Student</label>
            <select class="form-control" id="subject-booked-student" data-cy="student" name="student" v-model="subjectBooked.student">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  subjectBooked.student && studentOption.id === subjectBooked.student.id ? subjectBooked.student : studentOption
                "
                v-for="studentOption in students"
                :key="studentOption.id"
              >
                {{ studentOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="subject-booked-subject">Subject</label>
            <select class="form-control" id="subject-booked-subject" data-cy="subject" name="subject" v-model="subjectBooked.subject">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  subjectBooked.subject && subjectOption.id === subjectBooked.subject.id ? subjectBooked.subject : subjectOption
                "
                v-for="subjectOption in subjects"
                :key="subjectOption.id"
              >
                {{ subjectOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.subjectBooked.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./subject-booked-update.component.ts"></script>
