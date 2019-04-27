package net.saikatsune.veranylobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {

    @EventHandler
    public void on(WeatherChangeEvent e) {
        if(e.toWeatherState()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(ThunderChangeEvent e) {
        if(e.toThunderState()) {
            e.setCancelled(true);
        }
    }

}
