import * as React from 'react';
import './HolidaysWidget.css';

interface Holiday {
    date: string;
    localName: string;
}

interface HolidaysWidgetProps {
    holidays: Holiday[];
    countryCode: string;
}

export const HolidaysWidget: React.FC<HolidaysWidgetProps> = ({ holidays, countryCode }) => {
    if (!holidays || holidays.length === 0) {
        return <div className="widget-container">Loading holidays...</div>;
    }

    const upcomingHolidays = holidays
        .filter(h => new Date(h.date) >= new Date())
        .slice(0, 4);

    return (
        <div className="widget-container">
            <h4>Upcoming Public Holidays in {countryCode.toUpperCase()}</h4>
            <ul className="holidays-list">
                {upcomingHolidays.map(holiday => (
                    <li key={holiday.date}>
                        <span className="holiday-date">{new Date(holiday.date).toLocaleDateString('en-GB')}</span>
                        <span className="holiday-name">{holiday.localName}</span>
                    </li>
                ))}
            </ul>
        </div>
    );
};