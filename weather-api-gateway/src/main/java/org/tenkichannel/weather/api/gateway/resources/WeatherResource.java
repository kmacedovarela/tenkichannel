package org.tenkichannel.weather.api.gateway.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.camel.quarkus.core.runtime.CamelRuntime;
import org.tenkichannel.weather.api.gateway.domain.Forecast;
import org.tenkichannel.weather.api.gateway.domain.Location;
import org.tenkichannel.weather.api.gateway.domain.Weather;
import org.tenkichannel.weather.api.gateway.routes.openweathermap.OpenWeatherMapRouteFactory;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WeatherResource {
    
    // TODO: handle errors 
    
    @Inject
    CamelRuntime runtime;

    @GET
    @Path("/api/weather/city/{id}")
    public Weather getWeatherCondition(@PathParam("id") int cityId) {
        return runtime.getContext().createProducerTemplate().requestBody(OpenWeatherMapRouteFactory.ROUTE_CURRENT_WEATHER_DATA, new Location(cityId), Weather.class);
    }

    @GET
    @Path("/api/weather/location/{city}")
    public Weather getWeatherCondition(@PathParam("city") String cityName) {
        return runtime.getContext().createProducerTemplate().requestBody(OpenWeatherMapRouteFactory.ROUTE_CURRENT_WEATHER_DATA, new Location(cityName), Weather.class);
    }

    @GET
    @Path("/api/weather/geo/{lat}/{log}")
    public Weather getWeatherCondition(@PathParam("lat") Double latitude, @PathParam("log") Double longitude) {
        return runtime.getContext().createProducerTemplate().requestBody(OpenWeatherMapRouteFactory.ROUTE_CURRENT_WEATHER_DATA, new Location(latitude, longitude), Weather.class);
    }
    
    /**
     * Endpoint to be compatible with Kogito Service discovery mechanism 
     * 
     * @param location
     * @return
     */
    @POST
    @Path("/forecast")
    public Weather getWeatherCondition(Forecast forecast) {
        return runtime.getContext().createProducerTemplate().requestBody(OpenWeatherMapRouteFactory.ROUTE_CURRENT_WEATHER_DATA, forecast.getLocation(), Weather.class);
    }

}
