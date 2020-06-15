import { TransactionPayload } from './transaction-payload';

export class UserPayload{
    id: number;
    name: string;
    username: string;
    password: string;
    age: number;
    gender: string;
    location: string;
    usersTransactions: Array<TransactionPayload>;
}