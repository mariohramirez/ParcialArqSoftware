import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISubject } from '@/shared/model/subject.model';

import SubjectService from './subject.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Subject extends Vue {
  @Inject('subjectService') private subjectService: () => SubjectService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public subjects: ISubject[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllSubjects();
  }

  public clear(): void {
    this.retrieveAllSubjects();
  }

  public retrieveAllSubjects(): void {
    this.isFetching = true;
    this.subjectService()
      .retrieve()
      .then(
        res => {
          this.subjects = res.data;
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

  public prepareRemove(instance: ISubject): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeSubject(): void {
    this.subjectService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Subject is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllSubjects();
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
