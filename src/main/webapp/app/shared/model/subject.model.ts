export interface ISubject {
  id?: number;
  name?: string;
  code?: string;
  teacher?: string;
}

export class Subject implements ISubject {
  constructor(public id?: number, public name?: string, public code?: string, public teacher?: string) {}
}
