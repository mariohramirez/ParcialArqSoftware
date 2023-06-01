import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISubject } from '@/shared/model/subject.model';
import SubjectService from './subject.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class SubjectDetails extends Vue {
  @Inject('subjectService') private subjectService: () => SubjectService;
  @Inject('alertService') private alertService: () => AlertService;

  public subject: ISubject = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.subjectId) {
        vm.retrieveSubject(to.params.subjectId);
      }
    });
  }

  public retrieveSubject(subjectId) {
    this.subjectService()
      .find(subjectId)
      .then(res => {
        this.subject = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
