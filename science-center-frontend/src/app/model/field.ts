export interface Field {
  id: string;
  label: string;
  type: string;
  required: boolean;
  email: boolean;
  multiple: boolean;
  password: boolean;
  readonly: boolean;
  value: any;
  minNumber: number;
  values: Map<string, string>;
}
