import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import StudentService from './student/student.service';
import SubjectService from './subject/subject.service';
import SubjectBookedService from './subject-booked/subject-booked.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('studentService') private studentService = () => new StudentService();
  @Provide('subjectService') private subjectService = () => new SubjectService();
  @Provide('subjectBookedService') private subjectBookedService = () => new SubjectBookedService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
