import { Message } from './Message';
import { User } from './User';

export class Group {

  id: string;
  name: string;
  messages: Message[];
  users: User[];

}
