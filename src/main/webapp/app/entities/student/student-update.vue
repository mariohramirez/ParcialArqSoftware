<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="parcial2App.student.home.createOrEditLabel" data-cy="StudentCreateUpdateHeading">Create or edit a Student</h2>
        <div>
          <div class="form-group" v-if="student.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="student.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="student-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="student-name"
              data-cy="name"
              :class="{ valid: !$v.student.name.$invalid, invalid: $v.student.name.$invalid }"
              v-model="$v.student.name.$model"
              required
            />
            <div v-if="$v.student.name.$anyDirty && $v.student.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.student.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="student-doc">Doc</label>
            <input
              type="text"
              class="form-control"
              name="doc"
              id="student-doc"
              data-cy="doc"
              :class="{ valid: !$v.student.doc.$invalid, invalid: $v.student.doc.$invalid }"
              v-model="$v.student.doc.$model"
              required
            />
            <div v-if="$v.student.doc.$anyDirty && $v.student.doc.$invalid">
              <small class="form-text text-danger" v-if="!$v.student.doc.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="student-program">Program</label>
            <input
              type="text"
              class="form-control"
              name="program"
              id="student-program"
              data-cy="program"
              :class="{ valid: !$v.student.program.$invalid, invalid: $v.student.program.$invalid }"
              v-model="$v.student.program.$model"
              required
            />
            <div v-if="$v.student.program.$anyDirty && $v.student.program.$invalid">
              <small class="form-text text-danger" v-if="!$v.student.program.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="student-program_code">Program Code</label>
            <input
              type="text"
              class="form-control"
              name="program_code"
              id="student-program_code"
              data-cy="program_code"
              :class="{ valid: !$v.student.program_code.$invalid, invalid: $v.student.program_code.$invalid }"
              v-model="$v.student.program_code.$model"
              required
            />
            <div v-if="$v.student.program_code.$anyDirty && $v.student.program_code.$invalid">
              <small class="form-text text-danger" v-if="!$v.student.program_code.required"> This field is required. </small>
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
            :disabled="$v.student.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./student-update.component.ts"></script>
