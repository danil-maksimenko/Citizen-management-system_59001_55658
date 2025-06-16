import * as React from 'react';
import { useState, useEffect } from 'react';
import { CitizenTable } from './components/citizen-table/CitizenTable';
import { LoginPage } from './components/login-page/LoginPage';
import { RegistrationPage } from './components/registration-page/RegistrationPage';
import { API_BASE } from './common/Constants';

const isAuthenticated = async (): Promise<boolean> => {
    const token = localStorage.getItem('jwt');
    if (!token) return false;

    try {
        const res = await fetch(`${API_BASE}/citizens/username`, {
            headers: { 'Authorization': `Bearer ${token}` },
        });
        return res.ok;
    } catch {
        return false;
    }
};

const App: React.FC = () => {
    const [loggedIn, setLoggedIn] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(true);
    const [showLogin, setShowLogin] = useState<boolean>(true);

    useEffect(() => {
        isAuthenticated().then(isValid => {
            setLoggedIn(isValid);
            setLoading(false);
        });
    }, []);

    const handleLogin = () => {
        setLoggedIn(true);
    };

    if (loading) return <div style={{textAlign: 'center', marginTop: '50px'}}>Loading...</div>;

    if (!loggedIn) {
        return showLogin
            ? <LoginPage onLogin={handleLogin} onSwitchToRegister={() => setShowLogin(false)} />
            : <RegistrationPage onSwitchToLogin={() => setShowLogin(true)} />;
    }

    return <CitizenTable />;
};

export default App;