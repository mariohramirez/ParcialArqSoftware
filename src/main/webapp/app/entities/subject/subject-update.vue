<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="parcial2App.subject.home.createOrEditLabel" data-cy="SubjectCreateUpdateHeading">Create or edit a Subject</h2>
        <div>
          <div class="form-group" v-if="subject.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="subject.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="subject-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="subject-name"
              data-cy="name"
              :class="{ valid: !$v.subject.name.$invalid, invalid: $v.subject.name.$invalid }"
              v-model="$v.subject.name.$model"
              required
            />
            <div v-if="$v.subject.name.$anyDirty && $v.subject.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.subject.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="subject-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="subject-code"
              data-cy="code"
              :class="{ valid: !$v.subject.code.$invalid, invalid: $v.subject.code.$invalid }"
              v-model="$v.subject.code.$model"
              required
            />
            <div v-if="$v.subject.code.$anyDirty && $v.subject.code.$invalid">
              <small class="form-text text-danger" v-if="!$v.subject.code.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="subject-teacher">Teacher</label>
            <input
              type="text"
              class="form-control"
              name="teacher"
              id="subject-teacher"
              data-cy="teacher"
              :class="{ valid: !$v.subject.teacher.$invalid, invalid: $v.subject.teacher.$invalid }"
              v-model="$v.subject.teacher.$model"
              required
            />
            <div v-if="$v.subject.teacher.$anyDirty && $v.subject.teacher.$invalid">
              <small class="form-text text-danger" v-if="!$v.subject.teacher.required"> This field is required. </small>
            </div>
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
            :disabled="$v.subject.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./subject-update.component.ts"></script>
