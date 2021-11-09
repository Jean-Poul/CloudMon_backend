package rest;

import com.google.gson.Gson;
import dto.Breaking_BadDTO;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import utils.HttpUtils;

@Path("quotes")
public class Breaking_BadResource {

    @Context
    private UriInfo context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getQuotes() throws IOException {
        Gson gson = new Gson();
        String bb = HttpUtils.fetchData("https://breaking-bad-quotes.herokuapp.com/v1/quotes");
        Breaking_BadDTO[] bbDTO = gson.fromJson(bb, Breaking_BadDTO[].class);
        String bbJSON = gson.toJson(bbDTO);
        return bbJSON;
    }
    
    @Path("/{numberofquotes}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getByNumber(@PathParam("numberofquotes") int numberofquotes) throws IOException {
        Gson gson = new Gson();
        String url = ("https://breaking-bad-quotes.herokuapp.com/v1/quotes" + "/" + numberofquotes);
        String bb = HttpUtils.fetchData(url);
        Breaking_BadDTO[] bbDTO = gson.fromJson(bb, Breaking_BadDTO[].class);
        String bbJSON = gson.toJson(bbDTO);
        return bbJSON;
    }
    
}
