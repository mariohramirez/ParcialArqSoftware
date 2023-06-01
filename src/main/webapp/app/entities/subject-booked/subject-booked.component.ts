import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISubjectBooked } from '@/shared/model/subject-booked.model';

import SubjectBookedService from './subject-booked.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class SubjectBooked extends Vue {
  @Inject('subjectBookedService') private subjectBookedService: () => SubjectBookedService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public subjectBookeds: ISubjectBooked[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllSubjectBookeds();
  }

  public clear(): void {
    this.retrieveAllSubjectBookeds();
  }

  public retrieveAllSubjectBookeds(): void {
    this.isFetching = true;
    this.subjectBookedService()
      .retrieve()
      .then(
        res => {
          this.subjectBookeds = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: ISubjectBooked): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeSubjectBooked(): void {
    this.subjectBookedService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A SubjectBooked is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllSubjectBookeds();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
