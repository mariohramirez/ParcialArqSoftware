import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IStudent, Student } from '@/shared/model/student.model';
import StudentService from './student.service';

const validations: any = {
  student: {
    name: {
      required,
    },
    doc: {
      required,
    },
    program: {
      required,
    },
    program_code: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class StudentUpdate extends Vue {
  @Inject('studentService') private studentService: () => StudentService;
  @Inject('alertService') private alertService: () => AlertService;

  public student: IStudent = new Student();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.studentId) {
        vm.retrieveStudent(to.params.studentId);
      }
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
    if (this.student.id) {
      this.studentService()
        .update(this.student)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Student is updated with identifier ' + param.id;
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
      this.studentService()
        .create(this.student)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Student is created with identifier ' + param.id;
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

  public retrieveStudent(studentId): void {
    this.studentService()
      .find(studentId)
      .then(res => {
        this.student = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
