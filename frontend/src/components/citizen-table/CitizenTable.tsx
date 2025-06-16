import * as React from "react";
import { useEffect, useState } from "react";
import { Citizen } from "../../types/Citizen";
import { API_BASE } from "../../common/Constants";
import './CitizenTable.css';
import { AddressPage } from "../address-details/AddressDetails";
import { WeatherWidget } from "../widgets/WeatherWidget";
import {HolidaysWidget} from "../widgets/HolidaysWidgets";

interface Holiday {
    date: string;
    localName: string;
}
interface WeatherData {
    current_weather: {
        temperature: number;
        windspeed: number;
        weathercode: number;
    };
}

const getRoleFromJwt = (): string => {
    const token = localStorage.getItem('jwt');
    if (!token) return '';
    try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        const authority = payload.roles[0]?.authority;
        if (authority && typeof authority === 'string') return authority.replace('ROLE_', '');
        return '';
    } catch (e) { console.error("Failed to decode JWT:", e); return ''; }
};

async function fetchWithAuth(url: string, options: RequestInit = {}) {
    const token = localStorage.getItem('jwt');
    const headers = new Headers(options.headers || {});
    if (token) headers.set('Authorization', `Bearer ${token}`);
    headers.set('Content-Type', 'application/json');
    const response = await fetch(url, { ...options, headers });
    if (!response.ok) {
        if (response.status === 401 || response.status === 403) {
            localStorage.removeItem('jwt');
            window.location.reload();
            throw new Error('Session expired. Please login again.');
        }
        const errorText = await response.text();
        throw new Error(`Request failed: ${errorText}`);
    }
    if (response.status === 204) return null;
    return response.json();
}

const initialFormData = {
    firstName: '', lastName: '', passportNumber: '',
    address: { street: '', city: '', zipCode: '' }
};

