/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SubjectBookedDetailComponent from '@/entities/subject-booked/subject-booked-details.vue';
import SubjectBookedClass from '@/entities/subject-booked/subject-booked-details.component';
import SubjectBookedService from '@/entities/subject-booked/subject-booked.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('SubjectBooked Management Detail Component', () => {
    let wrapper: Wrapper<SubjectBookedClass>;
    let comp: SubjectBookedClass;
    let subjectBookedServiceStub: SinonStubbedInstance<SubjectBookedService>;

    beforeEach(() => {
      subjectBookedServiceStub = sinon.createStubInstance<SubjectBookedService>(SubjectBookedService);

      wrapper = shallowMount<SubjectBookedClass>(SubjectBookedDetailComponent, {
        store,
        localVue,
        router,
        provide: { subjectBookedService: () => subjectBookedServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSubjectBooked = { id: 123 };
        subjectBookedServiceStub.find.resolves(foundSubjectBooked);

        // WHEN
        comp.retrieveSubjectBooked(123);
        await comp.$nextTick();

        // THEN
        expect(comp.subjectBooked).toBe(foundSubjectBooked);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSubjectBooked = { id: 123 };
        subjectBookedServiceStub.find.resolves(foundSubjectBooked);

        // WHEN
        comp.beforeRouteEnter({ params: { subjectBookedId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.subjectBooked).toBe(foundSubjectBooked);
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
