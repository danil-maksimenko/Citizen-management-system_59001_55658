import * as React from "react";
import { Citizen } from "../../types/Citizen";
import './AddressDetails.css';

export const AddressPage: React.FC<{ citizen: Citizen; onBack: () => void }> = ({ citizen, onBack }) => {


    if (!citizen.address) {
        return (
            <div className="address-page-container">
                <h2 className="address-title">Address Details</h2>
                <p style={{textAlign: 'center'}}>No address information available.</p>
                <button className="address-back-btn" onClick={onBack}>Back</button>
            </div>
        );
    }

    return (
        <div className="address-page-container">
            <h2 className="address-title">Address Details</h2>
            <ul className="address-details">
                <li>
                    <span className="address-label">Street:</span>
                    <span className="address-value">{citizen.address.street}</span>
                </li>
                <li>
                    <span className="address-label">City:</span>
                    <span className="address-value">{citizen.address.city}</span>
                </li>
                <li>
                    <span className="address-label">Zip Code:</span>
                    <span className="address-value">{citizen.address.zipCode}</span>
                </li>
            </ul>
            <button className="address-back-btn" onClick={onBack}>Back</button>
        </div>
    );
};
