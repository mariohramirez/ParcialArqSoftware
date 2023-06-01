/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SubjectDetailComponent from '@/entities/subject/subject-details.vue';
import SubjectClass from '@/entities/subject/subject-details.component';
import SubjectService from '@/entities/subject/subject.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Subject Management Detail Component', () => {
    let wrapper: Wrapper<SubjectClass>;
    let comp: SubjectClass;
    let subjectServiceStub: SinonStubbedInstance<SubjectService>;

    beforeEach(() => {
      subjectServiceStub = sinon.createStubInstance<SubjectService>(SubjectService);

      wrapper = shallowMount<SubjectClass>(SubjectDetailComponent, {
        store,
        localVue,
        router,
        provide: { subjectService: () => subjectServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSubject = { id: 123 };
        subjectServiceStub.find.resolves(foundSubject);

        // WHEN
        comp.retrieveSubject(123);
        await comp.$nextTick();

        // THEN
        expect(comp.subject).toBe(foundSubject);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSubject = { id: 123 };
        subjectServiceStub.find.resolves(foundSubject);

        // WHEN
        comp.beforeRouteEnter({ params: { subjectId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.subject).toBe(foundSubject);
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
