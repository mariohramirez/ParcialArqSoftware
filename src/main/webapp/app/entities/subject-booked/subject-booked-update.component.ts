import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import StudentService from '@/entities/student/student.service';
import { IStudent } from '@/shared/model/student.model';

import SubjectService from '@/entities/subject/subject.service';
import { ISubject } from '@/shared/model/subject.model';

import { ISubjectBooked, SubjectBooked } from '@/shared/model/subject-booked.model';
import SubjectBookedService from './subject-booked.service';

const validations: any = {
  subjectBooked: {
    dateEnroll: {},
    grades: {},
  },
};

@Component({
  validations,
})
export default class SubjectBookedUpdate extends Vue {
  @Inject('subjectBookedService') private subjectBookedService: () => SubjectBookedService;
  @Inject('alertService') private alertService: () => AlertService;

  public subjectBooked: ISubjectBooked = new SubjectBooked();

  @Inject('studentService') private studentService: () => StudentService;

  public students: IStudent[] = [];

  @Inject('subjectService') private subjectService: () => SubjectService;

  public subjects: ISubject[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.subjectBookedId) {
        vm.retrieveSubjectBooked(to.params.subjectBookedId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.subjectBooked.id) {
      this.subjectBookedService()
        .update(this.subjectBooked)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A SubjectBooked is updated with identifier ' + param.id;
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.subjectBookedService()
        .create(this.subjectBooked)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A SubjectBooked is created with identifier ' + param.id;
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveSubjectBooked(subjectBookedId): void {
    this.subjectBookedService()
      .find(subjectBookedId)
      .then(res => {
        this.subjectBooked = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.studentService()
      .retrieve()
      .then(res => {
        this.students = res.data;
      });
    this.subjectService()
      .retrieve()
      .then(res => {
        this.subjects = res.data;
      });
  }
}
