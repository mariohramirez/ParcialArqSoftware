/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SubjectComponent from '@/entities/subject/subject.vue';
import SubjectClass from '@/entities/subject/subject.component';
import SubjectService from '@/entities/subject/subject.service';
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
  describe('Subject Management Component', () => {
    let wrapper: Wrapper<SubjectClass>;
    let comp: SubjectClass;
    let subjectServiceStub: SinonStubbedInstance<SubjectService>;

    beforeEach(() => {
      subjectServiceStub = sinon.createStubInstance<SubjectService>(SubjectService);
      subjectServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SubjectClass>(SubjectComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          subjectService: () => subjectServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      subjectServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSubjects();
      await comp.$nextTick();

      // THEN
      expect(subjectServiceStub.retrieve.called).toBeTruthy();
      expect(comp.subjects[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      subjectServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(subjectServiceStub.retrieve.callCount).toEqual(1);

      comp.removeSubject();
      await comp.$nextTick();

      // THEN
      expect(subjectServiceStub.delete.called).toBeTruthy();
      expect(subjectServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
