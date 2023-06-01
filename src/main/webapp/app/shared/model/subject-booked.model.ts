import { IStudent } from '@/shared/model/student.model';
import { ISubject } from '@/shared/model/subject.model';

export interface ISubjectBooked {
  id?: number;
  dateEnroll?: Date | null;
  grades?: string | null;
  student?: IStudent | null;
  subject?: ISubject | null;
}

export class SubjectBooked implements ISubjectBooked {
  constructor(
    public id?: number,
    public dateEnroll?: Date | null,
    public grades?: string | null,
    public student?: IStudent | null,
    public subject?: ISubject | null
  ) {}
}
