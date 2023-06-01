/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import StudentDetailComponent from '@/entities/student/student-details.vue';
import StudentClass from '@/entities/student/student-details.component';
import StudentService from '@/entities/student/student.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Student Management Detail Component', () => {
    let wrapper: Wrapper<StudentClass>;
    let comp: StudentClass;
    let studentServiceStub: SinonStubbedInstance<StudentService>;

    beforeEach(() => {
      studentServiceStub = sinon.createStubInstance<StudentService>(StudentService);

      wrapper = shallowMount<StudentClass>(StudentDetailComponent, {
        store,
        localVue,
        router,
        provide: { studentService: () => studentServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundStudent = { id: 123 };
        studentServiceStub.find.resolves(foundStudent);

        // WHEN
        comp.retrieveStudent(123);
        await comp.$nextTick();

        // THEN
        expect(comp.student).toBe(foundStudent);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundStudent = { id: 123 };
        studentServiceStub.find.resolves(foundStudent);

        // WHEN
        comp.beforeRouteEnter({ params: { studentId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.student).toBe(foundStudent);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
