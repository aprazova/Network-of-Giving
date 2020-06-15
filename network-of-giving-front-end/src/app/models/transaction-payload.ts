import { CharityPayload } from '../add-charity/charity-payload';
import { UserPayload } from './user-payload';

export class TransactionPayload{
    id: number;
    charity: CharityPayload;
    user: UserPayload;
    donation: number;
    isVolunteer: boolean;
    registeredAt: Date;
}