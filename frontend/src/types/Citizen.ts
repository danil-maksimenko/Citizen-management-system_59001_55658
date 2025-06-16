import { Address } from './Address';

export interface Citizen {
    id?: number;
    firstName: string;
    lastName: string;
    passportNumber: string;
    address: Address | null;
}