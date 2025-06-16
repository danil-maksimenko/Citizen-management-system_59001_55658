import * as React from "react";
import { useState } from "react";
import { API_BASE } from "../../common/Constants";
import './RegistrationPage.css';

interface RegistrationData {
    username: string;
    password: string;
    firstName: string;
    lastName: string;
    passportNumber: string;
    street: string;
    city: string;
    zipCode: string;
}

const initialFormData: RegistrationData = {
    username: '',
    password: '',
    firstName: '',
    lastName: '',
    passportNumber: '',
    street: '',
    city: '',
    zipCode: '',
};

export const RegistrationPage: React.FC<{ onSwitchToLogin: () => void }> = ({ onSwitchToLogin }) => {
    const [formData, setFormData] = useState<RegistrationData>(initialFormData);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");
        setSuccess("");

        if (formData.password.length < 6) {
            setError("Password must be at least 6 characters long.");
            return;
        }

        try {
            const res = await fetch(`${API_BASE}/auth/register`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(formData),
            });

            const responseText = await res.text();

            if (!res.ok) {
                try {
                    const json = JSON.parse(responseText);
                    throw new Error(json.message || "Registration failed.");
                } catch {
                    throw new Error(responseText || "Registration failed.");
                }
            }

            setSuccess("Registration successful! You can now log in.");
            setFormData(initialFormData);

            setTimeout(() => {
                onSwitchToLogin();
            }, 3000);

        } catch (e: any) {
            const msg = e.message || "";

            if (msg.includes("passport_number") && msg.includes("Duplicate entry")) {
                setError("Passport number is already registered.");
            } else if (msg.includes("username") && msg.includes("Duplicate entry")) {
                setError("Username is already taken.");
            } else {
                setError(msg);
            }
        }
    };

    return (
        <div className="registration-container">
            <form className="registration-form" onSubmit={handleSubmit}>
                <h2>Register</h2>
                {error && <div className="message error-message">{error}</div>}
                {success && <div className="message success-message">{success}</div>}

                <div className="form-section">
                    <h4>Account Details</h4>
                    <input name="username" value={formData.username} onChange={handleChange} placeholder="Username" required />
                    <input name="password" type="password" value={formData.password} onChange={handleChange} placeholder="Password" required />
                </div>

                <div className="form-section">
                    <h4>Personal Information</h4>
                    <input name="firstName" value={formData.firstName} onChange={handleChange} placeholder="First Name" required />
                    <input name="lastName" value={formData.lastName} onChange={handleChange} placeholder="Last Name" required />
                    <input name="passportNumber" value={formData.passportNumber} onChange={handleChange} placeholder="Passport Number" required />
                </div>

                <div className="form-section">
                    <h4>Address</h4>
                    <input name="street" value={formData.street} onChange={handleChange} placeholder="Street" required />
                    <input name="city" value={formData.city} onChange={handleChange} placeholder="City" required />
                    <input name="zipCode" value={formData.zipCode} onChange={handleChange} placeholder="Zip Code" required />
                </div>

                <button type="submit" className="submit-btn">Register</button>
                <p className="switch-link">
                    Already have an account? <a href="#" onClick={(e) => { e.preventDefault(); onSwitchToLogin(); }}>Login here</a>
                </p>
            </form>
        </div>
    );
};