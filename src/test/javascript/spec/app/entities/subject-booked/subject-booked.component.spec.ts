/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SubjectBookedComponent from '@/entities/subject-booked/subject-booked.vue';
import SubjectBookedClass from '@/entities/subject-booked/subject-booked.component';
import SubjectBookedService from '@/entities/subject-booked/subject-booked.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('SubjectBooked Management Component', () => {
    let wrapper: Wrapper<SubjectBookedClass>;
    let comp: SubjectBookedClass;
    let subjectBookedServiceStub: SinonStubbedInstance<SubjectBookedService>;

    beforeEach(() => {
      subjectBookedServiceStub = sinon.createStubInstance<SubjectBookedService>(SubjectBookedService);
      subjectBookedServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SubjectBookedClass>(SubjectBookedComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          subjectBookedService: () => subjectBookedServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      subjectBookedServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSubjectBookeds();
      await comp.$nextTick();

      // THEN
      expect(subjectBookedServiceStub.retrieve.called).toBeTruthy();
      expect(comp.subjectBookeds[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      subjectBookedServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(subjectBookedServiceStub.retrieve.callCount).toEqual(1);

      comp.removeSubjectBooked();
      await comp.$nextTick();

      // THEN
      expect(subjectBookedServiceStub.delete.called).toBeTruthy();
      expect(subjectBookedServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
