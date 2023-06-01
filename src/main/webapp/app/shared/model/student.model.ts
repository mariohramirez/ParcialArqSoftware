export interface IStudent {
  id?: number;
  name?: string;
  doc?: string;
  program?: string;
  program_code?: string;
}

export class Student implements IStudent {
  constructor(public id?: number, public name?: string, public doc?: string, public program?: string, public program_code?: string) {}
}
