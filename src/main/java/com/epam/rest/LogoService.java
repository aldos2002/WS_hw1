package com.epam.rest;


import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Almas_Doskozhin
 * on 4/10/2016.
 */
@Path("/image")
public class LogoService {

    private static final Logger LOG = LoggerFactory.getLogger(LogoService.class);
    private static final String FILE_PATH = "c:\\logos\\";

    @GET
    @Path("/get/{logoname}")
    @Produces("image/png")
    public Response getFile(@PathParam("logoname") String logoname) {
        File file = new File(FILE_PATH + logoname);
        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition",
                "attachment; filename=image_from_server.png");
        return response.build();
    }


    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {
        String uploadedFileLocation = "c://logos/" + fileDetail.getFileName();
        writeToFile(uploadedInputStream, uploadedFileLocation);
        String output = "File uploaded to: " + uploadedFileLocation;
        LOG.info(output);
        return Response.status(200).entity(output).build();
    }

    private void writeToFile(InputStream uploadedInputStream,
                             String uploadedFileLocation) {
        try(OutputStream out = new FileOutputStream(new File(uploadedFileLocation))) {
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (IOException e) {
            LOG.error("Write to file failed: ", e);
        }
    }
}
