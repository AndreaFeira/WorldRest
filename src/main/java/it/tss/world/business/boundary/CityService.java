package it.tss.world.business.boundary;

import it.tss.world.business.entity.City;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Andrea Feira
 */
@Path(value = "cities")
@Stateless
public class CityService {

    @PersistenceContext //L'annotazione @PersistenceContext ci fornisce un collegamento tra la persistece unit e il nostro oggetto
    private EntityManager em;

    public List<City> findAll() {
        return findAll(null);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON) //Con @Produces scegliamo il tipo di formato da rispondere al Client
    public List<City> findAll(Comparator<City> comp) {
        List result = em.createNamedQuery("City.findAll").getResultList();

        if (comp != null) {
            Collections.sort(result, comp);
        }
        return result;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON) //Con @Produces scegliamo il tipo di formato da rispondere al Client
    public City findCity(@PathParam("id") Integer id) {
        return em.find(City.class, id);
    }

    @GET
    @Path("byName/{city}")
    @Produces(MediaType.APPLICATION_JSON) //Con @Produces scegliamo il tipo di formato da rispondere al Client
    public List<City> findByName(@PathParam("city") String city) {
        return em.createQuery("select C from City C where C.name like :city", City.class)
                .setParameter("city", "%" + city + "%")
                .getResultList();
    }
    
    @GET
    @Path("byCountry/{country}")
    @Produces(MediaType.APPLICATION_JSON) //Con @Produces scegliamo il tipo di formato da rispondere al Client
    public List<City> findByCountry(@PathParam("country") String country) {
        return em.createQuery("select C from City C where C.countryCode.name like :country", City.class)
                .setParameter("country", "%" + country + "%")
                .getResultList();
    }
}