export const CitizenTable: React.FC = () => {
    const [citizens, setCitizens] = useState<Citizen[]>([]);
    const [selectedCitizen, setSelectedCitizen] = useState<Citizen | null>(null);
    const [error, setError] = useState('');
    const [userRole, setUserRole] = useState('');

    const [isUpdating, setIsUpdating] = useState<string | null>(null);
    const [formData, setFormData] = useState(initialFormData);

    const [holidays, setHolidays] = useState<Holiday[]>([]);
    const [weather, setWeather] = useState<WeatherData | null>(null);
    const [city, setCity] = useState<string | null>(null);
    const polishCountryCode = "PL";

    const fetchCitizensByRole = (role: string) => {
        if (!role) return;
        const url = role === 'ADMIN' ? `${API_BASE}/citizens` : `${API_BASE}/citizens/username`;
        fetchWithAuth(url)
            .then(data => {
                const citizenList = role === 'ADMIN' ? data : [data];
                setCitizens(citizenList);

                if (role === 'USER' && citizenList.length > 0) {
                    const userCity = citizenList[0].address?.city;
                    if (userCity) {
                        setCity(userCity);
                        fetchWithAuth(`${API_BASE}/weather/my-city`)
                            .then(setWeather)
                            .catch(e => console.error("Failed to fetch weather:", e));
                    }
                }
            })
            .catch(e => setError(e.message));
    };

    useEffect(() => {
        const role = getRoleFromJwt();
        setUserRole(role);

        fetch(`${API_BASE}/public/holidays/${polishCountryCode}`)
            .then(res => res.ok ? res.json() : [])
            .then(setHolidays)
            .catch(e => console.error("Error fetching holidays:", e));

        fetchCitizensByRole(role);
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('jwt');
        window.location.reload();
    };

    const handleDelete = (passportNumber: string) => {
        if (!passportNumber) {
            alert("Cannot delete a citizen with an empty passport number.");
            return;
        }
        if (window.confirm("Are you sure?")) {
            fetchWithAuth(`${API_BASE}/citizens/passport/${passportNumber}`, { method: 'DELETE' })
                .then(() => fetchCitizensByRole(userRole))
                .catch(e => alert(e.message));
        }
    };

    const handleUpdateClick = (citizen: Citizen) => {
        setIsUpdating(citizen.passportNumber);
        setFormData({ ...citizen, address: citizen.address || initialFormData.address });
    };

    const handleCancelUpdate = () => {
        setIsUpdating(null);
        setFormData(initialFormData);
    };

    const handleFormChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        if (['street', 'city', 'zipCode'].includes(name)) {
            setFormData(prev => ({ ...prev, address: { ...prev.address, [name]: value } }));
        } else {
            setFormData(prev => ({ ...prev, [name]: value }));
        }
    };

    const handleSave = () => {
        if (!isUpdating) return;

        if (!formData.firstName.trim() || !formData.lastName.trim() || !formData.passportNumber.trim()) {
            alert("Fields cannot be empty.");
            return;
        }

        const url = `${API_BASE}/citizens/passport/${isUpdating}`;
        fetchWithAuth(url, { method: 'PUT', body: JSON.stringify(formData) })
            .then(() => {
                handleCancelUpdate();
                fetchCitizensByRole(userRole);
            })
            .catch(e => alert(e.message));
    };


    if (selectedCitizen) {
        return <AddressPage citizen={selectedCitizen} onBack={() => setSelectedCitizen(null)} />;
    }
    if (error) return <div className="error" style={{ color: 'red' }}>{error}</div>;

    return (
        <div className="citizen-table-container">
            {userRole === 'USER' ? (
                <WeatherWidget weather={weather} city={city} />
            ) : (
                <HolidaysWidget holidays={holidays} countryCode={polishCountryCode} />
            )}

            <div className="table-header">
                <h2 className="citizen-table-title">Citizens Data</h2>
                <button className="logout-btn" onClick={handleLogout}>Logout</button>
            </div>

            {userRole === 'ADMIN' && isUpdating && (
                <div className="modal-backdrop">
                    <div className="modal">
                        <h3>Update Citizen</h3>
                        <input type="text" name="firstName" value={formData.firstName} onChange={handleFormChange} placeholder="First Name"/>
                        <input type="text" name="lastName" value={formData.lastName} onChange={handleFormChange} placeholder="Last Name"/>
                        <input type="text" name="passportNumber" value={formData.passportNumber} readOnly disabled/>

                        <h4 style={{marginTop: '15px', marginBottom: '5px', color: '#555'}}>Address</h4>
                        <input type="text" name="street" value={formData.address.street} onChange={handleFormChange} placeholder="Street"/>
                        <input type="text" name="city" value={formData.address.city} onChange={handleFormChange} placeholder="City"/>
                        <input type="text" name="zipCode" value={formData.address.zipCode} onChange={handleFormChange} placeholder="Zip Code"/>

                        <div className="modal-actions">
                            <button className="action-btn" onClick={handleSave}>Save Changes</button>
                            <button className="action-btn cancel-btn" onClick={handleCancelUpdate}>Cancel</button>
                        </div>
                    </div>
                </div>
            )}

            <ul className="citizen-list">
                {citizens.map((c) => (
                    <li className="citizen-list-item" key={c.passportNumber || `citizen-${c.id}`}>
                        <div className="citizen-info">
                            <span className="citizen-name">{c.firstName} {c.lastName}</span>
                            <span className="citizen-passport">({c.passportNumber})</span>
                        </div>
                        <div className="citizen-actions">
                            <a href="#" className="show-address-link" onClick={() => setSelectedCitizen(c)}>Show Address</a>
                            {userRole === 'ADMIN' && (
                                <>
                                    <button className="action-btn update-btn" onClick={() => handleUpdateClick(c)}>Update</button>
                                    <button className="action-btn delete-btn" onClick={() => handleDelete(c.passportNumber)}>Delete</button>
                                </>
                            )}
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};
