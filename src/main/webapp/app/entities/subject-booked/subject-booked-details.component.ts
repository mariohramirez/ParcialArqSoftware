import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISubjectBooked } from '@/shared/model/subject-booked.model';
import SubjectBookedService from './subject-booked.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class SubjectBookedDetails extends Vue {
  @Inject('subjectBookedService') private subjectBookedService: () => SubjectBookedService;
  @Inject('alertService') private alertService: () => AlertService;

  public subjectBooked: ISubjectBooked = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.subjectBookedId) {
        vm.retrieveSubjectBooked(to.params.subjectBookedId);
      }
    });
  }

  public retrieveSubjectBooked(subjectBookedId) {
    this.subjectBookedService()
      .find(subjectBookedId)
      .then(res => {
        this.subjectBooked = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
