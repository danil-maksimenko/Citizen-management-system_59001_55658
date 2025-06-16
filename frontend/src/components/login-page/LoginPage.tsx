import * as React from "react";
import { useState } from "react";
import { API_BASE } from "../../common/Constants";
import './LoginPage.css';

export const LoginPage: React.FC<{ onLogin: () => void; onSwitchToRegister: () => void; }> = ({ onLogin, onSwitchToRegister }) => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");
        try {
            const res = await fetch(`${API_BASE}/auth/login`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password }),
            });

            const responseBody = await res.text();

            if (!res.ok) {
                throw new Error(responseBody || "Invalid credentials");
            }

            localStorage.setItem("jwt", responseBody);
            onLogin();

        } catch (e: any) {
            setError(e.message);
        }
    };

    return (
        <div className="login-container">
            <form className="login-form" onSubmit={handleSubmit}>
                <h2>Login</h2>
                {error && <div className="message error-message">{error}</div>}
                <input
                    type="text"
                    value={username}
                    onChange={e => setUsername(e.target.value)}
                    placeholder="Username"
                    required
                />
                <input
                    type="password"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                    placeholder="Password"
                    required
                />
                <button type="submit" className="submit-btn">Login</button>

                <p className="switch-link">
                    Don't have an account? <a href="#" onClick={(e) => { e.preventDefault(); onSwitchToRegister(); }}>Register here</a>
                </p>
            </form>
        </div>
    );
};