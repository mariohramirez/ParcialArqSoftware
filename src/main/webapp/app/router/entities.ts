import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Student = () => import('@/entities/student/student.vue');
// prettier-ignore
const StudentUpdate = () => import('@/entities/student/student-update.vue');
// prettier-ignore
const StudentDetails = () => import('@/entities/student/student-details.vue');
// prettier-ignore
const Subject = () => import('@/entities/subject/subject.vue');
// prettier-ignore
const SubjectUpdate = () => import('@/entities/subject/subject-update.vue');
// prettier-ignore
const SubjectDetails = () => import('@/entities/subject/subject-details.vue');
// prettier-ignore
const SubjectBooked = () => import('@/entities/subject-booked/subject-booked.vue');
// prettier-ignore
const SubjectBookedUpdate = () => import('@/entities/subject-booked/subject-booked-update.vue');
// prettier-ignore
const SubjectBookedDetails = () => import('@/entities/subject-booked/subject-booked-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'student',
      name: 'Student',
      component: Student,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'student/new',
      name: 'StudentCreate',
      component: StudentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'student/:studentId/edit',
      name: 'StudentEdit',
      component: StudentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'student/:studentId/view',
      name: 'StudentView',
      component: StudentDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'subject',
      name: 'Subject',
      component: Subject,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'subject/new',
      name: 'SubjectCreate',
      component: SubjectUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'subject/:subjectId/edit',
      name: 'SubjectEdit',
      component: SubjectUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'subject/:subjectId/view',
      name: 'SubjectView',
      component: SubjectDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'subject-booked',
      name: 'SubjectBooked',
      component: SubjectBooked,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'subject-booked/new',
      name: 'SubjectBookedCreate',
      component: SubjectBookedUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'subject-booked/:subjectBookedId/edit',
      name: 'SubjectBookedEdit',
      component: SubjectBookedUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'subject-booked/:subjectBookedId/view',
      name: 'SubjectBookedView',
      component: SubjectBookedDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
