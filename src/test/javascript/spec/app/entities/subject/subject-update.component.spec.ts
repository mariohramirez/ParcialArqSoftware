/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SubjectUpdateComponent from '@/entities/subject/subject-update.vue';
import SubjectClass from '@/entities/subject/subject-update.component';
import SubjectService from '@/entities/subject/subject.service';

import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Subject Management Update Component', () => {
    let wrapper: Wrapper<SubjectClass>;
    let comp: SubjectClass;
    let subjectServiceStub: SinonStubbedInstance<SubjectService>;

    beforeEach(() => {
      subjectServiceStub = sinon.createStubInstance<SubjectService>(SubjectService);

      wrapper = shallowMount<SubjectClass>(SubjectUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          subjectService: () => subjectServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.subject = entity;
        subjectServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(subjectServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.subject = entity;
        subjectServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(subjectServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSubject = { id: 123 };
        subjectServiceStub.find.resolves(foundSubject);
        subjectServiceStub.retrieve.resolves([foundSubject]);

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
