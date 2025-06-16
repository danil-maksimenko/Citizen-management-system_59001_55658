import * as React from "react";
import "./WeatherWidget.css";

interface WeatherData {
  current_weather: {
    temperature: number;
    windspeed: number;
    weathercode: number;
  };
}

interface WeatherWidgetProps {
  weather: WeatherData | null;
  city: string | null;
}

const getWeatherIcon = (code: number): string => {
  if (code === 0) return "☀️";
  if (code >= 1 && code <= 3) return "⛅️";
  if (code >= 45 && code <= 48) return "🌫️";
  if ((code >= 61 && code <= 67) || (code >= 80 && code <= 82)) return "🌧️";
  if (code >= 71 && code <= 77) return "❄️";
  if (code >= 95 && code <= 99) return "⛈️";
  return "🌍";
};

export const WeatherWidget: React.FC<WeatherWidgetProps> = ({
  weather,
  city,
}) => {
  if (!city) {
    return null;
  }

  if (!weather) {
    return <div className="weather-widget">Loading weather for {city}...</div>;
  }

  const { temperature, windspeed, weathercode } = weather.current_weather;
  const icon = getWeatherIcon(weathercode);

  return (
    <div className="weather-widget">
      <h4>Weather in {city}</h4>
      <div className="weather-details">
        <span className="weather-icon">{icon}</span>
        <span className="weather-temp">{temperature.toFixed(1)}°C</span>
        <span className="weather-wind">💨 {windspeed} km/h</span>
      </div>
    </div>
  );
};
