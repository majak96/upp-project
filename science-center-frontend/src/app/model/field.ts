export interface Field {
  id: string;
  label: string;
  type: string;
  required: boolean;
  email: boolean;
  password: boolean;
  values: Map<string, string>;
}
