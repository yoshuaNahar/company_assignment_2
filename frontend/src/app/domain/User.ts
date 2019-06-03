import { Group } from './Group';

export class User {

  id: string;
  username: string;
  groups: Group[];
  jwt: string;

}
