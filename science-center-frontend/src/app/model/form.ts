import { Field } from './field';

export interface Form {
  taskId: string;
  processId: string;
  taskName: string;
  fieldList: Field[];
}
