package ru.otus.fp.trxreceiver.api;

import org.springframework.http.ResponseEntity;
import ru.otus.fp.trxreceiver.model.TrxData;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/process")
public interface TrxProcessorApiClient {

    @POST
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    Long processTrx(TrxData trxData);
}
