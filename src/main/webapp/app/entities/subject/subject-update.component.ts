import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { ISubject, Subject } from '@/shared/model/subject.model';
import SubjectService from './subject.service';

const validations: any = {
  subject: {
    name: {
      required,
    },
    code: {
      required,
    },
    teacher: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class SubjectUpdate extends Vue {
  @Inject('subjectService') private subjectService: () => SubjectService;
  @Inject('alertService') private alertService: () => AlertService;

  public subject: ISubject = new Subject();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.subjectId) {
        vm.retrieveSubject(to.params.subjectId);
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
    if (this.subject.id) {
      this.subjectService()
        .update(this.subject)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Subject is updated with identifier ' + param.id;
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
      this.subjectService()
        .create(this.subject)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Subject is created with identifier ' + param.id;
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

  public retrieveSubject(subjectId): void {
    this.subjectService()
      .find(subjectId)
      .then(res => {
        this.subject = res;
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
