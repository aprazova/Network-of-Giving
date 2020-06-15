export class CharityPayload{
    id: number;
    owner: string;
    title: string;
    image: string;
    description: string;
    requiredBudget: number;
    currentBudget: number;
    requiredVolunteers: number;
    currentVolunteers: number;
    imageBody: string;
}