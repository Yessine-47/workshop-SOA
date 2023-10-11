package ressources;

import entities.Logement;
import metiers.LogementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Path("logements")

public class LogementRessources {
    public static LogementBusiness logementMetier = new LogementBusiness();

@POST
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_JSON)
    public Response addLogement(Logement l) {
     if(logementMetier.addLogement(l))
         return  Response.status(Status.CREATED).entity(logementMetier.getLogements()).build();
     return  Response.status(Status.NOT_FOUND).build();
    }





@GET
@Produces(MediaType.APPLICATION_JSON)
@Path("getall")
public Response getAllLogements()
    {
        if (logementMetier.getLogements().size() != 0)
            return Response.status(Status.OK).entity(logementMetier.getLogements()).build();
        return Response.status(Status.NOT_FOUND).entity("la liste est vide .").build();
    }




    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogementsByDelegation(@QueryParam("delegation") String delegation) {
        List<Logement> liste=new ArrayList<Logement>();
        if(delegation != null) {
            liste = logementMetier.getLogementsByDeleguation(delegation);
            return  Response.status(Status.OK).entity(liste).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }










    public Response getLogements(String delegation, String reference) {
        List<Logement> liste=new ArrayList<Logement>();
        if(reference == null && delegation != null) {
            liste = logementMetier.getLogementsByDeleguation(delegation);

        } else if(delegation == null && reference !=null ) {
           liste =logementMetier.getLogementsListeByref(Integer.parseInt(reference));
        } else {
            liste = logementMetier.getLogements() ;
        }

           if(liste.size()==0)
               return  Response.status(Status.NOT_FOUND).build();
        return  Response.status(Status.OK).entity(liste).build();
    }


    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Path("{ref}")
    public Response updateLogement(Logement updatedLogement,@PathParam("ref") int reference) {


        if (logementMetier.updateLogement(reference,updatedLogement)) {
            return Response.status(Status.OK).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }


    @DELETE
    @Path("{ref}")
        public  Response deleteLogement(@PathParam("ref") int reference){
           if(logementMetier.deleteLogement(reference))
                    return Response.status(Status.OK).build();


            return Response.status(Status.NOT_FOUND).build();

        }

}
