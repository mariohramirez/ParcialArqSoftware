/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import StudentComponent from '@/entities/student/student.vue';
import StudentClass from '@/entities/student/student.component';
import StudentService from '@/entities/student/student.service';
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
  describe('Student Management Component', () => {
    let wrapper: Wrapper<StudentClass>;
    let comp: StudentClass;
    let studentServiceStub: SinonStubbedInstance<StudentService>;

    beforeEach(() => {
      studentServiceStub = sinon.createStubInstance<StudentService>(StudentService);
      studentServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<StudentClass>(StudentComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          studentService: () => studentServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      studentServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllStudents();
      await comp.$nextTick();

      // THEN
      expect(studentServiceStub.retrieve.called).toBeTruthy();
      expect(comp.students[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      studentServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(studentServiceStub.retrieve.callCount).toEqual(1);

      comp.removeStudent();
      await comp.$nextTick();

      // THEN
      expect(studentServiceStub.delete.called).toBeTruthy();
      expect(studentServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
